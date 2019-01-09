package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.Getter;

import javax.validation.constraints.NotNull;

public class ReservationDTO {

    @Getter
    @NotNull(message = "Book ID is required.")
    private Long bookId;
}
