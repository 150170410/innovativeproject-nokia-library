package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookStatusRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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
		List<Book> books = bookRepository.findAll();
		for (Book book : books) {
			Hibernate.initialize(book.getStatus());
			Hibernate.initialize(book.getBookDetails());
		}
		return books;
	}

	public Book getBookById(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		Hibernate.initialize(book.getBookDetails());
		Hibernate.initialize(book.getStatus());
		return book;
	}

	public List<Book> getAllBooksByBookDetailsId(Long id) {
		return bookRepository.getBooksByBookDetailsId(id);
	}


	public Book createBook(BookDTO bookDTO) {
		ModelMapper mapper = new ModelMapper();
		Book book = mapper.map(bookDTO, Book.class);
		return bookRepository.save(persistRequiredEntities(book, bookDTO));
	}

	public Book updateBook(Long id, BookDTO bookDTO) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		book.setComments(bookDTO.getComments());
		book.setSignature(bookDTO.getSignature());
		return bookRepository.save(persistRequiredEntities(book, bookDTO));
	}

	private Book persistRequiredEntities(Book book, BookDTO bookDTO) {
		Hibernate.initialize(book.getBookDetails());
		Hibernate.initialize(book.getStatus());
		book.setBookDetails(bookDetailsRepository.findById(bookDTO.getBookDetailsId()).orElseThrow(
				() -> new ResourceNotFoundException("book details")));
		book.setStatus(bookStatusRepository.findById(bookDTO.getBookStatusId()).orElseThrow(
				() -> new ResourceNotFoundException("status")));
		return book;
	}

	public void deleteBook(Long id)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book"));
		bookRepository.delete(book);
	}
}