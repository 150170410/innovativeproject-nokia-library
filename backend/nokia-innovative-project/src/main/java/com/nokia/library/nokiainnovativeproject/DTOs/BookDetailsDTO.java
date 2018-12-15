package com.nokia.library.nokiainnovativeproject.DTOs;

import com.nokia.library.nokiainnovativeproject.entities.Author;
import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.BookCategory;
import com.nokia.library.nokiainnovativeproject.entities.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsDTO {

	@Size(min = 10, max = 13, message = "ISBN must be 10-13 numbers long")
	@NotBlank(message = "ISBN is required")
	private String isbn;

	@Size(max = 100, message = "Title can't exceed 100 characters")
	@NotBlank(message = "Title is required")
	private String title;

	@Size(max = 1000, message = "Description can't exceed 1000 characters")
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
