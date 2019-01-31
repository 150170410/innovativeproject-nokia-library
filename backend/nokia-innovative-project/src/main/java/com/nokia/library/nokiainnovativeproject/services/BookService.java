package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.BookStatus;
import com.nokia.library.nokiainnovativeproject.entities.BookWithOwner;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.exceptions.InvalidBookStateException;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookStatusRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import com.nokia.library.nokiainnovativeproject.utils.BookStatusEnum;
import com.nokia.library.nokiainnovativeproject.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final BookDetailsRepository bookDetailsRepository;
	private final BookStatusRepository bookStatusRepository;
	private final BookStatusService bookStatusService;
	private final UserService userService;
	private final UserRepository userRepository;

	public List<Book> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		for (Book book : books) {
			Hibernate.initialize(book.getBookDetails());
			Hibernate.initialize(book.getStatus());
		}
		return books;
	}

	public List<BookWithOwner> getAllBookWithOwner() {
		List<Book> books = getAllBooks();
		List<BookWithOwner> booksWithOwner = new ArrayList<>();
		for(Book book : books) {
			booksWithOwner.add(getBookWithOwner(book));
		}
		return booksWithOwner;
	}

	public BookWithOwner getBookWithOwnerById(Long id) {
		Book book = getBookById(id);
		return getBookWithOwner(book);
	}

	private BookWithOwner getBookWithOwner(Book book) {
		ModelMapper modelMapper = new ModelMapper();
		BookWithOwner bookWithOwner = modelMapper.map(book, BookWithOwner.class);
		bookWithOwner.setActualOwner(userRepository.findById(book.getCurrentOwnerId()).orElseThrow(
				() -> new ResourceNotFoundException("user")));
		return bookWithOwner;
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
		book.setAvailableDate(LocalDateTime.now());
		book.setCurrentOwnerId(userService.getLoggedInUser().getId());
		return bookRepository.save(persistRequiredEntities(book, bookDTO));
	}

	public Book updateBook(Long id, BookDTO bookDTO) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		book.setComments(bookDTO.getComments());
		book.setSignature(bookDTO.getSignature());
		book.getBookDetails().setIsRemovable(true);
		book.setCurrentOwnerId(userService.getLoggedInUser().getId());
		return bookRepository.save(persistRequiredEntities(book, bookDTO));
	}

	private Book persistRequiredEntities(Book book, BookDTO bookDTO) {
		Hibernate.initialize(book.getBookDetails());
		Hibernate.initialize(book.getStatus());
		book.setBookDetails(bookDetailsRepository.findById(bookDTO.getBookDetailsId()).orElseThrow(
				() -> new ResourceNotFoundException("book details")));
		book.setStatus(bookStatusRepository.findById(bookDTO.getBookStatusId()).orElseThrow(
				() -> new ResourceNotFoundException("status")));
		book.getBookDetails().setIsRemovable(false);
		return book;
	}

	public void deleteBook(Long id)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book"));

		if (!book.getStatus().getStatusName().equals("AVAILABLE") && !book.getStatus().getStatusName().equals("UNAVAILABLE"))
			return;
		book.getBookDetails().setIsRemovable(bookRepository.countBooksByBookDetails(book.getBookDetails()) == 1);
		bookRepository.delete(book);
	}

	public Book changeState(Book book, Long newStatusId, Integer days, User newOwner) {
		BookStatus newStatus = bookStatusService.getBookStatusById(newStatusId);
		book.setStatus(newStatus);
		LocalDateTime oldAvailableDate = book.getAvailableDate();
		if (oldAvailableDate == null) {
			oldAvailableDate = LocalDateTime.now();
			book.setAvailableDate(oldAvailableDate);
		}
		if (days == 31) {
			book.setAvailableDate(oldAvailableDate.plusMonths(1));
		} else if (days == -31) {
			book.setAvailableDate(oldAvailableDate.minusMonths(1));
		} else if (0 < days && days < 31) {
			book.setAvailableDate(oldAvailableDate.plusDays(days));
		} else if (-31 < days && days < 0) {
			book.setAvailableDate(oldAvailableDate.minusDays(-1 * days));
		} else if (days == 0) {
			book.setAvailableDate(LocalDateTime.now());
		}
		if (newOwner != null) {
			book.setCurrentOwnerId(newOwner.getId());
		}
		return book;
	}

	public Book lockBook(String signature) {
		Book bookToLock = bookRepository.findBySignature(signature);
		if (!bookToLock.getStatus().getId().equals(BookStatusEnum.AVAILABLE.getStatusId())) {
			throw new InvalidBookStateException(Constants.MessageTypes.BOOK_RESERVED);
		}
		return changeState(bookToLock, BookStatusEnum.UNAVAILABLE.getStatusId(), 0, null);
	}

	public Book unlockBook(String signature) {
		Book bookToUnlock = bookRepository.findBySignature(signature);
		if (!bookToUnlock.getStatus().getId().equals(BookStatusEnum.UNAVAILABLE.getStatusId())) {
			throw new InvalidBookStateException(Constants.MessageTypes.BOOK_RESERVED);
		}
		return changeState(bookToUnlock, BookStatusEnum.AVAILABLE.getStatusId(), 0, null);
	}
}