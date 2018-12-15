package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryDTO {

	@NotBlank(message = "Book category name is required")
	@Size(max = 50, message = "Book category name can't exceed 50 characters")
	private String bookCategoryName;
}
