package com.nokia.library.nokiainnovativeproject.servicesImpl;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.OldBook;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	public List<OldBook> getAllBooks() {
		return bookRepository.findAll();
	}

	public OldBook getBookById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("OldBook", "id", id));
	}

	public OldBook createBook(OldBook oldBook) {
		return bookRepository.save(oldBook);
	}

	public OldBook updateBook(Long id, BookDTO bookDTO) {
		OldBook oldBook = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("OldBook", "id", id));
		oldBook.setAuthorName(bookDTO.getAuthorName());
		oldBook.setAuthorSurname(bookDTO.getAuthorSurname());
		oldBook.setTitle(bookDTO.getTitle());
		return bookRepository.save(oldBook);
	}

	public void deleteBook(Long id)
			throws ResourceNotFoundException {
		OldBook oldBook = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("OldBook", "id", id));
		bookRepository.delete(oldBook);
	}
}
