package com.nokia.library.nokiainnovativeproject.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class BookDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Integer isbn;

    @Setter
    private String title;

    @Setter
    private String description;

    @Setter
    private String coverPictureUrl;

    @Setter
    private Date dateOfPublication;

    @Setter
    private String tableOfContents;

    @Setter
    @ManyToMany(cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH },
                fetch = FetchType.LAZY)
    @JoinColumn(name = "authors")
	private List<Author> authors;

    @Setter
	@ManyToMany(    cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH },
                    fetch = FetchType.LAZY  )
    @JoinColumn(    name = "book_category_id")
	private List<BookCategory> categories;

    @Setter
    @OneToMany( cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private List<Review> reviews;
}