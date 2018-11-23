package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryDTO {

	@NotNull(message = "Book category name is required")
	@Size(min = 1, max = 50, message = "Book category name must be 1-50 characters long")
	private String bookCategoryName;
}
