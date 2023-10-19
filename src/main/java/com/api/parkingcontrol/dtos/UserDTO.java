package com.api.parkingcontrol.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public class UserDTO {
	@NotBlank
	private UUID userId;
	@NotBlank
	private String username;

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UserDTO(UUID userId, String username) {
		this.userId = userId;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;

	}

}
