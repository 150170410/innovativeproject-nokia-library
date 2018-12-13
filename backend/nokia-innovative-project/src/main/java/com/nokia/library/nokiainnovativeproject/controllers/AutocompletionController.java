package com.nokia.library.nokiainnovativeproject.controllers;


import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.services.AutocompletionService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + AUTOCOMPLETION)
public class AutocompletionController {

    private final AutocompletionService autocompletionService;

    @GetMapping(Mappings.GET_ALL)
    public MessageInfo getBookDetails(@RequestParam String isbn) throws ParseException {
        List<BookDetailsDTO> list = new ArrayList<>();
        list.add(autocompletionService.getBookDetailsFromApiItBookStore(isbn));
        list.add(autocompletionService.getBookDetailsFromApiGoogle(isbn));
        return MessageInfo.success(list, Arrays.asList("bookDetails"));
    }

}
