package com.nokia.library.nokiainnovativeproject.DTOs;

import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

	@Size(max = 300, message = "Comments can't exceed 300 characters")
	private String comments;

	@NotNull(message = "Book details are required.")
	private BookDetails bookDetails;

}
