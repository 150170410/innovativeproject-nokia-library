package com.nokia.library.nokiainnovativeproject.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;

@RestController
@RequestMapping("/library")
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	// Get All Books
	@GetMapping("/books")
	public List<Book> getAllBooks() {
	    return bookRepository.findAll();
	}
	
	// Get a Single Book
	@GetMapping("/books/{id}")
	public Book getBookById(@PathVariable(value = "id") Long bookId) {
	    return bookRepository.findById(bookId)
	            .orElseThrow( ()-> new ResourceNotFoundException("Book", "id", bookId));
	}
	
	// Save a Single Book
	@PostMapping("/books")
	public Book createBook(@Valid @RequestBody Book book) {
		return bookRepository.save(book);
	}
	
	// Update a Single Book
	@PutMapping("/books/{id}")
	public ResponseEntity<Book> updateBook(
			@PathVariable(value = "id") Long bookId,
			@Valid @RequestBody Book bookDetails) throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
		        .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
		
		book.setTitle(bookDetails.getTitle());
		book.setAuthorName(bookDetails.getAuthorName());
		book.setAuthorSurname(bookDetails.getAuthorSurname());
		final Book updatedBook = bookRepository.save(book);
		return ResponseEntity.ok(updatedBook);
	}
	
	// Delete a Single Book
	@DeleteMapping("/books/{id}")
	public Map<String, Boolean> deleteBook(
			@PathVariable(value = "id") Long bookId) throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
		        .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));

		bookRepository.delete(book);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}