package com.nokia.library.nokiainnovativeproject.DTOs;

import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

	@Size(max = 100, message = "Signature can't exceed 100 characters")
	@NotBlank(message = "Signature is required")
	private String signature;

	@Size(max = 5000, message = "Comments can't exceed 5000 characters")
	private String comments;

	@NotNull(message = "Book details is required.")
	private Long bookDetailsId;

    @NotNull(message = "Book status is required.")
	private Long bookStatusId;
}
