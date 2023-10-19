package com.api.parkingcontrol.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.api.parkingcontrol.dtos.UserDTO;
import com.api.parkingcontrol.models.UserModel;
import com.api.parkingcontrol.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/cadastro")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping
	@Transactional
	public ResponseEntity<Object> saveUser(@RequestBody UserModel userModel) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserModel> users = userService.findAll(); 
		List<UserDTO> usersDTOs = users.stream().map(user -> new UserDTO(user.getUserId(), user.getUsername()))
				.collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(usersDTOs);
	}

	@GetMapping("/{id}")
	@Operation(summary = "returns a user registered record searched by id", description = "returns a user registered record searched by id")
	public ResponseEntity<Object> getOneUserModel(@PathVariable(value = "id") UUID id) {
		Optional<UserModel> userModelOptional = userService.findById(id);
		if (!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id){
		Optional<UserModel> userModelOptional = userService.findById(id);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
	}
	
	  @PutMapping("/{id}")
	    @Operation(summary = "update a registered record searched by id", description = "dupdate a registered record searched by id")
	    public ResponseEntity<Object> updateParkingSpot(@PathVariable (value= "id") UUID id, @RequestBody UserModel userModel){
	        Optional<UserModel> userModelOptional = userService.findById(id);
	        if(!userModelOptional.isPresent()){
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
	        }
	        userModel.setUserId(userModelOptional.get().getUserId());
	        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
	  }
	
}
