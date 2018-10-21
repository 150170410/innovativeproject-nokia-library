package com.nokia.library.nokiainnovativeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nokia.library.nokiainnovativeproject.entities.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}