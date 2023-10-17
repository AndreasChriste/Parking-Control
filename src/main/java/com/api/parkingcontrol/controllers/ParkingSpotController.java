package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.dtos.ParkingSpotDTO;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
@Tag(name = "Parking Control", description = "Parking Control Api REST")
public class ParkingSpotController {
    
    @Autowired
    ParkingSpotService parkingSpotService;


    @PostMapping
    @Operation(summary = "Return the Object inserted in the database", description = "Return the Object inserted in the database")
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDto){
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use");
        }
         if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use");
        }
         if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getApartment() )){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking spot already registered for this apartment/block!");
        }


        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

    @GetMapping
    @Operation(summary = "Return all  registered Parking Spots", description = "Return all  registered Parking Spots")

    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpotModel(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "returns a registered record searched by id", description = "returns a registered record searched by id")
    public ResponseEntity<Object> getOneParkingSpotModel(@PathVariable(value= "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a registered record searched by id", description = "delete a registered record searched by id")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value="id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a registered record searched by id", description = "dupdate a registered record searched by id")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable (value= "id") UUID id, @RequestBody @Valid ParkingSpotDTO parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        //first way to do.
        //requer more manual work.For cases with multiple modifications, the second option is better
        /* 
        var parkingSpotModel = parkingSpotModelOptional.get();
        parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
        parkingSpotModel.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
        parkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
        parkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
        parkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
        parkingSpotModel.setResponsableName(parkingSpotDto.getResponsableName());
        parkingSpotModel.setApartment(parkingSpotDto.getApartment());
        parkingSpotModel.setBlock(parkingSpotDto.getBlock());
         */
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
    }
}
