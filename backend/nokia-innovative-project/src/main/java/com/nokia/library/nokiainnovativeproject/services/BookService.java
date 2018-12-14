package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	public List<Book> getAllBooks() {
		List<Book> list = bookRepository.findAll();
		for(Book book : list) {
			Hibernate.initialize(book.getBookDetails().getAuthors());
			Hibernate.initialize(book.getBookDetails().getReviews());
			Hibernate.initialize(book.getBookDetails().getCategories());
			Hibernate.initialize(book.getBookDetails().getBooks());
			book.getBookDetails().setBooks(new ArrayList<>());
		}
		return list;

	}

	public Book getBookById(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		Hibernate.initialize(book.getBookDetails());
		book.getBookDetails().setBooks(new ArrayList<>());
		return book;
	}

	public Book createBook(BookDTO bookDTO) {
		ModelMapper mapper = new ModelMapper();
		Book book = mapper.map(bookDTO, Book.class);
		return bookRepository.save(book);
	}

	public Book updateBook(Long id, BookDTO bookDTO) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		book.setComments(bookDTO.getComments());
		book.setBookDetails(bookDTO.getBookDetails());
		return bookRepository.save(book);
	}

	public void deleteBook(Long id)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book"));
		bookRepository.delete(book);
	}
}
