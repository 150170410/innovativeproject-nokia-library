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

   List<Book> findAllByAdminOwnerId(Long adminId);

   Long countBooksByBookDetails(BookDetails bookDetails);

   Book findBySignature(String signature);
}
