package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.services.AuthorService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_AUTHOR)
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(Mappings.GET_ALL)
    public List<Author> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping(Mappings.GET_ONE)
    public Author getAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }

    @PostMapping(Mappings.SAVE)
    public Author createAuthor(@RequestBody AuthorDTO authorDTO){
        return authorService.createAuthor(authorDTO);
    }

    @PostMapping(Mappings.UPDATE)
    public Author updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO){
        return authorService.updateAuthor(id, authorDTO);
    }

    @DeleteMapping(Mappings.REMOVE)
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
    }
}
