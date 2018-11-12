package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookDetailsService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_DETAILS)
public class BookDetailsController {

	private final BookDetailsService bookDetailsService;

	@GetMapping(Mappings.GET_ALL)
	public MessageInfo getAllBooks() {
		return new MessageInfo(true, bookDetailsService.getAllBookDetails(), "list of bookDetails");
	}

	@GetMapping(Mappings.GET_ONE)
	public MessageInfo getBookById(@PathVariable Long id) {
		return new MessageInfo(true, bookDetailsService.getBookDetailsById(id), "bookDetails of ID = " + id.toString());
	}

	@PostMapping(Mappings.SAVE)
	public MessageInfo createBook(@RequestBody BookDetailsDTO bookDetailsDTO) {
		return new MessageInfo(true, bookDetailsService.createBookDetails(bookDetailsDTO), "bookDetails created successfully");
	}

	@PostMapping(Mappings.UPDATE)
	public MessageInfo updateBook(@PathVariable Long id, @RequestBody BookDetailsDTO bookDetailsDTO) {
		return new MessageInfo(true, bookDetailsService.updateBookDetails(id, bookDetailsDTO), "bookDetails updated successfully");
	}

	@DeleteMapping(Mappings.REMOVE)
	public MessageInfo deleteBook(@PathVariable Long id) throws ResourceNotFoundException {
		bookDetailsService.deleteBookDetails(id);
		return new MessageInfo(true, null, "bookDetails with ID = " + id.toString() + " removed successfully");
	}
}
