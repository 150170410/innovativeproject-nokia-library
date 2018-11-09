package com.nokia.library.nokiainnovativeproject.controllers;


		import com.nokia.library.nokiainnovativeproject.DTOs.BookCategoryDTO;
		import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
		import com.nokia.library.nokiainnovativeproject.services.BookCategoryService;
		import com.nokia.library.nokiainnovativeproject.utils.Mappings;
		import lombok.RequiredArgsConstructor;
		import org.springframework.web.bind.annotation.*;

		import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_CATEGORY)
public class BookCategoryController {

	private final BookCategoryService bookCategoryService;

	@GetMapping(Mappings.GET_ALL)
	public List<BookCategory> getAllBookCategories() {
		return bookCategoryService.getAllBookCategories();
	}

	@GetMapping(Mappings.GET_ONE)
	public BookCategory getBookCategoryById(@PathVariable Long id) {
		return bookCategoryService.getBookCategoryById(id);
	}

	@PostMapping(Mappings.SAVE)
	public BookCategory createBook(@RequestBody BookCategoryDTO bookCategoryDTO) {
		return bookCategoryService.createBookCategory(bookCategoryDTO);
	}

	@PostMapping(Mappings.UPDATE)
	public BookCategory updateBook(@PathVariable Long id, @RequestBody BookCategoryDTO bookCategoryDTO) {
		return bookCategoryService.updateBookCategory(id, bookCategoryDTO);
	}

	@DeleteMapping(Mappings.REMOVE)
	public void deleteBookCategory(@PathVariable Long id) {
		bookCategoryService.deleteBookCategory(id);
	}
}
