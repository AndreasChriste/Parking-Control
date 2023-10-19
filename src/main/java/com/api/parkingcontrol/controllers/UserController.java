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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

@Tag(name = "User Controller", description = "User Api REST")
@RequestMapping("/cadastro")
public class UserController {

	@Autowired
	UserService userService;

	@Operation(summary = "Return the User inserted in the database", description = "Return the User inserted in the database")
	@PostMapping
	@Transactional
	public ResponseEntity<Object> saveUser(@RequestBody UserModel userModel) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
	}

	@Operation(summary = "Return all registered user", description = "Return all registered user")
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
	@Operation(summary = "delete a user registered record searched by id", description = "delete a registered record searched by id")
	public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id){
		Optional<UserModel> userModelOptional = userService.findById(id);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
	}
	
	  @PutMapping("/{id}")
	    @Operation(summary = "Update a registered record searched by id", description = "Update a registered record searched by id")
	    public ResponseEntity<Object> updateParkingSpot(@PathVariable (value= "id") UUID id, @RequestBody UserModel userModel){
	        Optional<UserModel> userModelOptional = userService.findById(id);
	        if(!userModelOptional.isPresent()){
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
	        }
	        userModel.setUserId(userModelOptional.get().getUserId());
	        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
	  }
	
}
