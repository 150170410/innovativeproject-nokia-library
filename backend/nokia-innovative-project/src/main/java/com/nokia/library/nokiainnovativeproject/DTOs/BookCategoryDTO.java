package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryDTO {

	@NotNull(message = "Book category name is required")
	@Size(max = 20, message = "Book category name can't be longer than 20 characters")
	private String bookCategoryName;
}
