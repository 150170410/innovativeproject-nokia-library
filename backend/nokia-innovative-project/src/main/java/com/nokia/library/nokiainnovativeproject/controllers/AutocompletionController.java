package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.services.AutocompletionService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nokia.library.nokiainnovativeproject.utils.Mappings.API_VERSION;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.AUTOCOMPLETION;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + AUTOCOMPLETION)
public class AutocompletionController {

    private final AutocompletionService autocompletionService;

    @GetMapping(Mappings.GET_ALL)
    public ResponseEntity getBookDetails(@RequestParam String isbn) {
        List<BookDetailsDTO> list = new ArrayList<>();
        BookDetailsDTO bookDetailsItBookStore = autocompletionService.getBookDetailsFromApiItBookStore(isbn);
        if(bookDetailsItBookStore != null)
            list.add(bookDetailsItBookStore);
        BookDetailsDTO bookDetailsGoogle = autocompletionService.getBookDetailsFromApiGoogle(isbn);
        if(bookDetailsGoogle  != null)
            list.add(bookDetailsGoogle);
        BookDetailsDTO bookDetailsBN = autocompletionService.getBookDetailsFromApiBN(isbn);
        if(bookDetailsBN != null)
            list.add(bookDetailsBN);
        return list.isEmpty() ? MessageInfo.failure(Arrays.asList(BOOK_WITH_ISBN_NOT_FOUND.toString())) :
                MessageInfo.success(list, Arrays.asList(LIST_OF + "bookDetails"));
    }
}