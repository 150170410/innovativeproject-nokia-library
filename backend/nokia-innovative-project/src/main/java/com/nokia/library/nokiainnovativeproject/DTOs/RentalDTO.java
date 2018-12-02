package com.nokia.library.nokiainnovativeproject.DTOs;


import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.User;
import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class RentalDTO {

    @Getter
    @NotNull(message = "User ID is required.")
    private Long userId;

    @Getter
    @NotNull(message = "Book ID is required.")
    private Long bookId;

    @NotNull(message = "The return date should be defined")
    @Future(message = "The return date should be future")
    @Getter
    private Date returnDate;
}
