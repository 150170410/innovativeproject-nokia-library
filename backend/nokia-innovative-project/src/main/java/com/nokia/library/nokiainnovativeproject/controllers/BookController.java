package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookService;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + BOOKS)
public class BookController {

	private final BookService bookService;

	@GetMapping(GET_ALL)
	public ResponseEntity getAllBooks() {
		return MessageInfo.success(bookService.getAllBooks(), Arrays.asList(Messages.get(LIST_OF) + "books."));
	}

	@GetMapping(GET_ALL_FILL)
	public ResponseEntity getAllBooksWithOwner() {
		return MessageInfo.success(bookService.getAllBookWithOwner(), Arrays.asList(Messages.get(LIST_OF) + "books with owner."));
	}

	@GetMapping(GET_ONE)
	public ResponseEntity getBookById(@PathVariable Long id) {
		return MessageInfo.success(bookService.getBookById(id),
				Arrays.asList("Book" + Messages.get(REQUESTED)));
	}

	@GetMapping(GET_ONE_FILL)
	public ResponseEntity getBookWithOwnerById(@PathVariable Long id) {
		return MessageInfo.success(bookService.getBookWithOwnerById(id),
				Arrays.asList("Book with owner" + Messages.get(REQUESTED)));
	}

	@PostMapping(CREATE)
	public ResponseEntity createBook(@RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult) {
		MessageInfo.validateBindingResults(bindingResult);
		return MessageInfo.success(bookService.createBook(bookDTO),
				Arrays.asList("Book" + Messages.get(CREATED_SUCCESSFULLY)));
	}

	@PostMapping(UPDATE)
	public ResponseEntity updateBook(@PathVariable Long id,
									 @RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult) {
		MessageInfo.validateBindingResults(bindingResult);
		return MessageInfo.success(bookService.updateBook(id, bookDTO),
				Arrays.asList("Book" + Messages.get(UPDATED_SUCCESSFULLY)));
	}

	@DeleteMapping(REMOVE)
	public ResponseEntity deleteBook(@PathVariable Long id) throws ResourceNotFoundException {
		bookService.deleteBook(id);
		return MessageInfo.success(null,
				Arrays.asList("Book" + Messages.get(UPDATED_SUCCESSFULLY)));
	}

	@PostMapping("/lock/{signature}")
	public ResponseEntity lockBook(@PathVariable String signature) {
		return MessageInfo.success(bookService.lockBook(signature),
				Arrays.asList("Book" + Messages.get(LOCKED_SUCCESSFULLY)));
	}

	@PostMapping("/unlock/{signature}")
	public ResponseEntity unlockBook(@PathVariable String signature) {
		return MessageInfo.success(bookService.unlockBook(signature),
				Arrays.asList("Book" + Messages.get(UNLOCKED_SUCCESSFULLY)));
	}

	@PostMapping(ASSIGN_ADMIN_TO_BOOKS)
	public ResponseEntity addNewOwnerToAllAdminBooks(@PathVariable Long newOwnerId) {
		return MessageInfo.success(bookService.addNewOwnerToBooks(newOwnerId),
				Arrays.asList("Books" + Messages.get(HAS_BEEN_ASSIGNED_TO_ADMIN)));
	}

	@PostMapping(TRANSFER_BOOKS_TO_ADMIN)
	public ResponseEntity transferAllBooksToNewAdmin(@PathVariable Long newOwnerId) {
		return MessageInfo.success(bookService.transferAllBookToNewOwner(newOwnerId),
				Arrays.asList("Books" + Messages.get(HAS_BEEN_TRANSFERRED_TO_ADMIN)));
	}
}