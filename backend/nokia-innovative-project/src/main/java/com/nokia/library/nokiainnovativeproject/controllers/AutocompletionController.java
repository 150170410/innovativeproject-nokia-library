package com.nokia.library.nokiainnovativeproject.controllers;


import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.services.AutocompletionService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;

import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + AUTOCOMPLETION)
public class AutocompletionController {

    private final AutocompletionService autocompletionService;

    @GetMapping(Mappings.GET_ALL)
    public MessageInfo getBookDetails(@RequestParam String isbn) throws ParseException {
        BookDetailsDTO bookDetailsDTO = autocompletionService.getBookDetailsFromApiItBookStore(isbn);
        if(bookDetailsDTO == null)
            bookDetailsDTO = autocompletionService.getBookDetailsFromApiGoogle(isbn);
        return MessageInfo.success(bookDetailsDTO, Arrays.asList("bookDetails"));
    }

}
