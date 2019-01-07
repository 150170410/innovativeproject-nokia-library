package com.nokia.library.nokiainnovativeproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="\"User\"")
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 30, message = "User's name must be 3-30 characters long")
    @NotBlank(message = "User's name can't be null and can't contain whitespace")
    private String firstName;

    @Size(min = 3, max = 30, message = "User's surname must be 3-30 characters long")
    @NotBlank(message = "User's surname can't be null and can't contain whitespace")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email can't be null and can't contain whitespace")
    @Size(min = 10, max = 40, message = "User email must be 10-40 characters long")
    private String email;

    @OneToOne(  cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REMOVE},
                fetch = FetchType.LAZY  )
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(cascade ={  CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    private List<Book> books;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = { CascadeType.DETACH,
                        CascadeType.MERGE   })
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private List<Role> roles;
}