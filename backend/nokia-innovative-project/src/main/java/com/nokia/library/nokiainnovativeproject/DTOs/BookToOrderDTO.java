package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookToOrderDTO {

    @Size(min = 10, max = 13, message = "ISBN must be 10-13 numbers long")
    @NotBlank(message = "ISBN is required")
    private String isbn;

    @Length(max = 100, message = "Title can't exceed 100 characters")
    @NotBlank(message = "Title is required")
    private String title;
}
