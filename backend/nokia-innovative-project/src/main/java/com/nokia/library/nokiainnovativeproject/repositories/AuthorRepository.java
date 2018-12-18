package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Long countAuthorByAuthorFullName(String authorFullName);
}
