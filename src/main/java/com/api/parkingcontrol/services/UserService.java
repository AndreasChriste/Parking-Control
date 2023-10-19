package com.api.parkingcontrol.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.api.parkingcontrol.models.UserModel;
import com.api.parkingcontrol.repositories.UserRepository;

@Repository
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public UserModel save(UserModel userModel) {
		userModel.setPassword(new BCryptPasswordEncoder().encode(userModel.getPassword()));
		return userRepository.save(userModel);
	}
	
	public List<UserModel> findAll(){
		return userRepository.findAll();
	}
	
	public Optional<UserModel> findById(UUID userId) {
		return userRepository.findById(userId);
	}
	
}
