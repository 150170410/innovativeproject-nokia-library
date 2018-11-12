package com.nokia.library.nokiainnovativeproject.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.DTOs.ReviewDTO;
import com.nokia.library.nokiainnovativeproject.entities.Review;
import com.nokia.library.nokiainnovativeproject.services.ReviewService;
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
class ReviewControllerTest {
    private static Review review;
    private static ReviewDTO reviewDTO;
    private static ObjectMapper mapper;
    private static Date date;
    private MockMvc mockMvc;
    private static final String BASE_URL = Mappings.PORT_AUTOTESTS + Mappings.API_VERSION + Mappings.BOOK_REVIEW;

    @Mock
    private ReviewService service;

    @InjectMocks
    private ReviewController controller;

    @BeforeAll
    public static void init() {
        mapper = new ObjectMapper();
        date = new Date();
        review = new Review();
        review.setComment("test comment");
        review.setAddDate(date);
        reviewDTO = new ReviewDTO("test comment", date);
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getBooksTest() throws Exception {
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        when(service.getAllReviews()).thenReturn(reviews);
        mockMvc.perform(get(BASE_URL + Mappings.GET_ALL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object[0].comment", Matchers.is("test comment")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object[0].addDate", Matchers.is(date.getTime())));
    }
    @Test
    public void getBookByIdTest() throws Exception {
        when(service.getReviewById(1L)).thenReturn(review);
        mockMvc.perform(get(BASE_URL + Mappings.GET_ONE, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.comment", Matchers.is("test comment")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.addDate", Matchers.is(date.getTime())));
    }

    @Test
    public void createBookTest() throws Exception {
        String jsonRequest = mapper.writeValueAsString(reviewDTO);
        when(service.createReview(reviewDTO)).thenReturn(review);
        mockMvc.perform(post(BASE_URL + Mappings.SAVE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.comment", Matchers.is("test comment")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.addDate", Matchers.is(date.getTime())));
    }

    @Test
    public void updateBookTest() throws Exception {
        ReviewDTO updatedDTO = new ReviewDTO();
        updatedDTO.setComment("updated comment");

        Review updatedReview = new Review();
        updatedReview.setComment("updated comment");

        String jsonRequest = mapper.writeValueAsString(updatedDTO);

        when(service.updateReview(1L, updatedDTO)).thenReturn(updatedReview);
        mockMvc.perform(post(BASE_URL + Mappings.UPDATE, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.comment", Matchers.is("updated comment")));
    }

    @Test
    public void deleteBookTest() throws Exception {
        mockMvc.perform(delete(BASE_URL + Mappings.REMOVE, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service).deleteReview(1L);
    }

}