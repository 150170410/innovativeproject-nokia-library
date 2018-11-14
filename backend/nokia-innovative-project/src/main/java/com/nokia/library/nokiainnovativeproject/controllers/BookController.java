package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOKS)
public class BookController {

	private final BookService bookService;

	@GetMapping(Mappings.GET_ALL)
	public MessageInfo getAllBooks() {
		return new MessageInfo(true, bookService.getAllBooks(), "list of books");
	}

	@GetMapping(Mappings.GET_ONE)
	public MessageInfo getBookById(@PathVariable Long id) {
		return new MessageInfo(true,bookService.getBookById(id), "Book of ID = " + id.toString());
	}

	@PostMapping(Mappings.CREATE)
	public MessageInfo createBook(@RequestBody @Valid  BookDTO bookDTO) {
		return new MessageInfo(true, bookService.createBook(bookDTO), "Book created successfully");
	}

	@PostMapping(Mappings.UPDATE)
	public MessageInfo updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO){
		return new MessageInfo(true, bookService.updateBook(id, bookDTO), "Book updated successfully");
	}

	@DeleteMapping(Mappings.REMOVE)
	public MessageInfo deleteBook(@PathVariable Long id) throws ResourceNotFoundException {
		bookService.deleteBook(id);
		return new MessageInfo(true, null, "Book with ID = " + id.toString() + " removed successfully");
	}
}