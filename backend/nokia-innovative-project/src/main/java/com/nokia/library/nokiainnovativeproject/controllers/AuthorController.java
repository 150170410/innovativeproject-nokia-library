package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.services.AuthorService;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import com.nokia.library.nokiainnovativeproject.validators.BindingResultsValidator;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;



@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + BOOK_AUTHOR)
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(GET_ALL)
    public MessageInfo getAllAuthors(){
        return MessageInfo.success(authorService.getAllAuthors(), Arrays.asList("list of authors"));
    }

    @GetMapping(GET_ONE)
    public MessageInfo getAuthorById(@PathVariable Long id){
        return MessageInfo.success(authorService.getAuthorById(id), Arrays.asList("Author of ID = " + id.toString()));
    }

    @PostMapping(CREATE)
    public MessageInfo createAuthor(@RequestBody @Valid AuthorDTO authorDTO, BindingResult bindingResult){
        BindingResultsValidator.validateBindingResults(bindingResult, authorDTO.getClass().getSimpleName());
        return MessageInfo.success(authorService.createAuthor(authorDTO), Arrays.asList("Author created successfully"));
    }

    @PostMapping(UPDATE)
    public MessageInfo updateAuthor(@PathVariable Long id, @RequestBody  @Valid AuthorDTO authorDTO, BindingResult bindingResult){
        BindingResultsValidator.validateBindingResults(bindingResult, authorDTO.getClass().getSimpleName());
        return MessageInfo.success(authorService.updateAuthor(id, authorDTO), Arrays.asList("Author updated successfully"));
    }

    @DeleteMapping(REMOVE)
    public MessageInfo deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return MessageInfo.success(null, Arrays.asList("Author with ID = " + id.toString() + " removed successfully"));
    }
}
