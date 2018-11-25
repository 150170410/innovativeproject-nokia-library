package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookDetailsService {

	private final BookDetailsRepository bookDetailsRepository;

	public List<BookDetails> getAllBookDetails() {
		//TODO: This metod returns BookDetails without authors, categories and reviews. Was that supposed to be like that? How this details will be loaded in FE part? Separate requests for authors, categories, etc?
		//https://github.com/nokia-wroclaw/innovativeproject-nokia-library/pull/26#discussion_r232793457
		return bookDetailsRepository.findAll();
	}

	public BookDetails getBookDetailsById(Long id) {
		return bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
	}

	public BookDetails createBookDetails(BookDetailsDTO bookDetailsDTO) {
		ModelMapper mapper = new ModelMapper();
		BookDetails bookDetails = mapper.map(bookDetailsDTO, BookDetails.class);
		bookDetails.setAuthors(new ArrayList<>());
		bookDetails.setCategories(new ArrayList<>());

		// TODO: Why do you load just saved entity? save(..) returns this entity.
		// https://github.com/nokia-wroclaw/innovativeproject-nokia-library/pull/26#discussion_r232793952
		BookDetails objectDetails = bookDetailsRepository.save(bookDetails);

		objectDetails.setAuthors(bookDetailsDTO.getAuthors());
		objectDetails.setCategories(bookDetailsDTO.getCategories());

		return bookDetailsRepository.save(objectDetails);
	}

	public BookDetails updateBookDetails(Long id, BookDetailsDTO bookDetailsDTO) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		bookDetails.setIsbn(bookDetailsDTO.getIsbn());
		bookDetails.setTitle(bookDetailsDTO.getTitle());
		bookDetails.setDescription(bookDetailsDTO.getDescription());
		bookDetails.setCoverPictureUrl(bookDetailsDTO.getCoverPictureUrl());
		bookDetails.setDateOfPublication(bookDetailsDTO.getDateOfPublication());
		bookDetails.setTableOfContents(bookDetailsDTO.getTableOfContents());
		bookDetails.setAuthors(bookDetailsDTO.getAuthors());
		return bookDetailsRepository.save(bookDetails);
	}

	public void deleteBookDetails(Long id) {
		BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("book details"));
		bookDetailsRepository.delete(bookDetails);
	}
}
