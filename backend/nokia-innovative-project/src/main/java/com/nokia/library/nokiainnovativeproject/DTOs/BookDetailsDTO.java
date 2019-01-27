package com.nokia.library.nokiainnovativeproject.DTOs;

import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDTO {

	@Size(min = 10, max = 17, message = "ISBN must be 10-17 numbers long")
	@NotBlank(message = "ISBN is required")
	@Pattern(regexp = "(([0-9Xx][- ]*){13}|([0-9Xx][- ]*){10})",
			message = "ISBN is not valid")
	private String isbn;

	@Size(max = 100, message = "Title can't exceed 100 characters")
	@NotBlank(message = "Title is required")
	private String title;

	@Size(max = 2000, message = "Description can't exceed 2000 characters")
	private String description;

	@Size(max = 1000, message = "Cover picture URL can't exceed 1000 characters")
	private String coverPictureUrl;

	@Past(message = "The publication date of the book should be a past date")
	private Date publicationDate;

	@Valid
	@NotNull(message = "At least one book author is required.")
	private List<Author> authors;

	@Valid
	@NotNull(message = "At least one book category is required.")
	private List<BookCategory> categories;
}
