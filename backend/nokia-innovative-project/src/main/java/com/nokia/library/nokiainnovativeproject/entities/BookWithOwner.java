package com.nokia.library.nokiainnovativeproject.entities;

import lombok.Data;

import java.util.List;

@Data
public class BookWithOwner extends Book {

    private Long id;

    private User actualOwner;
}
