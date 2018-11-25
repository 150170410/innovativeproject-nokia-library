package com.nokia.library.nokiainnovativeproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.DTOs.EmailDTO;
import com.nokia.library.nokiainnovativeproject.services.EmailService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("autotests")
public class EmailControllerTest {

    private static final String BASE_URL = Mappings.PORT_AUTOTESTS + Mappings.API_VERSION + Mappings.EMAIL;
    private MockMvc mockMvc;
    private static EmailDTO emailDTO;
    private static ObjectMapper mapper;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @BeforeAll
    public static void init() {
        mapper = new ObjectMapper();
        emailDTO = new EmailDTO();
        emailDTO.setSubject("subject");
        emailDTO.setMessageContext("message context");
        emailDTO.setRecipients(Arrays.asList("efesvddvve@gmail.com"));
    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }

    @Test
    public void createEmailTest() throws Exception {
        String jsonRequest = mapper.writeValueAsString(emailDTO);
        mockMvc.perform(post(BASE_URL + Mappings.CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk());

        emailDTO.setRecipients(null);
        jsonRequest = mapper.writeValueAsString(emailDTO);
        mockMvc.perform(post(BASE_URL + Mappings.CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.contains("You must specify at least one recipient")));
    }
}