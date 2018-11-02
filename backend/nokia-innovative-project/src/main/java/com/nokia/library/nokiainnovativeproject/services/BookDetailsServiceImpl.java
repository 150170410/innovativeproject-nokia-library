package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookDetailsServiceImpl implements BookDetailsService {

    private final BookDetailsRepository bookDetailsRepository;

    @Override
    public List<BookDetails> getAllBookDetails() {
        return bookDetailsRepository.findAll();
    }

    @Override
    public BookDetails getBookDetailsById(Long id) {
        return bookDetailsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("BookDetailsRepository", "id", id));
    }

    @Override
    public BookDetails createBookDetails(BookDetailsDTO bookDetailsDTO) {
        ModelMapper mapper = new ModelMapper();
        BookDetails bookDetails = mapper.map(bookDetailsDTO, BookDetails.class);
        return bookDetailsRepository.save(bookDetails);
    }

    @Override
    public BookDetails updateBookDetails(Long id, BookDetailsDTO bookDetailsDTO) {
        BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("BookDetailsRepository", "id", id));
        bookDetails.setIsbn(bookDetailsDTO.getIsbn());
        bookDetails.setTitle(bookDetailsDTO.getTitle());
        bookDetails.setDescription(bookDetailsDTO.getDescription());
        bookDetails.setCoverPictureUrl(bookDetailsDTO.getCoverPictureUrl());
        bookDetails.setDateOfPublication(bookDetailsDTO.getDateOfPublication());
        bookDetails.setTableOfContents(bookDetailsDTO.getTableOfContents());
        return  bookDetailsRepository.save(bookDetails);
    }

    @Override
    public void deleteBookDetails(Long id) {
        BookDetails bookDetails = bookDetailsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("BookDetaisRepository", "id", id));
        bookDetailsRepository.delete(bookDetails);
    }
}
