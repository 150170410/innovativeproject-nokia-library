package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.entities.Library;
import com.nokia.library.nokiainnovativeproject.repositories.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;

    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }
}
