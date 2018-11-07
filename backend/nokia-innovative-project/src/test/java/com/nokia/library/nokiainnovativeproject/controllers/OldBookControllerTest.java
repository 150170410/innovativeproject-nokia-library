package com.nokia.library.nokiainnovativeproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.DTOs.BookDTO;
import com.nokia.library.nokiainnovativeproject.entities.OldBook;
import com.nokia.library.nokiainnovativeproject.services.BookService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("autotests")
public class OldBookControllerTest {

	private static OldBook oldBook;
	private static BookDTO bookDTO;
	private static ObjectMapper mapper;
	private MockMvc mockMvc;
	private static final String BASE_URL = Mappings.PORT_AUTOTESTS + Mappings.API_VERSION + Mappings.LIBRARY;

	@Mock
	private BookService service;

	@InjectMocks
	private BookController controller;

	@BeforeAll
	public static void init(){
		mapper = new ObjectMapper();
		oldBook = new OldBook();
		oldBook.setTitle("test title");
		oldBook.setAuthorName("test name");
		oldBook.setAuthorSurname("test surname");

		bookDTO = new BookDTO("dto title", "dto name", "dto surname");
	}

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void getBooksTest() throws Exception {
		List<OldBook> oldBooks = new ArrayList<>();
		oldBooks.add(oldBook);
		when(service.getAllBooks()).thenReturn(oldBooks);
		mockMvc.perform(get(BASE_URL + Mappings.BOOKS)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("test title")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].authorName", Matchers.is("test name")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].authorSurname", Matchers.is("test surname")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(1)));
	}

	@Test
	public void createBookTest() throws Exception {
		String jsonRequest = mapper.writeValueAsString(oldBook);
		when(service.createBook(oldBook)).thenReturn(oldBook);
		mockMvc.perform(post(BASE_URL + Mappings.BOOKS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test title")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.authorName", Matchers.is("test name")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.authorSurname", Matchers.is("test surname")));
	}

	@Test
	public void updateBookTest() throws Exception {
		String jsonRequest = mapper.writeValueAsString(oldBook);
		when(service.updateBook(1L, bookDTO)).thenReturn(oldBook);
		mockMvc.perform(post(BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)).andDo(print());

		String newBook = mapper.writeValueAsString(bookDTO);
		mockMvc.perform(post(BASE_URL + Mappings.BOOKS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(newBook)).andDo(print())
				.andExpect(status().isOk());

		Assertions.fail("not finished yet");
//				.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("dto title")))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.authorName", Matchers.is("dto name")))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.authorSurname", Matchers.is("dto surname")));
	}


	@Test
	public void getBookByIdTest() throws Exception {
		when(service.getBookById(1L)).thenReturn(oldBook);
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
