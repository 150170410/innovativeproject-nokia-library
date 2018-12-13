package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AutocompletionService {

    public BookDetailsDTO getBookDetailsFromApiItBookStore(String isbn) throws ParseException {
        BookDetailsDTO bookDetailsDTO = null;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String uri = "https://api.itbook.store/1.0/books/";
        ResponseEntity<String> result = restTemplate.exchange(uri + isbn, HttpMethod.GET, entity, String.class);

        JSONObject bookJSON = new JSONObject(result.toString().replace("<200,",""));

        if(bookJSON.has("title")) {
            bookDetailsDTO = new BookDetailsDTO();
            bookDetailsDTO.setTitle(bookJSON.getString("title"));
            if(bookJSON.has("image"))
            bookDetailsDTO.setCoverPictureUrl(bookJSON.getString("image"));
            if(bookJSON.has("desc"))
            bookDetailsDTO.setDescription(bookJSON.getString("desc"));
            String[] authors = bookJSON.getString("authors").split(", ");
            List<Author> authorList = new ArrayList<>();
            for(String name : authors){
                Author author = new Author();
                author.setAuthorFullName(name);
                authorList.add(author);
            }
            if(bookJSON.has("year")) {
                bookDetailsDTO.setAuthors(authorList);
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.YEAR, Integer.parseInt(bookJSON.getString("year").substring(0, 4)) + 1);
                Date date = calendar.getTime();
                bookDetailsDTO.setPublicationDate(date);
            }
        }
        return bookDetailsDTO;
    }

    public BookDetailsDTO getBookDetailsFromApiGoogle(String isbn) throws ParseException {
        BookDetailsDTO bookDetailsDTO = null;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        String uri = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
        ResponseEntity<String> result = restTemplate.exchange(uri + isbn, HttpMethod.GET, entity, String.class);

        JSONObject responseJSON = new JSONObject(result.toString().replace("<200,",""));
        if(responseJSON.getInt("totalItems") > 0) {
            bookDetailsDTO = new BookDetailsDTO();
            JSONObject volumeInfo = responseJSON.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
            bookDetailsDTO.setTitle(volumeInfo.getString("title"));
            if(volumeInfo.has("imageLinks"))
            bookDetailsDTO.setCoverPictureUrl(volumeInfo.getJSONObject("imageLinks").getString("thumbnail"));
            if(volumeInfo.has("description"))
            bookDetailsDTO.setDescription(volumeInfo.getString("description"));
            List<Author> authorList = new ArrayList<>();
            String[] authors = volumeInfo.getJSONArray("authors").toString()
                    .replace("[","")
                    .replace("]", "")
                    .replace("\"","")
                    .split(",");
            for(String name : authors){
                Author author = new Author();
                author.setAuthorFullName(name);
                authorList.add(author);
            }
            bookDetailsDTO.setAuthors(authorList);
            if(volumeInfo.has("publishedDate")) {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.YEAR, Integer.parseInt(volumeInfo.getString("publishedDate").substring(0, 4)) + 1);
                Date date = calendar.getTime();
                bookDetailsDTO.setPublicationDate(date);
            }
        }
        return bookDetailsDTO;
    }
}
