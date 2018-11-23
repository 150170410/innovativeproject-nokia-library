package com.nokia.library.nokiainnovativeproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.DTOs.BookCategoryDTO;
import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import com.nokia.library.nokiainnovativeproject.services.BookDetailsService;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("autotests")
public class BookDetailsControllerTest {

	private static BookDetails bookDetails;
	private static BookDetailsDTO bookDetailsDTO;
	private static ObjectMapper mapper;
	private static Date date;
	private MockMvc mockMvc;
	private static final String BASE_URL = Mappings.PORT_AUTOTESTS + Mappings.API_VERSION + Mappings.BOOK_DETAILS;

	@Mock
	private BookDetailsService service;

	@InjectMocks
	private BookDetailsController controller;

	@BeforeAll
	public static void init() {
		date = new Date();
		mapper = new ObjectMapper();
		bookDetails = new BookDetails();
		bookDetails.setTitle("test title");
		bookDetails.setDescription("test description");
		bookDetails.setIsbn("test isbn123");
		bookDetails.setTableOfContents("test table of contents");
		bookDetails.setCoverPictureUrl("test cover picture url");
		bookDetails.setDateOfPublication(date);
		bookDetails.setAuthors(new ArrayList<>());
		bookDetails.setCategories(new ArrayList<>());

		bookDetailsDTO = new BookDetailsDTO("test isbn123", "test title", "test description", "test cover picture url", date, "test table of contents", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void getBookDetailsListTest() throws Exception {
		when(service.getAllBookDetails()).thenReturn(Arrays.asList(bookDetails));
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
		String jsonRequest = mapper.writeValueAsString(bookDetailsDTO);
		when(service.createBookDetails(bookDetailsDTO)).thenReturn(bookDetails);
		mockMvc.perform(post(BASE_URL + Mappings.CREATE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.title", Matchers.is("test title")))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.object.description", Matchers.is("test description")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.isbn", Matchers.is("test isbn123")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.tableOfContents", Matchers.is("test table of contents")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.coverPictureUrl", Matchers.is("test cover picture url")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.dateOfPublication", Matchers.is(date.getTime())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.authors",Matchers.hasSize(0)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.categories",Matchers.hasSize(0)));

	}

	@Test
	public void updateBookDetailsTest() throws Exception {
		BookDetailsDTO updatedDTO = new BookDetailsDTO();
		updatedDTO.setTitle("updated title");
		updatedDTO.setIsbn("111111111111");
		updatedDTO.setAuthors(new ArrayList<Author>());
		updatedDTO.setCategories(new ArrayList<BookCategory>());


		BookDetails updatedBookDetails = new BookDetails();
		updatedBookDetails.setTitle("updated title");
		updatedBookDetails.setIsbn("111111111111");
		updatedBookDetails.setAuthors(new ArrayList<Author>());
		updatedBookDetails.setCategories(new ArrayList<BookCategory>());

		String jsonRequest = mapper.writeValueAsString(updatedDTO);
		when(service.updateBookDetails(1L, updatedDTO)).thenReturn(updatedBookDetails);
		mockMvc.perform(post(BASE_URL + Mappings.UPDATE, 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.title", Matchers.is("updated title")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.isbn", Matchers.is("111111111111")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.authors", Matchers.hasSize(0)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.object.categories", Matchers.hasSize(0)));
	}

	@Test
	public void deleteBookDetailsTest() throws Exception {
		mockMvc.perform(delete(BASE_URL + Mappings.REMOVE, 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(service).deleteBookDetails(1L);
	}
}