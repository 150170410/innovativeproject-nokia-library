package com.nokia.library.nokiainnovativeproject.DTOs;

import com.nokia.library.nokiainnovativeproject.entities.BookDetails;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

	private String comments;
	private BookDetails bookDetails;

}
