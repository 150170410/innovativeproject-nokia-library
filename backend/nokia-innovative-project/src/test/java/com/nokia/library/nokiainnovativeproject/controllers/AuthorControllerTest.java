package com.nokia.library.nokiainnovativeproject.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.DTOs.AuthorDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.services.AuthorService;
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

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("autotests")
class AuthorControllerTest {
    private static Author author;
    private static AuthorDTO authorDTO;
    private static ObjectMapper mapper;
    private MockMvc mockMvc;
    private static final String BASE_URL = Mappings.PORT_AUTOTESTS + Mappings.API_VERSION + Mappings.BOOK_AUTHOR;

    @Mock
    private AuthorService service;

    @InjectMocks
    private AuthorController controller;

    @BeforeAll
    public static void init() {
        mapper = new ObjectMapper();
        author = new Author();
        author.setAuthorFullName("test name");
        authorDTO = new AuthorDTO();
        authorDTO.setAuthorFullName("test name");
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAuthorsTest() throws Exception {
        when(service.getAllAuthors()).thenReturn(Arrays.asList(author));
        mockMvc.perform(get(BASE_URL + Mappings.GET_ALL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object[0].authorFullName", Matchers.is("test name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.*", Matchers.hasSize(1)));
    }

    @Test
    public void getAuthorByIdTest() throws Exception {
        when(service.getAuthorById(1L)).thenReturn(author);
        mockMvc.perform(get(BASE_URL + Mappings.GET_ONE, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.authorFullName", Matchers.is("test name")));

    }

    @Test
    public void createAuthorTest() throws Exception {
        String jsonRequest = mapper.writeValueAsString(authorDTO);
        when(service.createAuthor(authorDTO)).thenReturn(author);
        mockMvc.perform(post(BASE_URL + Mappings.CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.authorFullName", Matchers.is("test name")));
    }

    @Test
    public void updateAuthorTest() throws Exception {
        AuthorDTO updatedDTO = new AuthorDTO();
        updatedDTO.setAuthorFullName("updated name");

        Author updatedAuthor = new Author();
        updatedAuthor.setAuthorFullName("updated name");

        String jsonRequest = mapper.writeValueAsString(updatedDTO);

        when(service.updateAuthor(1L, updatedDTO)).thenReturn(updatedAuthor);
        mockMvc.perform(post(BASE_URL + Mappings.UPDATE, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.authorFullName", Matchers.is("updated name")));

    }

    @Test
    public void deleteAuthorTest() throws Exception {
        mockMvc.perform(delete(BASE_URL + Mappings.REMOVE, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service).deleteAuthor(1L);
    }
}