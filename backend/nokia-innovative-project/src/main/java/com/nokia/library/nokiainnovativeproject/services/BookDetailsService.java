package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.exceptions.ValidationException;
import com.nokia.library.nokiainnovativeproject.repositories.AuthorRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookCategoryRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookDetailsService {

	private final BookDetailsRepository bookDetailsRepository;
	private final AuthorRepository authorRepository;
	private final BookCategoryRepository bookCategoryRepository;

	public List<BookDetails> getAllBookDetails() {
		List<BookDetails> list = bookDetailsRepository.findAll();
		for(BookDetails bookDetails : list) {
			Hibernate.initialize(bookDetails.getAuthors());
			Hibernate.initialize(bookDetails.getCategories());
			Hibernate.initialize(bookDetails.getReviews());
		}
		return list;
	}

	public BookDetails getBookDetailsById(Long id) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		Hibernate.initialize(bookDetails.getAuthors());
		Hibernate.initialize(bookDetails.getCategories());
		Hibernate.initialize(bookDetails.getReviews());
		return bookDetails;
	}

    @Transactional
	public BookDetails createBookDetails(BookDetailsDTO bookDetailsDTO) {
		MessageInfo.isThisEntityUnique(bookDetailsRepository.countBookDetailsByIsbnAndAndTitle(
				bookDetailsDTO.getIsbn(), bookDetailsDTO.getTitle()), "book details");

		ModelMapper mapper = new ModelMapper();
		BookDetails bookDetails = mapper.map(bookDetailsDTO, BookDetails.class);
		return bookDetailsRepository.save(persistingRequiredEntities(bookDetails, bookDetailsDTO));
	}

	public BookDetails updateBookDetails(Long id, BookDetailsDTO bookDetailsDTO) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		bookDetails.setIsbn(bookDetailsDTO.getIsbn());
		bookDetails.setTitle(bookDetailsDTO.getTitle());
		bookDetails.setDescription(bookDetailsDTO.getDescription());
		bookDetails.setCoverPictureUrl(bookDetailsDTO.getCoverPictureUrl());
		bookDetails.setPublicationDate(bookDetailsDTO.getPublicationDate());
		return bookDetailsRepository.save(persistingRequiredEntities(bookDetails, bookDetailsDTO));
	}

	public void deleteBookDetails(Long id) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		try {
			bookDetailsRepository.delete(bookDetails);
		}catch(DataIntegrityViolationException e) {
			throw new ValidationException("The book details you are trying to delete is assigned to a book. You can't delete it.");
		}
	}

	private BookDetails persistingRequiredEntities(BookDetails bookDetails, BookDetailsDTO bookDetailsDTO) {

		List<Author> authors = bookDetailsDTO.getAuthors();

		List<Author> authorsToRemove = authors.stream().filter(author -> author.getId() != null).collect(Collectors.toList());

		Iterable<Long> iterable = authorsToRemove.stream().map(Author::getId).collect(Collectors.toList());

		List<Author> existingAuthors = authorRepository.findAllById(iterable);

		List<BookCategory> categories = bookDetailsDTO.getCategories();
		List<BookCategory> categoriesToRemove = categories.stream().filter(
				bookCategory -> bookCategory.getId() != null).collect(Collectors.toList());
		iterable = categoriesToRemove.stream().map(BookCategory::getId).collect(Collectors.toList());
		List<BookCategory> existingCategories = bookCategoryRepository.findAllById(iterable);

		int size = categories.size();
		categories.removeAll(categoriesToRemove);
		categories.addAll(existingCategories);
		bookDetails.setCategories(categories);
		if(categories.size() != size)
			throw new ResourceNotFoundException("category");

		size = authors.size();
		authors.removeAll(authorsToRemove);
		authors.addAll(existingAuthors);
		bookDetails.setAuthors(authors);
		if(authors.size() != size)
			throw new ResourceNotFoundException("author");

		return bookDetails;
	}
}