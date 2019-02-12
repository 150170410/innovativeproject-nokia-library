package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.*;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.exceptions.ValidationException;
import com.nokia.library.nokiainnovativeproject.repositories.*;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookDetailsService {

	private final BookDetailsRepository bookDetailsRepository;
	private final AuthorRepository authorRepository;
	private final BookCategoryRepository bookCategoryRepository;
	private final BookService bookService;
	private final BookRepository bookRepository;
	private final UserRepository userRepository;


	public List<BookDetails> getAllBookDetails() {
		List<BookDetails> booksDetails = bookDetailsRepository.findAll();
		for(BookDetails bookDetails : booksDetails) {
			Hibernate.initialize(bookDetails.getAuthors());
			Hibernate.initialize(bookDetails.getCategories());
		}
		return booksDetails;
	}

	public List<BookDetailsWithBooks> getAvailableBookDetails() {

		List<BookDetails> booksDetails = bookDetailsRepository.findAll();
		//it's impossible to create book without bookDetails (NotNull annotation)
		List<Book> books = bookRepository.findAll();

		ModelMapper mapper = new ModelMapper();
		HashMap<Long, BookDetailsWithBooks> booksDetailsWithBooks = new HashMap<>();
		for(BookDetails bookDetails : booksDetails) {
			booksDetailsWithBooks.put(bookDetails.getId(), mapper.map(bookDetails, BookDetailsWithBooks.class));
		}

		for(Book book : books) {
			Long bookDetailsId = book.getBookDetails().getId();
			BookDetailsWithBooks bookDetailsWithBooks = booksDetailsWithBooks.get(bookDetailsId);
			List<BookWithoutBookDetails> booksWithoutBookDetails = bookDetailsWithBooks.getBooks();
			if(booksWithoutBookDetails == null) {
				booksWithoutBookDetails = new LinkedList<>();
			}
			booksWithoutBookDetails.add(mapper.map(book, BookWithoutBookDetails.class));
			bookDetailsWithBooks.setBooks(booksWithoutBookDetails);
			booksDetailsWithBooks.replace(bookDetailsId, bookDetailsWithBooks);
		}

		List<BookDetailsWithBooks> bookDetailsWithBooks = new LinkedList<>();
		for(Map.Entry<Long, BookDetailsWithBooks> entry : booksDetailsWithBooks.entrySet()) {
			BookDetailsWithBooks withBooks = entry.getValue();
			String description = withBooks.getDescription();
			if(description != null && description.length() > 400) {
				description = description.substring(0, 400);
			}
			withBooks.setDescription(description);
			if(withBooks.getBooks() != null)
				bookDetailsWithBooks.add(withBooks);
		}
		return bookDetailsWithBooks;
	}

	public BookDetailsWithBooks getBookDetailsById(Long id) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		Hibernate.initialize(bookDetails.getAuthors());
		Hibernate.initialize(bookDetails.getCategories());

		ModelMapper mapper = new ModelMapper();

		List<Book> books = bookService.getAllBooksByBookDetailsId(bookDetails.getId());
		if (!books.isEmpty())
			bookDetails.setIsRemovable(false);
		List<BookWithoutBookDetails> bookWithoutBookDetails = new ArrayList<>();
		for (Book book : books) {
			bookWithoutBookDetails.add(mapper.map(book, BookWithoutBookDetails.class));
		}
		BookDetailsWithBooks withBooks = mapper.map(bookDetails, BookDetailsWithBooks.class);
		withBooks.setBooks(bookWithoutBookDetails);
		return withBooks;
	}

	public BookDetails createBookDetails(BookDetailsDTO bookDetailsDTO) {
		MessageInfo.isThisEntityUnique(bookDetailsRepository.countBookDetailsByIsbnAndAndTitle(
				bookDetailsDTO.getIsbn(), bookDetailsDTO.getTitle()), "book details");

		ModelMapper mapper = new ModelMapper();
		BookDetails bookDetails = mapper.map(bookDetailsDTO, BookDetails.class);
		bookDetails.setIsRemovable(true);
		return bookDetailsRepository.save(persistingRequiredEntities(bookDetails, bookDetailsDTO));
	}

	public BookDetails updateBookDetails(Long id, BookDetailsDTO bookDetailsDTO) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		bookDetails.setIsbn(bookDetailsDTO.getIsbn());
		bookDetails.setTitle(bookDetailsDTO.getTitle());
		bookDetails.setDescription(bookDetailsDTO.getDescription());
		bookDetails.setCoverPictureUrl(bookDetailsDTO.getCoverPictureUrl());
		bookDetails.setPublicationDate(bookDetailsDTO.getPublicationDate());
		bookDetails.getAuthors().forEach(author -> author.setIsRemovable(true));
		bookDetails.getCategories().forEach(category -> category.setIsRemovable(true));
		return bookDetailsRepository.save(persistingRequiredEntities(bookDetails, bookDetailsDTO));
	}


	public void deleteBookDetails(Long id) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		if (bookRepository.countBooksByBookDetails(bookDetails) > 0) {
			throw new ValidationException("The book details" + Messages.get(IS_ASSIGNED_CANT_DELETE));
		}
		bookDetails.getAuthors().forEach(author -> author.setIsRemovable(authorRepository.countBookDetailsByAuthor(author.getId()) == 1));
		bookDetails.getCategories().forEach(category -> category.setIsRemovable(bookCategoryRepository.countBookDetailsByCategory(category.getId()) == 1));
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
		if (categories.size() != size)
			throw new ResourceNotFoundException("category");

		size = authors.size();
		authors.removeAll(authorsToRemove);
		authors.addAll(existingAuthors);
		bookDetails.setAuthors(authors);
		if (authors.size() != size)
			throw new ResourceNotFoundException("author");

		authors.forEach(author -> author.setIsRemovable(false));
		categories.forEach(category -> category.setIsRemovable(false));
		return bookDetails;
	}
}