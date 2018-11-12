package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name="\"User\"")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    @Size(min = 3, max = 30, message = "User's name must be 3-30 characters long")
    @NotBlank(message = "User's name can't be null and can't contain whitespace")
    private String firstName;

    @Setter
    @Size(min = 3, max = 30, message = "User's surname must be 3-30 characters long")
    @NotBlank(message = "User's surname can't be null and can't contain whitespace")
    private String lastName;

    @Setter
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email can't be null and can't contain whitespace")
    @Size(min = 10, max = 40, message = "User email must be 10-40 characters long")
    private String email;

    @Setter
    @OneToOne(  cascade = CascadeType.ALL,
                fetch = FetchType.LAZY  )
    @JoinColumn(name = "address_id")
    private Address address;

    @Setter
    @OneToMany(mappedBy = "user",
                fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH,
                        CascadeType.MERGE,
                        CascadeType.PERSIST,
                        CascadeType.REFRESH})
    private List<Reservation> reservations;

    @Setter
    @OneToMany( mappedBy = "user",
                cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH },
                fetch = FetchType.LAZY)
    private List<Review> reviews;

    @Setter
    @ManyToMany(cascade ={  CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    private List<Book> books;

    @ManyToMany(cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    private List<Role> roles;
}