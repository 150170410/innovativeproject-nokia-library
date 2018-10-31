package com.nokia.library.nokiainnovativeproject.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catalogNumber;

    @Setter
    @ManyToOne( cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH},
                fetch = FetchType.LAZY  )
    @JoinColumn(name = "book_id")
    private BookDetails bookDetails;

    @Setter
    private String comments;

    @Setter
    @OneToMany( cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH},
                fetch = FetchType.LAZY  )
    @JoinColumn(name = "library_id")
    private List<Library> library;
}