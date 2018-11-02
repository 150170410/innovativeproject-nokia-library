package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("AuthorRepository", "id", id));
    }

    @Override
    public Author createAuthor(AuthorDTO authorDTO) {
        ModelMapper mapper = new ModelMapper();
        Author author = mapper.map(authorDTO, Author.class);
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("AuthorRepository", "id", id));
        author.setAuthorName(authorDTO.getAuthorName());
        author.setAuthorSurname(authorDTO.getAuthorSurname());
        author.setAuthorDescription(authorDTO.getAuthorDescription());
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("AuthorRepository", "id", id));
        authorRepository.delete(author);
    }
}
