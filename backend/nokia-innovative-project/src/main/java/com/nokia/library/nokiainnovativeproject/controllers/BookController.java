package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.LIBRARY)
public class BookController {

	private final BookService bookService;

	@GetMapping(Mappings.BOOKS)
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping(Mappings.BOOKS_ID)
	public Book getBookById(@PathVariable Long id) {
		return bookService.getBookById(id);
	}

	@PostMapping(Mappings.BOOKS)
	public Book createBook(@RequestBody BookDTO bookDTO) {
		return bookService.createBook(bookDTO);
	}

	@PostMapping(Mappings.BOOKS_ID)
	public Book updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
		return bookService.updateBook(id, bookDTO);
	}

	@DeleteMapping(Mappings.BOOKS_ID)
	public void deleteBook(@PathVariable Long id)
			throws ResourceNotFoundException {
		bookService.deleteBook(id);
	}
}