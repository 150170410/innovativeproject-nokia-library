package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    @NotNull(message = "Author's name is required")
    @Size(max = 300, message = "Author's name must be 0-300 characters long")
    private String authorName;

    @NotBlank(message = "Author's surname is required")
    @Size(max = 300, message = "The author's surname must be 1-300 characters long")
    private String authorSurname;

    @Size(max = 10000, message = "Author description can't exceed 200 characters")
    private String authorDescription;
}
