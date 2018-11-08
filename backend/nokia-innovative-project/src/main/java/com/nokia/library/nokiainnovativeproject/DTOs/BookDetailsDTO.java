package com.nokia.library.nokiainnovativeproject.DTOs;

import com.nokia.library.nokiainnovativeproject.entities.Author;
import lombok.*;

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
}
