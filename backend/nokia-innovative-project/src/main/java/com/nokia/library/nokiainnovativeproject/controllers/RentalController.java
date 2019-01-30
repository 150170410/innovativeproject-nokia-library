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

import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION)
public class RentalController {

	private final RentalService rentalService;

	@GetMapping(RENTALS + GET_ALL)
	public ResponseEntity getAllRentals() {
		return MessageInfo.success(rentalService.getAllRentals(),
				Arrays.asList(Messages.get(LIST_OF) + "book rentals."));
	}

	@GetMapping(RENTALS + GET_ALL_FILL)
	public ResponseEntity getAllRentalsWithOwner() {
		return MessageInfo.success(rentalService.getAllRentalsWithOwner(),
				Arrays.asList(Messages.get(LIST_OF) + "book rentals."));
	}

	@GetMapping(RENTALS + GET_ONE)
	public ResponseEntity getRentalById(@PathVariable Long id) {
		return MessageInfo.success(rentalService.getRentalById(id),
				Arrays.asList("Rental" + Messages.get(REQUESTED)));
	}

	@GetMapping(RENTALS + GET_ONE_FILL)
	public ResponseEntity getRentalWithOwnerById(@PathVariable Long id) {
		return MessageInfo.success(rentalService.getRentalsWithActualOwner(id),
				Arrays.asList("Rental" + Messages.get(REQUESTED)));
	}

	@GetMapping(RENTALS + USER)
	public ResponseEntity getRentalsByUser() {
		return MessageInfo.success(rentalService.getRentalsByUser(),
				Arrays.asList("Rentals" + Messages.get(REQUESTED)));
	}

	@GetMapping(BOOKS + RENTALS + GET_ONE)
	public ResponseEntity getRentalsByBookId(@PathVariable Long id) {
		return MessageInfo.success(rentalService.getRentalsByBookId(id),
				Arrays.asList("Rentals" + Messages.get(REQUESTED)));
	}

	@PostMapping(RENTALS + CREATE)
	public ResponseEntity createRental(@RequestBody @Valid RentalDTO rentalDTO, BindingResult bindingResult) {
		MessageInfo.validateBindingResults(bindingResult);
		return MessageInfo.success(rentalService.createRental(rentalDTO),
				Arrays.asList("Rental" + Messages.get(CREATED_SUCCESSFULLY)));
	}

	@PostMapping(RENTALS + PROLONG)
	public ResponseEntity prolongRental(@PathVariable Long id) {
		return MessageInfo.success(rentalService.prolongRental(id),
				Arrays.asList("Rental" + Messages.get(UPDATED_SUCCESSFULLY)));
	}

	@PostMapping(RENTALS + HANDOVER)
	public ResponseEntity handOverRental(@PathVariable Long id) {
		return MessageInfo.success(rentalService.handOverRental(id),
				Arrays.asList("Rental" + Messages.get(UPDATED_SUCCESSFULLY)));
	}

	@PostMapping(RENTALS + RETURN)
	public ResponseEntity returnRental(@PathVariable Long id) {
		rentalService.returnRental(id);
		return MessageInfo.success(null,
				Arrays.asList("Rental" + Messages.get(RETURNED_SUCCESSFULLY)));
	}

	@DeleteMapping(RENTALS + REMOVE)
	public ResponseEntity cancelRental(@PathVariable Long id) {
		rentalService.cancelRental(id);
		return MessageInfo.success(null,
				Arrays.asList("Rental" + Messages.get(REMOVED_SUCCESSFULLY)));
	}
}