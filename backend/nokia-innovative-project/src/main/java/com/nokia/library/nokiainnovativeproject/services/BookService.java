package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookStatusRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final BookDetailsRepository bookDetailsRepository;
	private final BookStatusRepository bookStatusRepository;

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book getBookById(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
	}
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public Book createBook(BookDTO bookDTO) {
		System.out.println(bookDTO);
		ModelMapper mapper = new ModelMapper();
		Book book = mapper.map(bookDTO, Book.class);
		System.out.println(book);
		book.setBookDetails(bookDetailsRepository.getOne(bookDTO.getBookDetailsId()));
		book.setStatus(bookStatusRepository.getOne(bookDTO.getBookStatusId()));
        System.out.println(book);
		return bookRepository.save(book);
	}

	public Book updateBook(Long id, BookDTO bookDTO) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		book.setComments(bookDTO.getComments());
		book.setSignature(bookDTO.getSignature());
		return bookRepository.save(book);
	}

	public void deleteBook(Long id)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book"));
		bookRepository.delete(book);
	}
}