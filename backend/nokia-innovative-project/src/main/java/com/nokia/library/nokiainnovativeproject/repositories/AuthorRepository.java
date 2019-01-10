package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Long countAuthorByAuthorFullName(String authorFullName);

    @Query(value = "SELECT COUNT(*) FROM book_details_authors WHERE author_id = :id ;",
    nativeQuery = true)
    Long countBookDetailsByAuthor(@Param("id") Long author_id);
}
