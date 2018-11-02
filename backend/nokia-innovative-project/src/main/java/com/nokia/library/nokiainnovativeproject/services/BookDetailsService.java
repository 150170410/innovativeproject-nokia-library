package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;

import java.util.List;

public interface BookDetailsService {

    List<BookDetails> getAllBookDetails();

    BookDetails getBookDetailsById(Long id);

    BookDetails createBookDetails(BookDetailsDTO bookDetailsDTO);

    BookDetails updateBookDetails(Long id, BookDetailsDTO bookDetailsDTO);

    void deleteBookDetails(Long id);
}
