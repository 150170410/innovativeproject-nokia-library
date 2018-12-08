package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookService;
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
@RequestMapping(API_VERSION + BOOKS)
public class BookController {

	private final BookService bookService;

	@GetMapping(GET_ALL)
	public MessageInfo getAllBooks() {
		return MessageInfo.success(bookService.getAllBooks(), Arrays.asList("list of books"));
	}

	@GetMapping(GET_ONE)
	public MessageInfo getBookById(@PathVariable Long id) {
		return MessageInfo.success(bookService.getBookById(id), Arrays.asList("Book of ID = " + id.toString()));
	}

	@PostMapping(CREATE)
	public MessageInfo createBook(@RequestBody @Valid  BookDTO bookDTO, BindingResult bindingResult){
		BindingResultsValidator.validateBindingResults(bindingResult, bookDTO.getClass().getSimpleName());
		return  MessageInfo.success(bookService.createBook(bookDTO), Arrays.asList("Book created successfully"));
	}

	@PostMapping(UPDATE)
	public MessageInfo updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult){
		BindingResultsValidator.validateBindingResults(bindingResult, bookDTO.getClass().getSimpleName());
		return  MessageInfo.success(bookService.updateBook(id, bookDTO), Arrays.asList("Book updated successfully"));
	}

	@DeleteMapping(REMOVE)
	public MessageInfo deleteBook(@PathVariable Long id) throws ResourceNotFoundException {
		bookService.deleteBook(id);
		return MessageInfo.success(null, Arrays.asList("Book with ID = " + id.toString() + " removed successfully"));
	}
}
