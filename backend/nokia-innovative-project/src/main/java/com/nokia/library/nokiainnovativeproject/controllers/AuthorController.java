package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.services.AuthorService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;



@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_AUTHOR)
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(Mappings.GET_ALL)
    public ResponseEntity getAllAuthors(){
        return MessageInfo.success(authorService.getAllAuthors(), Arrays.asList("list of authors"));
    }

    @GetMapping(Mappings.GET_ONE)
    public ResponseEntity getAuthorById(@PathVariable Long id){
        return MessageInfo.success(authorService.getAuthorById(id), Arrays.asList("Author of ID = " + id.toString()));
    }

    @PostMapping(Mappings.CREATE)
    public ResponseEntity createAuthor(@RequestBody @Valid AuthorDTO authorDTO, BindingResult bindingResult){
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(authorService.createAuthor(authorDTO), Arrays.asList("Author created successfully"));
    }

    @PostMapping(Mappings.UPDATE)
    public ResponseEntity updateAuthor(@PathVariable Long id, @RequestBody  @Valid AuthorDTO authorDTO, BindingResult bindingResult){
        MessageInfo.validateBindingResults(bindingResult);
        return MessageInfo.success(authorService.updateAuthor(id, authorDTO), Arrays.asList("Author updated successfully"));
    }

    @DeleteMapping(Mappings.REMOVE)
    public ResponseEntity deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return MessageInfo.success(null, Arrays.asList("Author with ID = " + id.toString() + " removed successfully"));
    }
}
