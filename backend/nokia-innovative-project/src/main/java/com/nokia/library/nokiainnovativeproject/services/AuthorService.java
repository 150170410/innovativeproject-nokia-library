package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author getAuthorById(Long id);

    Author createAuthor(AuthorDTO authorDTO);

    Author updateAuthor(Long id, AuthorDTO authorDTO);

    void deleteAuthor(Long id);
}
