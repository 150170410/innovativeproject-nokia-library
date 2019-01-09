package com.nokia.library.nokiainnovativeproject.DTOs;


import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RentalDTO {

    @NotNull(message = "Book ID is required.")
    private Long bookId;
}
