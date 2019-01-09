package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

	@NotEmpty(message = "City name can't be empty")
	private String city;

	@NotEmpty(message = "Building name can't be empty")
	private String building;
}
