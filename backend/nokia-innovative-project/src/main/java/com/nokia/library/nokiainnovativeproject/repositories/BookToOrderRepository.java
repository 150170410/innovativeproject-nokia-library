package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.BookToOrder;
import com.nokia.library.nokiainnovativeproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookToOrderRepository extends JpaRepository<BookToOrder, Long> {
    @Query(value = "DELETE FROM BOOK_TO_ORDER " +
            "WHERE CREATION_DATE <= now() - interval '2 week';",
    nativeQuery = true)
    void removeOverdueBooks();

    Optional<BookToOrder> getBookToOrdersByIsbn(String isbn);
}
