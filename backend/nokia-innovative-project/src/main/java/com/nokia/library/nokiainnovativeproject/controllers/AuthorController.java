package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.services.AuthorService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.BOOK_AUTHOR)
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(Mappings.GET_ALL)
    public MessageInfo getAllAuthors(){
        return MessageInfo.success(authorService.getAllAuthors(), Arrays.asList("list of authors"));
    }

    @GetMapping(Mappings.GET_ONE)
    public MessageInfo getAuthorById(@PathVariable Long id){
        return MessageInfo.success(authorService.getAuthorById(id), Arrays.asList("Author of ID = " + id.toString()));
    }

    @PostMapping(Mappings.CREATE)
    public MessageInfo createAuthor(@RequestBody @Valid AuthorDTO authorDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<String> errorsList = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return MessageInfo.failure(errorsList);
        }
        return MessageInfo.success(authorService.createAuthor(authorDTO), Arrays.asList("Author created successfully"));
    }

    @PostMapping(Mappings.UPDATE)
    public MessageInfo updateAuthor(@PathVariable Long id, @RequestBody  @Valid AuthorDTO authorDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            List<String> errorsList = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return MessageInfo.failure(errorsList);
        }
        return MessageInfo.success(authorService.updateAuthor(id, authorDTO), Arrays.asList("Author updated successfully"));
    }

    @DeleteMapping(Mappings.REMOVE)
    public MessageInfo deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return MessageInfo.success(null, Arrays.asList("Author with ID = " + id.toString() + " removed successfully"));
    }
}
