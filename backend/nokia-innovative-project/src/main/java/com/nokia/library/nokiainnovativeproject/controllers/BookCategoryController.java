package com.nokia.library.nokiainnovativeproject.controllers;


		import com.nokia.library.nokiainnovativeproject.DTOs.BookCategoryDTO;
		import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
		import com.nokia.library.nokiainnovativeproject.services.BookCategoryService;
		import com.nokia.library.nokiainnovativeproject.utils.Mappings;
		import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
		import lombok.RequiredArgsConstructor;
		import org.springframework.web.bind.annotation.*;

		import javax.validation.Valid;
		import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_CATEGORY)
public class BookCategoryController {

	private final BookCategoryService bookCategoryService;

	@GetMapping(Mappings.GET_ALL)
	public MessageInfo getAllBookCategories() {
		return new MessageInfo(true, bookCategoryService.getAllBookCategories(), "list of bookCategories");
	}

	@GetMapping(Mappings.GET_ONE)
	public MessageInfo getBookCategoryById(@PathVariable Long id) {
		return new MessageInfo(true, bookCategoryService.getBookCategoryById(id), "BookCategory of ID = " + id.toString());
	}

	@PostMapping(Mappings.SAVE)
	public MessageInfo createBookCategory(@RequestBody @Valid BookCategoryDTO bookCategoryDTO) {
		return new MessageInfo(true, bookCategoryService.createBookCategory(bookCategoryDTO), "BookCategory created successfully");
	}

	@PostMapping(Mappings.UPDATE)
	public MessageInfo updateBookCategory(@PathVariable Long id, @RequestBody @Valid BookCategoryDTO bookCategoryDTO) {
		return new MessageInfo(true, bookCategoryService.updateBookCategory(id, bookCategoryDTO), "BookCategory updated successfully");
	}

	@DeleteMapping(Mappings.REMOVE)
	public MessageInfo deleteBookCategory(@PathVariable Long id) {
		bookCategoryService.deleteBookCategory(id);
		return new MessageInfo(true, null, "BookCategory with ID = " + id.toString() + " removed successfully");
	}
}
