package com.nokia.library.nokiainnovativeproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.DTOs.BookToOrderDTO;
import com.nokia.library.nokiainnovativeproject.entities.BookToOrder;
import com.nokia.library.nokiainnovativeproject.services.BookToOrderService;
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
public class BookToOrderControllerTest {

    private static BookToOrder bookToOrder;
    private static BookToOrderDTO bookToOrderDTO;
    private static ObjectMapper mapper;
    private MockMvc mockMvc;
    private static final String BASE_URL = Mappings.PORT_AUTOTESTS + Mappings.API_VERSION + Mappings.BOOK_TO_ORDER;

    @Mock
    private BookToOrderService service;

    @InjectMocks
    private BookToOrderController controller;

    @BeforeAll
    public static void init() {
        mapper = new ObjectMapper();
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        bookToOrder = new BookToOrder();
        bookToOrder.setTitle("title");
        bookToOrder.setIsbn("9781449396985");
        bookToOrderDTO = new BookToOrderDTO();
        bookToOrderDTO.setTitle("title");
        bookToOrderDTO.setIsbn("9781449396985");
    }

    @Test
    public void whenGetAllBookToOrderThenOk() throws Exception {
        when(service.getAllBookToOrders()).thenReturn(Arrays.asList(bookToOrderDTO));
        mockMvc.perform(get(BASE_URL + Mappings.GET_ALL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object[0].title", Matchers.is("title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object[0].isbn", Matchers.is("9781449396985")));
    }

    @Test
    public void whenGetBookToOrderThenOk() throws Exception {
        when(service.getBookToOrderById(1L)).thenReturn(bookToOrderDTO);
        mockMvc.perform(get(BASE_URL + Mappings.GET_ONE, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.title", Matchers.is("title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.isbn", Matchers.is("9781449396985")));
    }

    @Test
    public void whenCreateBookToOrderThenOk() throws Exception {
        String jsonRequest = mapper.writeValueAsString(bookToOrderDTO);
        when(service.createBookToOrder(bookToOrderDTO)).thenReturn(bookToOrder);
        mockMvc.perform(post(BASE_URL + Mappings.CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.title", Matchers.is("title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.isbn", Matchers.is("9781449396985")));
    }

    @Test
    public void whenUpdateBookToOrderThenOk() throws Exception {
        bookToOrderDTO.setTitle("new title");
        bookToOrder.setTitle("new title");
        String jsonRequest = mapper.writeValueAsString(bookToOrder);
        when(service.updateBookToOrder(1L, bookToOrderDTO)).thenReturn(bookToOrder);
        mockMvc.perform(post(BASE_URL + Mappings.UPDATE, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.title", Matchers.is("new title")));
    }

    @Test
    public void whenDeletedBookToOrderThenOk() throws Exception {
        mockMvc.perform(delete(BASE_URL + Mappings.REMOVE, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service).deleteBookToOrder(1L);
    }

    @Test
    public void whenCreateBookToOrderWithMistakesThenBadRequest() throws Exception {
        bookToOrderDTO.setTitle("");
        bookToOrderDTO.setIsbn("");
        String jsonRequest = mapper.writeValueAsString(bookToOrderDTO);
        when(service.createBookToOrder(bookToOrderDTO)).thenReturn(bookToOrder);
        mockMvc.perform(post(BASE_URL + Mappings.CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateBookToOrderWithMistakesThenBadRequest() throws Exception {
        bookToOrderDTO.setTitle("");
        bookToOrderDTO.setIsbn("");
        String jsonRequest = mapper.writeValueAsString(bookToOrderDTO);
        when(service.updateBookToOrder(1L, bookToOrderDTO)).thenReturn(bookToOrder);
        mockMvc.perform(post(BASE_URL + Mappings.UPDATE, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}