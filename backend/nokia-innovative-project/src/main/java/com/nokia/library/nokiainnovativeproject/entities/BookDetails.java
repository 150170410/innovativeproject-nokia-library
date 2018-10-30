package com.nokia.library.nokiainnovativeproject.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class BookDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer isbn;

    private String title;

    private String authors;

    private String description;

    private String coverPicture;

    private Date dateOfPublication;

    ////////////////////////////////////
    //private BookCategory bookCategory;
    ////////////////////////////////////

    private String tableOfContents;

    private String authorsDescription;
}
