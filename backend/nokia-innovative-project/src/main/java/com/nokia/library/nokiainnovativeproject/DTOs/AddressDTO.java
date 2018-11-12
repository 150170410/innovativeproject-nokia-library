package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

	@NotNull(message = "City name is required")
	@Size(min = 3, max = 25, message = "Build name must be 3-25 characters long")
	private String city;

	@NotNull(message = "Building name is required")
	@Size(min = 3, max = 30, message = "Building name must be 3-30 characters long")
	private String building;
}
