package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.BookToOrder;
import com.nokia.library.nokiainnovativeproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookToOrderRepository extends JpaRepository<BookToOrder, Long> {

    List<BookToOrder> findAllByUser(User user);
}
