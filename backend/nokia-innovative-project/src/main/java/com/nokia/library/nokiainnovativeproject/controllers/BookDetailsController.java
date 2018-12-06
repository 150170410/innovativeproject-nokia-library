package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;

import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookDetailsService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;


@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_DETAILS)
public class BookDetailsController {

	private final BookDetailsService bookDetailsService;

	@GetMapping(Mappings.GET_ALL)
	public MessageInfo getAllBookDetails() {
		return MessageInfo.success(bookDetailsService.getAllBookDetails(), Arrays.asList("list of bookDetails"));
	}

	@GetMapping(Mappings.GET_ONE)
	public MessageInfo getBookDetailsById(@PathVariable Long id) {
		return MessageInfo.success(bookDetailsService.getBookDetailsById(id), Arrays.asList("bookDetails of ID = " + id.toString()));
	}

	@PostMapping(Mappings.CREATE)
	public MessageInfo createBookDetails(@RequestBody @Valid BookDetailsDTO bookDetailsDTO, BindingResult bindingResult) {
		MessageInfo errors = MessageInfo.getErrors(bindingResult);
		if(errors != null)
			return errors;
		return bookDetailsService.createBookDetails(bookDetailsDTO);
	}

	@PostMapping(Mappings.UPDATE)
	public MessageInfo updateBookDetails(@PathVariable Long id, @RequestBody @Valid BookDetailsDTO bookDetailsDTO, BindingResult bindingResult){
		MessageInfo errors = MessageInfo.getErrors(bindingResult);
		if(errors != null)
			return errors;
		return bookDetailsService.updateBookDetails(id, bookDetailsDTO);
	}

	@DeleteMapping(Mappings.REMOVE)
	public MessageInfo deleteBookDetails(@PathVariable Long id) throws ResourceNotFoundException {
		bookDetailsService.deleteBookDetails(id);
		return MessageInfo.success(null, Arrays.asList("bookDetails with ID = " + id.toString() + " removed successfully"));
	}
}
