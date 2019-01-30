package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.services.AuthorService;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + BOOK_AUTHOR)
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(GET_ALL)
    public ResponseEntity getAllAuthors(){
        return MessageInfo.success(authorService.getAllAuthors(), Arrays.asList(Messages.get(LIST_OF) + "authors."));
    }

    @GetMapping(GET_ONE)
    public ResponseEntity getAuthorById(@PathVariable Long id){
        return MessageInfo.success(authorService.getAuthorById(id), Arrays.asList("Author" + Messages.get(REQUESTED)));
    }

    @PostMapping(CREATE)
    public ResponseEntity createAuthor(@RequestBody @Valid AuthorDTO authorDTO, BindingResult bindingResult){
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(authorService.createAuthor(authorDTO), Arrays.asList("Author" + Messages.get(CREATED_SUCCESSFULLY)));
    }

    @PostMapping(UPDATE)
    public ResponseEntity updateAuthor(@PathVariable Long id, @RequestBody  @Valid AuthorDTO authorDTO, BindingResult bindingResult){
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(authorService.updateAuthor(id, authorDTO), Arrays.asList("Author" + Messages.get(UPDATED_SUCCESSFULLY)));
    }

    @DeleteMapping(REMOVE)
    public ResponseEntity deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return MessageInfo.success(null, Arrays.asList("Author" + Messages.get(REMOVED_SUCCESSFULLY)));
    }
}