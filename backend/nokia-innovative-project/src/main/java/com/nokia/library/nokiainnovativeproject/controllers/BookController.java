package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + "/library")
public class BookController {

	private final BookRepository bookRepository;

	@GetMapping(Mappings.BOOKS)
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping(Mappings.BOOKS_ID)
	public Book getBookById(@PathVariable Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
	}

	@PostMapping(Mappings.BOOKS)
	public Book createBook(@Valid @RequestBody Book book) {
		return bookRepository.save(book);
	}

	@PutMapping(Mappings.BOOKS_ID)
	public ResponseEntity<Book> updateBook(
			@PathVariable Long id,
			@Valid @RequestBody Book bookDetails) throws ResourceNotFoundException {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

		book.setTitle(bookDetails.getTitle());
		book.setAuthorName(bookDetails.getAuthorName());
		book.setAuthorSurname(bookDetails.getAuthorSurname());
		final Book updatedBook = bookRepository.save(book);
		return ResponseEntity.ok(updatedBook);
	}

	@DeleteMapping(Mappings.BOOKS_ID)
	public Map<String, Boolean> deleteBook(
			@PathVariable Long id) throws ResourceNotFoundException {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

		bookRepository.delete(book);
		return new HashMap<String, Boolean>() {{
			put("deleted", true);
		}};
	}
}