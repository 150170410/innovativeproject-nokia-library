package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.exceptions.ValidationException;
import com.nokia.library.nokiainnovativeproject.repositories.AuthorRepository;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

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
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("author"));
        author.setAuthorFullName(authorDTO.getAuthorFullName());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
		Author author = authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("author"));
        try {
            authorRepository.delete(author);
        }catch(DataIntegrityViolationException e){
            throw new ValidationException("The author you are trying to delete is assigned to a book. You can't delete it.");
        }
    }
}