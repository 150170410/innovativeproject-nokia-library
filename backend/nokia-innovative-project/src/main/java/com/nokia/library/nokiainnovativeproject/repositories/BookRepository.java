package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

   @Query(value="SELECT * FROM BOOK B " +
           "LEFT JOIN BOOK_DETAILS BD ON BD.ID = B.ID " +
           "WHERE BD.ID = ?1",
   nativeQuery = true)
    List<Book> getBooksByBookDetailsId(Long id);
}