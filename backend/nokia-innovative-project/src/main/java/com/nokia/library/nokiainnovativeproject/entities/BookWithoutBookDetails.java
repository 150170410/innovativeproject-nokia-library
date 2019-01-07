package com.nokia.library.nokiainnovativeproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookWithoutBookDetails {

    private Long id;

    private String signature;

    private String comments;

    private BookStatus status;

    private LocalDate availableDate;
}
