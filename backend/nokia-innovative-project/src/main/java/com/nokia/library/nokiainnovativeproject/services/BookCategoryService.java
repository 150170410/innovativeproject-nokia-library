package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookCategoryDTO;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;

import java.util.List;

public interface BookCategoryService {

    List<BookCategory> getAllBookCategories();

    BookCategory getBookCategoryById(Long id);

    BookCategory createBookCategory(BookCategoryDTO bookCategoryDTO);

    BookCategory updateBookCategory(Long id, BookCategoryDTO bookCategoryDTO);

    void deleteBookCategory(Long id);
}
