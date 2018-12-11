package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.BookDetailsDTO;
import com.nokia.library.nokiainnovativeproject.entities.Author;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
            bookDetailsDTO.setIsbn(bookJSON.getString("isbn13"));
            bookDetailsDTO.setTitle(bookJSON.getString("title"));
            bookDetailsDTO.setCoverPictureUrl(bookJSON.getString("image"));
            bookDetailsDTO.setDescription(bookJSON.getString("desc"));
            String[] authors = bookJSON.getString("authors").split(", ");
            List<Author> authorList = new ArrayList<>();
            for(String name : authors){
                Author author = new Author();
                author.setAuthorFullName(name);
                authorList.add(author);
            }
            bookDetailsDTO.setAuthors(authorList);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            Date date = formatter.parse(bookJSON.getString("year"));
            bookDetailsDTO.setPublicationDate(date);
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
            JSONArray identifiers = volumeInfo.getJSONArray("industryIdentifiers");
            for(int i = 0; i < identifiers.length(); i++){
                if(identifiers.getJSONObject(i).get("type").equals("ISBN_13") || identifiers.getJSONObject(i).get("type").equals("ISBN_10"))
                    bookDetailsDTO.setIsbn(identifiers.getJSONObject(i).getString("identifier"));
            }
            bookDetailsDTO.setTitle(volumeInfo.getString("title"));
            bookDetailsDTO.setCoverPictureUrl(volumeInfo.getJSONObject("imageLinks").getString("thumbnail"));
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
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(volumeInfo.getString("publishedDate"));
            bookDetailsDTO.setPublicationDate(date);
        }
        return bookDetailsDTO;
    }
}
