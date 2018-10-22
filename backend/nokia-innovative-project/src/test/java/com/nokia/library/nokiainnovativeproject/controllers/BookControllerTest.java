package com.nokia.library.nokiainnovativeproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RequiredArgsConstructor
public class BookControllerTest {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final RestTemplate restTemplate = new RestTemplate();

	@Test
	public void testCreateBook() throws Exception {
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("title", "test title1");
		requestBody.put("authorName", "test name1");
		requestBody.put("authorSurname", "test surname1");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

		String url = "http://localhost:8080" + Mappings.API_VERSION + Mappings.LIBRARY + Mappings.BOOKS;
		Map<String, Object> apiResponse = restTemplate.postForObject(url, httpEntity, Map.class, Collections.emptyMap());

		Assert.assertNotNull(apiResponse);
	}

	@Test
	public void testGetBookById() throws Exception {
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("title", "test title2");
		requestBody.put("authorName", "test name2");
		requestBody.put("authorSurname", "test surname2");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

		String urlCreate = "http://localhost:8080" + Mappings.API_VERSION + Mappings.LIBRARY + Mappings.BOOKS;
		Map<String, Object> apiResponseCreate = restTemplate.postForObject(urlCreate, httpEntity, Map.class, Collections.emptyMap());

		String urlGetById = "http://localhost:8080" + Mappings.API_VERSION + Mappings.LIBRARY + Mappings.BOOKS_ID;
		Map<String, Object> apiResponseGetById = restTemplate.getForObject(urlGetById, Map.class, apiResponseCreate.get("id"), Collections.emptyMap());
		apiResponseGetById.put("id", apiResponseCreate.get("id"));
		Assert.assertTrue(apiResponseGetById.equals(apiResponseCreate));
	}

	@Test
	public void testDeleteBook() throws Exception {
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("title", "test title3");
		requestBody.put("authorName", "test name3");
		requestBody.put("authorSurname", "test surname3");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);

		String urlCreate = "http://localhost:8080" + Mappings.API_VERSION + Mappings.LIBRARY + Mappings.BOOKS;
		Map<String, Object> apiResponseCreate = restTemplate.postForObject(urlCreate, httpEntity, Map.class, Collections.emptyMap());

		String urlGetById = "http://localhost:8080" + Mappings.API_VERSION + Mappings.LIBRARY + Mappings.BOOKS_ID;
		Map<String, Object> apiResponseGetById = restTemplate.getForObject(urlGetById, Map.class, apiResponseCreate.get("id"), Collections.emptyMap());
		apiResponseGetById.put("id", apiResponseCreate.get("id"));

		Assert.assertTrue(apiResponseGetById.equals(apiResponseCreate));

		String urlDelete = "http://localhost:8080" + Mappings.API_VERSION + Mappings.LIBRARY + Mappings.BOOKS_ID;
		restTemplate.delete(urlDelete, apiResponseCreate.get("id"));
	}

}
