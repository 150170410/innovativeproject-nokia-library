package com.nokia.library.nokiainnovativeproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.services.BookService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("autotests")
public class BookControllerTest {

	private static Book book;
	private static BookDTO bookDTO;
	private static ObjectMapper mapper;
	private MockMvc mockMvc;
	private static final String BASE_URL = "http://localhost:8080" + Mappings.API_VERSION + Mappings.LIBRARY;

	@Mock
	private BookService service;

	@InjectMocks
	private BookController controller;

	@BeforeAll
	public static void init(){
		mapper = new ObjectMapper();
		book = new Book();
		book.setTitle("test title");
		book.setAuthorName("test name");
		book.setAuthorSurname("test surname");
	}

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void getBooksTest() throws Exception {
		List<Book> books = new ArrayList<>();
		books.add(book);
		when(service.getAllBooks()).thenReturn(books);
		mockMvc.perform(get(BASE_URL + Mappings.BOOKS)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("test title")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].authorName", Matchers.is("test name")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].authorSurname", Matchers.is("test surname")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(1)));
	}

	@Test
	public void createBookTest() throws Exception {
		String jsonRequest = mapper.writeValueAsString(book);
		when(service.createBook(book)).thenReturn(book);
		mockMvc.perform(post(BASE_URL + Mappings.BOOKS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test title")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.authorName", Matchers.is("test name")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.authorSurname", Matchers.is("test surname")));
	}

	@Test
	public void getBookByIdTest() throws Exception {
		when(service.getBookById(1L)).thenReturn(book);
		mockMvc.perform(get(BASE_URL + Mappings.BOOKS_ID, 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test title")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.authorName", Matchers.is("test name")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.authorSurname", Matchers.is("test surname")));
	}

	@Test
	public void deleteBookTest() throws Exception {
		mockMvc.perform(delete(BASE_URL + Mappings.BOOKS_ID, 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(service).deleteBook(1L);
	}
}
