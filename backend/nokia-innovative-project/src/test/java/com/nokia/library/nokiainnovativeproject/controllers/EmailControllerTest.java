package com.nokia.library.nokiainnovativeproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
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
        emailDTO.setSubject("subject");
        emailDTO.setMessageContext("message context");
        emailDTO.setRecipients(Arrays.asList("efesvddvve@gmail.com"));
    }

    @Test
    public void createEmailTest() throws Exception {
        String jsonRequest = mapper.writeValueAsString(emailDTO);
        mockMvc.perform(post(BASE_URL + Mappings.CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void emailCantContainNull() throws Exception {
        emailDTO.setRecipients(null);
        String jsonRequest = mapper.writeValueAsString(emailDTO);
        mockMvc.perform(post(BASE_URL + Mappings.CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.contains("You must specify at least one recipient")));
    }

    @Test
	public void emailDoesntExistTest() throws Exception {
		emailDTO.setRecipients(Arrays.asList("noSuchEmail"));
        String jsonRequest = mapper.writeValueAsString(emailDTO);
		mockMvc.perform(post(BASE_URL + Mappings.CREATE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(false)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.contains("Email addresses must be in the right form")));
	}

	@Test
	public void messageCantBeEmpty() throws Exception {
        emailDTO.setMessageContext("");
        String jsonRequest = mapper.writeValueAsString(emailDTO);
        mockMvc.perform(post(BASE_URL + Mappings.CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.contains("The message can't be empty")));
    }

    @Test
    public void subjectContBeEmpty() throws Exception {
        emailDTO.setSubject("");
        String jsonRequest = mapper.writeValueAsString(emailDTO);
        mockMvc.perform(post(BASE_URL + Mappings.CREATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.contains("The subject of the message is required")));
    }
}