package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Book getBookById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book"));
	}

	public MessageInfo createBook(BookDTO bookDTO) {
		ModelMapper mapper = new ModelMapper();
		Book book = mapper.map(bookDTO, Book.class);
		return saveBook(book, "Book created successfully");
	}

	public MessageInfo updateBook(Long id, BookDTO bookDTO) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		book.setComments(bookDTO.getComments());
		book.setBookDetails(bookDTO.getBookDetails());
		return saveBook(book, "Book updated successfully");
	}

	public void deleteBook(Long id)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book"));
		bookRepository.delete(book);
	}

	private MessageInfo saveBook(Book book, String defaultMessageForSuccess){
		try {
			book = bookRepository.save(book);
		}
		catch (ConstraintViolationException exc){
			return MessageInfo.getErrors(exc);
		}
		return new MessageInfo(true, book, Arrays.asList(defaultMessageForSuccess));
	}
}
