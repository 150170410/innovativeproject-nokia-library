package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookCategoryDTO;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.exceptions.ValidationException;
import com.nokia.library.nokiainnovativeproject.repositories.BookCategoryRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCategoryService  {

	private final BookCategoryRepository bookCategoryRepository;
	private final BookDetailsRepository bookDetailsRepository;

	public List<BookCategory> getAllBookCategories() {
		return bookCategoryRepository.findAll();
	}

	public BookCategory getBookCategoryById(Long id) {
		return bookCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book category"));
	}

	public BookCategory createBookCategory(BookCategoryDTO bookCategoryDTO) {
		MessageInfo.isThisEntityUnique(bookCategoryRepository.countBookCategoriesByBookCategoryName(
				bookCategoryDTO.getBookCategoryName()), "book category");

		ModelMapper mapper = new ModelMapper();
		BookCategory bookCategory = mapper.map(bookCategoryDTO, BookCategory.class);
		bookCategory.setIsRemovable(true);
		return bookCategoryRepository.save(bookCategory);
	}

	public BookCategory updateBookCategory(Long id, BookCategoryDTO bookCategoryDTO) {
		BookCategory bookCategory = bookCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book category"));
		bookCategory.setBookCategoryName(bookCategoryDTO.getBookCategoryName());
		return bookCategoryRepository.save(bookCategory);
	}

	public void deleteBookCategory(Long id) {
		BookCategory bookCategory = bookCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book category"));
		if (bookDetailsRepository.countBookDetailsByCategories(Arrays.asList(bookCategory)) > 0) {
			throw new ValidationException("The category" + IS_ASSIGNED_CANT_DELETE);
		}
		bookCategoryRepository.delete(bookCategory);
	}
}