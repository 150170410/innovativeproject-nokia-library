package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    @NotBlank(message = "Author's name is required")
    @Size(max = 300, message = "The maximum length of the author's name can't exceed 300 characters")
    private String authorName;

    @NotBlank(message = "Author's surname is required")
    @Size(max = 300, message = "The maximum length of the author's surname can't exceed 300 characters")
    private String authorSurname;

    @Size(max = 10000, message = "Author description can't exceed 10000 characters")
    private String authorDescription;
}
