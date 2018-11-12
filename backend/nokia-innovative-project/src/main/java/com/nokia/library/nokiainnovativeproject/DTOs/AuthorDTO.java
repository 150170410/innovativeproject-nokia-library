package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    @NotNull(message = "Author's name is required")
    @Size(max = 25, message = "Author's name must be 0-25 characters long")
    private String authorName;

    @NotNull(message = "Author's surname is required")
    @Size(min = 3, max = 25, message = "The author's surname must be 3-25 characters long")
    private String authorSurname;

    @Size(max = 200, message = "Author description can't exceed 200 characters")
    private String authorDescription;
}
