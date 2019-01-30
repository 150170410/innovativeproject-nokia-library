package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.entities.BookStatus;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookStatusRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookStatusService {
	private final BookStatusRepository bookStatusRepository;

	public BookStatus getBookStatusById(Long id) {
		BookStatus bookStatus = bookStatusRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book status"));
		Hibernate.initialize(bookStatus.getStatusName());
		return bookStatus;
	}
}