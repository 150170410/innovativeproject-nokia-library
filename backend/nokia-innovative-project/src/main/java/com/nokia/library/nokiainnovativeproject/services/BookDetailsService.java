package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.AuthorRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookCategoryRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
			Hibernate.initialize(bookDetails.getBooks());
		}
		return list;
	}

	public BookDetails getBookDetailsById(Long id) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		Hibernate.initialize(bookDetails.getAuthors());
		Hibernate.initialize(bookDetails.getCategories());
		Hibernate.initialize(bookDetails.getReviews());
		Hibernate.initialize(bookDetails.getBooks());
		return bookDetails;
	}

	public BookDetails createBookDetails(BookDetailsDTO bookDetailsDTO) {
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
		bookDetails.setTableOfContents(bookDetailsDTO.getTableOfContents());
		return bookDetailsRepository.save(persistingRequiredEntities(bookDetails, bookDetailsDTO));
	}

	public void deleteBookDetails(Long id) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		bookDetailsRepository.delete(bookDetails);
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