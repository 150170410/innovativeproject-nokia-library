package com.nokia.library.nokiainnovativeproject.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    private String description;

    private String coverPictureUrl;

    private Date dateOfPublication;

    private String tableOfContents;

    @ManyToMany
	private List<Author> authors;

	@ManyToMany
	private List<BookCategory> categories;
}
