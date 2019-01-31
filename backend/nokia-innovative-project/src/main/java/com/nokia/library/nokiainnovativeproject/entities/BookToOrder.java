package com.nokia.library.nokiainnovativeproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookToOrder implements Serializable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 10, max = 17, message = "ISBN must be 10-17 numbers long")
    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "(([0-9Xx][- ]*){13}|([0-9Xx][- ]*){10})",
            message = "ISBN is not valid")
    private String isbn;

    @Length(max = 100, message = "Title can't exceed 100 characters")
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "At least one user is required.")
    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(name = "book_to_order_user",
            joinColumns = @JoinColumn(name = "book_to_order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @NotNull
    private User requestCreator;

    @CreationTimestamp
    private LocalDateTime creationDate;
}