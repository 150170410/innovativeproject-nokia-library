package com.nokia.library.nokiainnovativeproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor
public class BookControllerTest {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final RestTemplate restTemplate = new RestTemplate();

	@Test
	public void testCreateBook() throws Exception {
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("title", "sum titel");
		requestBody.put("authorName", "sum nam");
		requestBody.put("authorSurname", "sum nome");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

		String url = "http://localhost:8080" + Mappings.API_VERSION + Mappings.LIBRARY + Mappings.BOOKS;
		Map<String, Object> apiResponse = restTemplate.postForObject(url, httpEntity, Map.class, Collections.emptyMap());

		Assert.assertNotNull(apiResponse);


	}

}
