package com.nokia.library.nokiainnovativeproject.services;


import com.nokia.library.nokiainnovativeproject.DTOs.BookCategoryDTO;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCategoryService {

	private final BookCategoryRepository bookCategoryRepository;

	public List<BookCategory> getAllBookCategories() {
		return bookCategoryRepository.findAll();
	}

	public BookCategory getBookCategoryById(Long id) {
		return bookCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BookCategory", "id", id));
	}

	public BookCategory createBookCategory(BookCategoryDTO bookCategoryDTO) {
		ModelMapper mapper = new ModelMapper();
		BookCategory bookCategory = mapper.map(bookCategoryDTO, BookCategory.class);
		return bookCategoryRepository.save(bookCategory);
	}

	public BookCategory updateBookCategory(Long id, BookCategoryDTO bookCategoryDTO) {
		BookCategory bookCategory = bookCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BookCategory", "id", id));
		bookCategory.setBookCategoryName(bookCategoryDTO.getBookCategoryName());
		return bookCategoryRepository.save(bookCategory);
	}

	public void deleteBookCategory(Long id) {
		BookCategory bookCategory = bookCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("BookCategory", "id", id));
		bookCategoryRepository.delete(bookCategory);

	}

}
