package com.nokia.library.nokiainnovativeproject.entities;

import lombok.Data;

@Data
public class BookWithOwner extends Book {

    private Long id;

    private User actualOwner;
}
