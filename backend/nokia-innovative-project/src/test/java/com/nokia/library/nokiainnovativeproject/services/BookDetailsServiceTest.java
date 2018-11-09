package com.nokia.library.nokiainnovativeproject.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.controllers.BookDetailsController;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
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
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("autotests")
public class BookDetailsServiceTest {

	private static BookDetails bookDetails;
	private static BookDetailsDTO bookDetailsDTO;
	private static ObjectMapper mapper;
	private MockMvc mockMvc;
	private static final String BASE_URL = Mappings.PORT_AUTOTESTS + Mappings.API_VERSION + Mappings.BOOK_DETAILS;

	@Mock
	private BookDetailsService service;

	@InjectMocks
	private BookDetailsController controller;

	@BeforeAll
	public static void init() {
		mapper = new ObjectMapper();
		bookDetails = new BookDetails();
		bookDetails.setTitle("test title");
		bookDetails.setDescription("test description");
		bookDetails.setIsbn("test isbn123");
		bookDetails.setTableOfContents("test table of contents");
		bookDetails.setCoverPictureUrl("test cover picture url");
		bookDetails.setDateOfPublication(new Date());
		bookDetails.setAuthors(new ArrayList<>());
		bookDetails.setCategories(new ArrayList<>());

		bookDetailsDTO = new BookDetailsDTO("dto isbn123", "dto title", "dto desc", "dto url", new Date(), "dto table", new ArrayList<>(), new ArrayList<>());
	}

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void getBookDetailsListTest() throws Exception {
		List<BookDetails> bookDetailsList = new ArrayList<>();
		bookDetailsList.add(bookDetails);
		when(service.getAllBookDetails()).thenReturn(bookDetailsList);
		mockMvc.perform(get(BASE_URL + Mappings.GET_ALL)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.object[0].title", Matchers.is("test title")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.*", Matchers.hasSize(1)));
	}

	@Test
	public void getBookDetailsByIdTest() throws Exception {
		when(service.getBookDetailsById(1L)).thenReturn(bookDetails);
		mockMvc.perform(get(BASE_URL + Mappings.GET_ONE, 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.title", Matchers.is("test title")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.description", Matchers.is("test description")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.isbn", Matchers.is("test isbn123")));
	}

	@Test
	public void createBookDetailsTest() throws Exception {

		Assertions.fail("not tested yet");
	}

	@Test
	public void updateBookDetailsTest() throws Exception {

		Assertions.fail("not tested yet");
	}

	@Test
	public void deleteBookDetailsTest() throws Exception {
		mockMvc.perform(delete(BASE_URL + Mappings.REMOVE, 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(service).deleteBookDetails(1L);
	}
}
