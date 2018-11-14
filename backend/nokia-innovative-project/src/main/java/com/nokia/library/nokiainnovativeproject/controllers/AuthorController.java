package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.services.AuthorService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_AUTHOR)
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(Mappings.GET_ALL)
    public MessageInfo getAllAuthors(){
        return new MessageInfo(true, authorService.getAllAuthors(), "list of authors");
    }

    @GetMapping(Mappings.GET_ONE)
    public MessageInfo getAuthorById(@PathVariable Long id){
        return new MessageInfo(true, authorService.getAuthorById(id), "Author of ID = " + id.toString());
    }

    @PostMapping(Mappings.CREATE)
    public MessageInfo createAuthor(@RequestBody @Valid AuthorDTO authorDTO){
        return new MessageInfo(true, authorService.createAuthor(authorDTO), "Author created successfully");
    }

    @PostMapping(Mappings.UPDATE)
    public MessageInfo updateAuthor(@PathVariable Long id, @RequestBody  @Valid AuthorDTO authorDTO){
        return new MessageInfo(true, authorService.updateAuthor(id, authorDTO), "Author updated successfully");
    }

    @DeleteMapping(Mappings.REMOVE)
    public MessageInfo deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return new MessageInfo(true, null, "Author with ID = " + id.toString() + " removed successfully");
    }
}
