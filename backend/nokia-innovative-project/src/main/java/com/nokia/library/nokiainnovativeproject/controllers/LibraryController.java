package com.nokia.library.nokiainnovativeproject.controllers;

import com.nokia.library.nokiainnovativeproject.services.LibraryService;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static com.nokia.library.nokiainnovativeproject.utils.Constants.MessageTypes.LIST_OF;
import static com.nokia.library.nokiainnovativeproject.utils.Constants.Messages;
import static com.nokia.library.nokiainnovativeproject.utils.Mappings.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + LIBRARY)
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping(GET_ALL)
    public ResponseEntity getAllLibraries(){
        return MessageInfo.success(libraryService.getAllLibraries(), Arrays.asList(Messages.get(LIST_OF) + "libraries."));
    }
}
