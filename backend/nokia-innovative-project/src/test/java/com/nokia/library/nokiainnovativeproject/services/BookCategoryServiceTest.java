package com.nokia.library.nokiainnovativeproject.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.DTOs.BookCategoryDTO;
import com.nokia.library.nokiainnovativeproject.controllers.BookCategoryController;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
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
public class BookCategoryServiceTest {


	private static BookCategory bookCategory;
	private static BookCategoryDTO bookCategoryDTO;
	private static ObjectMapper mapper;
	private MockMvc mockMvc;
	private static final String BASE_URL = Mappings.PORT_AUTOTESTS + Mappings.API_VERSION + Mappings.LIBRARY;

	@Mock
	private BookCategoryService service;

	@InjectMocks
	private BookCategoryController controller;

	@BeforeAll
	public static void init() {
		mapper = new ObjectMapper();
		bookCategory = new BookCategory();
		bookCategory.setBookCategoryName("test name");
		bookCategoryDTO = new BookCategoryDTO("dto name");
	}

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void getBooksTest() throws Exception {
		List<BookCategory> bookCategories = new ArrayList<>();
		bookCategories.add(bookCategory);
		when(service.getAllBookCategories()).thenReturn(bookCategories);
		mockMvc.perform(get(BASE_URL + Mappings.BK_CAT)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].bookCategoryName", Matchers.is("test name")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(1)));
	}

	@Test
	public void createBookTest() throws Exception {
		String jsonRequest = mapper.writeValueAsString(bookCategoryDTO);
		when(service.createBookCategory(bookCategoryDTO)).thenReturn(bookCategory);
		mockMvc.perform(post(BASE_URL + Mappings.BK_CAT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookCategoryName", Matchers.is("test name")));
	}

	@Test
	public void updateBookTest() throws Exception {

		Assertions.fail("not finished yet");
	}


	@Test
	public void getBookByIdTest() throws Exception {
		when(service.getBookCategoryById(1L)).thenReturn(bookCategory);
		mockMvc.perform(get(BASE_URL + Mappings.BK_CAT_ID, 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookCategoryName", Matchers.is("test name")));
	}

	@Test
	public void deleteBookTest() throws Exception {
		mockMvc.perform(delete(BASE_URL + Mappings.BK_CAT_ID, 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(service).deleteBookCategory(1L);
	}

}
