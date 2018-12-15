package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final BookDetailsRepository bookDetailsRepository;

	public List<Book> getAllBooks() {
		List<Book> list = bookRepository.findAll();
		for(Book book : list) {
			book.getBookDetails().setBooks(new ArrayList<>());
		}
		return list;

	}

	public Book getBookById(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		book.getBookDetails().setBooks(new ArrayList<>());
		return book;
	}

	public Book createBook(BookDTO bookDTO) {
		ModelMapper mapper = new ModelMapper();
		Book book = mapper.map(bookDTO, Book.class);
		return bookRepository.save(persistingRequiredEntities(book, bookDTO));
	}

	public Book updateBook(Long id, BookDTO bookDTO) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		book.setComments(bookDTO.getComments());
		return bookRepository.save(persistingRequiredEntities(book, bookDTO));
	}

	public void deleteBook(Long id)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book"));
		bookRepository.delete(book);
	}

	private Book persistingRequiredEntities(Book book, BookDTO bookDTO) {

		BookDetails bookDetails = bookDTO.getBookDetails();
		if(bookDetails.getId() != null){
			bookDetails = bookDetailsRepository.findById(bookDetails.getId()).orElseThrow(()-> new ResourceNotFoundException("book details"));
			book.setBookDetails(bookDetails);
		}
		return book;
	}
}