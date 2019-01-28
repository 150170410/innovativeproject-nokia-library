package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.RentalDTO;
import com.nokia.library.nokiainnovativeproject.services.RentalService;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION)
public class RentalController {

	private final RentalService rentalService;

	@GetMapping(RENTALS + GET_ALL)
	public ResponseEntity getAllRentals() {
		return MessageInfo.success(rentalService.getAllRentals(), Arrays.asList("Full list of book rentals"));
	}

	@GetMapping(RENTALS + GET_ALL_FILL)
	public ResponseEntity getAllRentalsWithOwner() {
		return MessageInfo.success(rentalService.getAllRentalsWithOwner(), Arrays.asList("Full list of book rentals"));
	}

	@GetMapping(RENTALS + GET_ONE)
	public ResponseEntity getRentalById(@PathVariable Long id) {
		return MessageInfo.success(rentalService.getRentalById(id), Arrays.asList("Rental with ID = " + id.toString()));
	}

	@GetMapping(RENTALS + GET_ONE_FILL)
	public ResponseEntity getRentalWithOwnerById(@PathVariable Long id) {
		return MessageInfo.success(rentalService.getRentalsWithActualOwner(id), Arrays.asList("Rental with ID = " + id.toString()));
	}

	@GetMapping(RENTALS + USER)
	public ResponseEntity getRentalsByUser() {
		return MessageInfo.success(rentalService.getRentalsByUser(), Arrays.asList("Rentals with UserID = "));
	}

	@GetMapping(BOOKS + RENTALS + GET_ONE)
	public ResponseEntity getRentalsByBookId(@PathVariable Long id) {
		return MessageInfo.success(rentalService.getRentalsByBookId(id), Arrays.asList("Rentals with BookID = " + id.toString()));
	}

	@PostMapping(RENTALS + CREATE)
	public ResponseEntity createRental(@RequestBody @Valid RentalDTO rentalDTO, BindingResult bindingResult) {
		MessageInfo.validateBindingResults(bindingResult);
		return MessageInfo.success(rentalService.createRental(rentalDTO), Arrays.asList("Rental created successfully"));
	}

	@PostMapping(RENTALS + PROLONG)
	public ResponseEntity prolongRental(@PathVariable Long id) {
		return MessageInfo.success(rentalService.prolongRental(id), Arrays.asList("Rental updated successfully"));
	}

	@PostMapping(RENTALS + HANDOVER)
	public ResponseEntity handOverRental(@PathVariable Long id) {
		return MessageInfo.success(rentalService.handOverRental(id), Arrays.asList("Rental updated successfully"));
	}

	@PostMapping(RENTALS + RETURN)
	public ResponseEntity returnRental(@PathVariable Long id) {
		rentalService.returnRental(id);
		return MessageInfo.success(null, Arrays.asList("Rental with ID = " + id.toString() + " returned successfully"));
	}

	@DeleteMapping(RENTALS + REMOVE)
	public ResponseEntity cancelRental(@PathVariable Long id) {
		rentalService.cancelRental(id);
		return MessageInfo.success(null, Arrays.asList("Rental with ID = " + id.toString() + " removed successfully"));
	}
}
