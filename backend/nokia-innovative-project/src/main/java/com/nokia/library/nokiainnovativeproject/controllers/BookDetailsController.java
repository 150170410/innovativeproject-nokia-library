package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;

import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookDetailsService;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import com.nokia.library.nokiainnovativeproject.validators.BindingResultsValidator;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;


@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + BOOK_DETAILS)
public class BookDetailsController {

	private final BookDetailsService bookDetailsService;

	@GetMapping(GET_ALL)
	public MessageInfo getAllBookDetails() {
		return MessageInfo.success(bookDetailsService.getAllBookDetails(), Arrays.asList("list of bookDetails"));
	}

	@GetMapping(GET_ONE)
	public MessageInfo getBookDetailsById(@PathVariable Long id) {
		return MessageInfo.success(bookDetailsService.getBookDetailsById(id), Arrays.asList("bookDetails of ID = " + id.toString()));
	}

	@PostMapping(CREATE)
	public MessageInfo createBookDetails(@RequestBody @Valid BookDetailsDTO bookDetailsDTO, BindingResult bindingResult) {
		BindingResultsValidator.validateBindingResults(bindingResult, bookDetailsDTO.getClass().getSimpleName());
		return MessageInfo.success(bookDetailsService.createBookDetails(bookDetailsDTO), Arrays.asList("bookDetails created successfully"));
	}

	@PostMapping(UPDATE)
	public MessageInfo updateBookDetails(@PathVariable Long id, @RequestBody @Valid BookDetailsDTO bookDetailsDTO, BindingResult bindingResult){
		BindingResultsValidator.validateBindingResults(bindingResult, bookDetailsDTO.getClass().getSimpleName());
		return MessageInfo.success(bookDetailsService.updateBookDetails(id, bookDetailsDTO), Arrays.asList("bookDetails updated successfully"));
	}

	@DeleteMapping(REMOVE)
	public MessageInfo deleteBookDetails(@PathVariable Long id) throws ResourceNotFoundException {
		bookDetailsService.deleteBookDetails(id);
		return MessageInfo.success(null, Arrays.asList("bookDetails with ID = " + id.toString() + " removed successfully"));
	}
}
