package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.OldBook;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.LIBRARY)
public class OldBookController {

	private final BookService bookService;

	@GetMapping(Mappings.GET_ALL)
	public List<OldBook> getAllBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping(Mappings.GET_ONE)
	public OldBook getBookById(@PathVariable Long id) {
		return bookService.getBookById(id);
	}

	@PostMapping(Mappings.SAVE)
	public OldBook createBook(@RequestBody BookDTO bookDTO) {
		ModelMapper mapper = new ModelMapper();
		OldBook oldBook = mapper.map(bookDTO, OldBook.class);
		return bookService.createBook(oldBook);
	}

	@PostMapping(Mappings.UPDATE)
	public OldBook updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
		return bookService.updateBook(id, bookDTO);
	}

	@DeleteMapping(Mappings.REMOVE)
	public void deleteBook(@PathVariable Long id)
			throws ResourceNotFoundException {
		bookService.deleteBook(id);
	}
}