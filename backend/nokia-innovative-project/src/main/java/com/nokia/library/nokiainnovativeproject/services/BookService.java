package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.*;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookStatusRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import com.nokia.library.nokiainnovativeproject.utils.BookStatusEnum;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;
	private final BookDetailsRepository bookDetailsRepository;
	private final BookStatusRepository bookStatusRepository;
	private final BookStatusService bookStatusService;
	private final UserRepository userRepository;


	public List<Book> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		for (Book book : books) {
			Hibernate.initialize(book.getBookDetails());
			Hibernate.initialize(book.getStatus());
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
		book.setAvailableDate(LocalDateTime.now());
		return bookRepository.save(persistRequiredEntities(book, bookDTO));
	}

	public Book updateBook(Long id, BookDTO bookDTO) {
		Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book"));
		book.setComments(bookDTO.getComments());
		book.setSignature(bookDTO.getSignature());
		book.getBookDetails().setIsRemovable(true);
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

		if(!book.getStatus().getStatusName().equals("AVAILABLE") && !book.getStatus().getStatusName().equals("UNAVAILABLE"))
			return;
		book.getBookDetails().setIsRemovable(bookRepository.countBooksByBookDetails(book.getBookDetails()) == 1);
		bookRepository.delete(book);
	}

	public Book changeState(Book book, Long newStatusId, Integer days, User newOwner) {
		BookStatus newStatus = bookStatusService.getBookStatusById(newStatusId);
		book.setStatus(newStatus);
		LocalDateTime oldAvailableDate = book.getAvailableDate();
		if(oldAvailableDate == null){
			oldAvailableDate = LocalDateTime.now();
			book.setAvailableDate(oldAvailableDate);
		}
		if (days == 30) {
			book.setAvailableDate(oldAvailableDate.plusMonths(1));
		} else if (days == -30) {
			book.setAvailableDate(oldAvailableDate.minusMonths(1));
		} else if (0 < days && days < 30) {
			book.setAvailableDate(oldAvailableDate.plusDays(days));
		} else if (-30 < days && days < 0) {
			book.setAvailableDate(oldAvailableDate.minusDays(days));
		} else if (days == 0){
			book.setAvailableDate(LocalDateTime.now());
		}
		if(newOwner != null){
			book.setCurrentOwnerId(newOwner.getId());
		}
//		System.out.println(book);
		return book;
	}

	public Book lockBook(String signature) {
		// TODO: add admin authorization, add condition about book status, can only equal 1, add  exceptions
		Book bookToLock = bookRepository.findBySignature(signature);
		return changeState(bookToLock, BookStatusEnum.UNAVAILABLE.getStatusId(), 0, null);
	}

	public Book unlockBook(String signature) {
		// TODO: add admin authorization, add condition about book status, can only equal 5, add exceptions
		Book bookToUnlock = bookRepository.findBySignature(signature);
		return changeState(bookToUnlock, BookStatusEnum.AVAILABLE.getStatusId(), 0, null);
	}

}