package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookCategoryDTO;
import com.nokia.library.nokiainnovativeproject.services.BookCategoryService;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + BOOK_CATEGORY)
public class BookCategoryController {

	private final BookCategoryService bookCategoryService;

	@GetMapping(GET_ALL)
	public ResponseEntity getAllBookCategories() {
		return MessageInfo.success(bookCategoryService.getAllBookCategories(),
				Arrays.asList(Messages.get(LIST_OF) + "book categories."));
	}

	@GetMapping(GET_ONE)
	public ResponseEntity getBookCategoryById(@PathVariable Long id) {
		return MessageInfo.success(bookCategoryService.getBookCategoryById(id),
				Arrays.asList("Book category" + Messages.get(REQUESTED)));
	}

	@PostMapping(CREATE)
	public ResponseEntity createBookCategory(@RequestBody @Valid BookCategoryDTO bookCategoryDTO, BindingResult bindingResult) {
		MessageInfo.validateBindingResults(bindingResult);
		return MessageInfo.success(bookCategoryService.createBookCategory(bookCategoryDTO),
				Arrays.asList("Book category" + Messages.get(CREATED_SUCCESSFULLY)));
	}

	@PostMapping(UPDATE)
	public ResponseEntity updateBookCategory(@PathVariable Long id, @RequestBody @Valid
			BookCategoryDTO bookCategoryDTO, BindingResult bindingResult) {
		MessageInfo.validateBindingResults(bindingResult);
		return MessageInfo.success(bookCategoryService.updateBookCategory(id, bookCategoryDTO),
				Arrays.asList("Book category" + Messages.get(UPDATED_SUCCESSFULLY)));
	}

	@DeleteMapping(REMOVE)
	public ResponseEntity deleteBookCategory(@PathVariable Long id) {
		bookCategoryService.deleteBookCategory(id);
		return MessageInfo.success(null, Arrays.asList("Book category" + Messages.get(REMOVED_SUCCESSFULLY)));
	}
}