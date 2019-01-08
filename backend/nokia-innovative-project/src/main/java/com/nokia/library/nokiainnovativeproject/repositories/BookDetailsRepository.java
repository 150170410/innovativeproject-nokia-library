package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {
    Long countBookDetailsByIsbnAndAndTitle(String isbn, String title);
    Long countBookDetailsByAuthors(List<Author> authors);
    Long countBookDetailsByCategories(List<BookCategory> categories);
}
