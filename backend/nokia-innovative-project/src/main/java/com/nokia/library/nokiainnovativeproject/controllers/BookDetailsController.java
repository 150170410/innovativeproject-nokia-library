package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.services.BookDetailsService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.LIBRARY)
public class BookDetailsController {

	private final BookDetailsService bookDetailsService;

	@GetMapping(Mappings.BK_DET)
	public List<BookDetails> getAllBooks() {
		return bookDetailsService.getAllBookDetails();
	}

	@GetMapping(Mappings.BK_DET_ID)
	public BookDetails getBookById(@PathVariable Long id) {
		return bookDetailsService.getBookDetailsById(id);
	}

	@PostMapping(Mappings.BK_DET)
	public BookDetails createBook(@RequestBody BookDetailsDTO bookDetailsDTO) {
		return bookDetailsService.createBookDetails(bookDetailsDTO);
	}

	@PostMapping(Mappings.BK_DET_ID)
	public BookDetails updateBook(@PathVariable Long id, @RequestBody BookDetailsDTO bookDetailsDTO){
		return bookDetailsService.updateBookDetails(id, bookDetailsDTO);
	}

	@DeleteMapping(Mappings.BK_DET_ID)
	public void deleteBook(@PathVariable Long id) throws ResourceNotFoundException {
		bookDetailsService.deleteBookDetails(id);
	}
}
