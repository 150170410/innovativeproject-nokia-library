package com.nokia.library.nokiainnovativeproject.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Library implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @ManyToOne(cascade = { CascadeType.MERGE,
                            CascadeType.PERSIST},
                fetch = FetchType.LAZY)
    @JoinColumn(name = "library_address")
    private Address address;

    private String additionalInformation;

    @OneToMany(cascade = { CascadeType.MERGE,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_library")
    private List<User> users;

    @OneToMany(cascade = { CascadeType.MERGE,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "book_library")
    private List<Book> books;
}
