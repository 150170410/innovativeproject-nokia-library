package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookDetailsService;
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
@RequestMapping(API_VERSION + BOOK_DETAILS)
public class BookDetailsController {

	private final BookDetailsService bookDetailsService;

	@GetMapping(GET_ALL)
	public ResponseEntity getAllBookDetails() {
		return MessageInfo.success(bookDetailsService.getAllBookDetails(),
				Arrays.asList(Messages.get(LIST_OF) + "bookDetails."));
	}

	@GetMapping(GET_ALL + AVAILABLE)
	public ResponseEntity getAvailableBookDetails() {
		return MessageInfo.success(bookDetailsService.getAvailableBookDetails(),
				Arrays.asList(Messages.get(LIST_OF) + "available bookDetails."));
	}

	@GetMapping(GET_ONE)
	public ResponseEntity getBookDetailsById(@PathVariable Long id) {
		return MessageInfo.success(bookDetailsService.getBookDetailsById(id),
				Arrays.asList("BookDetails" + Messages.get(REQUESTED)));
	}

	@PostMapping(CREATE)
	public ResponseEntity createBookDetails(@RequestBody @Valid BookDetailsDTO bookDetailsDTO, BindingResult bindingResult) {
		MessageInfo.validateBindingResults(bindingResult);
		return MessageInfo.success(bookDetailsService.createBookDetails(bookDetailsDTO),
				Arrays.asList("BookDetails" + Messages.get(CREATED_SUCCESSFULLY)));
	}

	@PostMapping(UPDATE)
	public ResponseEntity updateBookDetails(@PathVariable Long id, @RequestBody @Valid
			BookDetailsDTO bookDetailsDTO, BindingResult bindingResult){
		MessageInfo.validateBindingResults(bindingResult);
		return MessageInfo.success(bookDetailsService.updateBookDetails(id, bookDetailsDTO),
				Arrays.asList("BookDetails" + Messages.get(UPDATED_SUCCESSFULLY)));
	}

	@DeleteMapping(REMOVE)
	public ResponseEntity deleteBookDetails(@PathVariable Long id) throws ResourceNotFoundException {
		bookDetailsService.deleteBookDetails(id);
		return MessageInfo.success(null, Arrays.asList("BookDetails" + Messages.get(REMOVED_SUCCESSFULLY)));
	}
}