package com.nokia.library.nokiainnovativeproject.DTOs;


import com.nokia.library.nokiainnovativeproject.entities.Book;
import com.nokia.library.nokiainnovativeproject.entities.User;
import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
public class RentalDTO {


    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotNull(message = "Book ID is required.")
    private Long bookId;

}
