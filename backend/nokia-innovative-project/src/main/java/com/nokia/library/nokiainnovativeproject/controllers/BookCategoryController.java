package com.nokia.library.nokiainnovativeproject.controllers;


		import com.nokia.library.nokiainnovativeproject.DTOs.BookCategoryDTO;
		import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
		import com.nokia.library.nokiainnovativeproject.services.BookCategoryService;
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
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_CATEGORY)
public class BookCategoryController {

	private final BookCategoryService bookCategoryService;

	@GetMapping(Mappings.GET_ALL)
	public MessageInfo getAllBookCategories() {
		return MessageInfo.success(bookCategoryService.getAllBookCategories(), Arrays.asList("list of bookCategories"));
	}

	@GetMapping(Mappings.GET_ONE)
	public MessageInfo getBookCategoryById(@PathVariable Long id) {
		return MessageInfo.success(bookCategoryService.getBookCategoryById(id), Arrays.asList("BookCategory of ID = " + id.toString()));
	}

	@PostMapping(Mappings.CREATE)
	public MessageInfo createBookCategory(@RequestBody @Valid BookCategoryDTO bookCategoryDTO, BindingResult bindingResult) {
		return getMessageInfo(bindingResult , bookCategoryDTO, "BookCategory created successfully");
	}

	@PostMapping(Mappings.UPDATE)
	public MessageInfo updateBookCategory(@PathVariable Long id, @RequestBody @Valid BookCategoryDTO bookCategoryDTO, BindingResult bindingResult){
		return getMessageInfo(bindingResult, bookCategoryDTO , "BookCategory updated successfully");
	}

	@DeleteMapping(Mappings.REMOVE)
	public MessageInfo deleteBookCategory(@PathVariable Long id) {
		bookCategoryService.deleteBookCategory(id);
		return MessageInfo.success(null, Arrays.asList("BookCategory with ID = " + id.toString() + " removed successfully"));
	}

	private MessageInfo getMessageInfo(BindingResult bindingResult, BookCategoryDTO bookCategoryDTO, String defaultMessageForSuccess) {
        if(bindingResult.hasErrors()){
            List<String> errorsList = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return MessageInfo.failure(errorsList);
        }
        return MessageInfo.success(bookCategoryService.createBookCategory(bookCategoryDTO), Arrays.asList(defaultMessageForSuccess));
    }
}
