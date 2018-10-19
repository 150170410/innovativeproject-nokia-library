package com.nokia.library.nokiainnovativeproject.repositories;

import com.nokia.library.nokiainnovativeproject.entities.Book;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor
public class BookRepositoryTest {

	private final BookRepository bookRepository;

	@Test
	public void whenAddBookThenBookAdded() {
		Book book = Book.builder()
				.title("add")
				.authorName("authorName")
				.authorSurname("authorSurname")
				.build();
		book.setId(bookRepository.save(book).getId());
		assertTrue(bookRepository.findById(book.getId()).get().equals(book));
		bookRepository.delete(book);
	}

	@Test
	public void whenDeleteBookThenBookDeleted() {
		Book book = Book.builder()
				.title("delete")
				.authorName("authorName")
				.authorSurname("authorSurname")
				.build();
		book.setId(bookRepository.save(book).getId());
		assertTrue(bookRepository.existsById(book.getId()));
		bookRepository.delete(book);
		assertFalse(bookRepository.existsById(book.getId()));
	}

	@Test
	public void whenUpdateBookThenBookUpdated() {
		Book oldBook = Book.builder()
				.title("old")
				.authorName("authorName")
				.authorSurname("authorSurname")
				.build();
		Book newBook = Book.builder()
				.title("new")
				.authorName("authorName")
				.authorSurname("authorSurname")
				.build();
		newBook.setId(bookRepository.save(oldBook).getId());
		assertThat(bookRepository.findById(oldBook.getId()).get().getTitle(), is("old"));
		bookRepository.save(newBook);
		assertThat(bookRepository.findById(oldBook.getId()).get().getTitle(), is("new"));
		bookRepository.delete(newBook);
	}
}
