package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOKS)
public class BookController {

	private final BookService bookService;

	@GetMapping(Mappings.GET_ALL)
	public MessageInfo getAllBooks() {
		return MessageInfo.success(bookService.getAllBooks(), Arrays.asList("list of books"));
	}

	@GetMapping(Mappings.GET_ONE)
	public MessageInfo getBookById(@PathVariable Long id) {
		return MessageInfo.success(bookService.getBookById(id), Arrays.asList("Book of ID = " + id.toString()));
	}

	@PostMapping(Mappings.CREATE)
	public MessageInfo createBook(@RequestBody @Valid  BookDTO bookDTO, BindingResult bindingResult){
		return getMessageInfo(bindingResult, bookDTO, "Book created successfully");
	}

	@PostMapping(Mappings.UPDATE)
	public MessageInfo updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult){
		return getMessageInfo(bindingResult, bookDTO, "Book updated successfully");
	}

	@DeleteMapping(Mappings.REMOVE)
	public MessageInfo deleteBook(@PathVariable Long id) throws ResourceNotFoundException {
		bookService.deleteBook(id);
		return MessageInfo.success(null, Arrays.asList("Book with ID = " + id.toString() + " removed successfully"));
	}

	private MessageInfo getMessageInfo(BindingResult bindingResult, BookDTO bookDTO, String defaultMessageForSuccess) {
        if(bindingResult.hasErrors()){
            List<String> errorsList = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return MessageInfo.failure(errorsList);
        }
        return MessageInfo.success(bookService.createBook(bookDTO), Arrays.asList(defaultMessageForSuccess));
    }
}