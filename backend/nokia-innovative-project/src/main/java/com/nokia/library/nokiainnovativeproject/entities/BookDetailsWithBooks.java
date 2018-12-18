package com.nokia.library.nokiainnovativeproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailsWithBooks extends BookDetails implements Serializable {

    private Long id;

    private List<BookWithoutBookDetails> books;
}
