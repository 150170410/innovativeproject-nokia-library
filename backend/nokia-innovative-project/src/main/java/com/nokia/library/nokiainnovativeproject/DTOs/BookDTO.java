package com.nokia.library.nokiainnovativeproject.DTOs;

import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

	@Size(max = 5000, message = "Comments can't exceed 5000 characters")
	private String comments;

	@NotNull(message = "Book details are required.")
	private BookDetails bookDetails;
}
