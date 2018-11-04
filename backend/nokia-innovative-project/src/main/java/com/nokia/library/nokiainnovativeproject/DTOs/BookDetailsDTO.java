package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class BookDetailsDTO {

	private Integer isbn;
	private String title;
	private String description;
	private String coverPictureUrl;
	private Date dateOfPublication;
	private String tableOfContents;
}
