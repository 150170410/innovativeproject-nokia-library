package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.exceptions.ValidationException;
import com.nokia.library.nokiainnovativeproject.repositories.AuthorRepository;
import com.nokia.library.nokiainnovativeproject.repositories.BookDetailsRepository;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookDetailsRepository bookDetailsRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("author"));
    }

    public Author createAuthor(AuthorDTO authorDTO) {
        MessageInfo.isThisEntityUnique(authorRepository.countAuthorByAuthorFullName(
                authorDTO.getAuthorFullName()), "author");
        ModelMapper mapper = new ModelMapper();
        Author author = mapper.map(authorDTO, Author.class);
        author.setIsRemovable(true);
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("author"));
        author.setAuthorFullName(authorDTO.getAuthorFullName());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
		Author author = authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("author"));
        if(bookDetailsRepository.countBookDetailsByAuthors(Arrays.asList(author)) > 0) {
            throw new ValidationException("The author" + IS_ASSIGNED_CANT_DELETE);
        }
        authorRepository.delete(author);
    }
}