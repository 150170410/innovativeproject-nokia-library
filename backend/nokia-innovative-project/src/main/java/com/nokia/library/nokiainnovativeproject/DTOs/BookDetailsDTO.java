package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import java.util.Date;

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
