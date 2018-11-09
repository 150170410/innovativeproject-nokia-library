package com.nokia.library.nokiainnovativeproject.DTOs;

import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailsDTO {

	private String isbn;
	private String title;
	private String description;
	private String coverPictureUrl;
	private Date dateOfPublication;
	private String tableOfContents;

	private List<Author> authors = new ArrayList<>();
	private List<BookCategory> categories = new ArrayList<>();

}
