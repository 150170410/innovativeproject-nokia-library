package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByBookDetailsId(Long bookDetailsId);

    @Query(value = "SELECT * FROM Book b WHERE b.id IN " +
           "(SELECT boi.book_id FROM book_owner_id boi WHERE boi.owner_id = ?1 )",
         nativeQuery = true)
     List<Book> findAllByOwnersId(Long ownerId);

    Long countBooksByBookDetails(BookDetails bookDetails);

    Book findBySignature(String signature);
}
