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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

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

		List<Author> authorsToRemove = new ArrayList<>();
		List<Author> existingAuthors = new ArrayList<>();
		List<Author> authors = bookDetailsDTO.getAuthors();
		for(Author author : authors) {
			if(author.getId() != null){
				authorsToRemove.add(author);
				existingAuthors.add(authorRepository.findById(author.getId()).orElseThrow(()-> new ResourceNotFoundException("author")));
			}
		}
		List<BookCategory> categoriesToRemove = new ArrayList<>();
		List<BookCategory> existingCategories = new ArrayList<>();
		List<BookCategory> categories = bookDetailsDTO.getCategories();
		for(BookCategory bookCategory : categories) {
			if(bookCategory.getId() != null){
				categoriesToRemove.add(bookCategory);
				existingCategories.add(bookCategoryRepository.findById(bookCategory.getId()).orElseThrow(()-> new ResourceNotFoundException("category")));
			}
		}
		categories.removeAll(categoriesToRemove);
		categories.addAll(existingCategories);
		bookDetails.setCategories(categories);

		authors.removeAll(authorsToRemove);
		authors.addAll(existingAuthors);
		bookDetails.setAuthors(authors);
		return bookDetailsRepository.save(bookDetails);
	}

	public BookDetails updateBookDetails(Long id, BookDetailsDTO bookDetailsDTO) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		bookDetails.setIsbn(bookDetailsDTO.getIsbn());
		bookDetails.setTitle(bookDetailsDTO.getTitle());
		bookDetails.setDescription(bookDetailsDTO.getDescription());
		bookDetails.setCoverPictureUrl(bookDetailsDTO.getCoverPictureUrl());
		bookDetails.setPublicationDate(bookDetailsDTO.getPublicationDate());
		bookDetails.setTableOfContents(bookDetailsDTO.getTableOfContents());

		List<Author> authorsToRemove = new ArrayList<>();
		List<Author> existingAuthors = new ArrayList<>();
		List<Author> authors = bookDetailsDTO.getAuthors();
		for(Author author : authors) {
			if(author.getId() != null){
				authorsToRemove.add(author);
				existingAuthors.add(authorRepository.findById(author.getId()).orElseThrow(()-> new ResourceNotFoundException("author")));
			}
		}
		List<BookCategory> categoriesToRemove = new ArrayList<>();
		List<BookCategory> existingCategories = new ArrayList<>();
		List<BookCategory> categories = bookDetailsDTO.getCategories();
		for(BookCategory bookCategory : categories) {
			if(bookCategory.getId() != null){
				categoriesToRemove.add(bookCategory);
				existingCategories.add(bookCategoryRepository.findById(bookCategory.getId()).orElseThrow(()-> new ResourceNotFoundException("category")));
			}
		}
		categories.removeAll(categoriesToRemove);
		categories.addAll(existingCategories);
		bookDetails.setCategories(categories);

		authors.removeAll(authorsToRemove);
		authors.addAll(existingAuthors);
		bookDetails.setAuthors(authors);

		return bookDetailsRepository.save(bookDetails);
	}

	public void deleteBookDetails(Long id) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		bookDetailsRepository.delete(bookDetails);
	}
}
