package com.nokia.library.nokiainnovativeproject.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class BookToOrder implements Serializable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 10, max = 13, message = "ISBN must be 10-13 numbers long")
    @NotBlank(message = "ISBN is required")
    private String isbn;

    @Length(max = 100, message = "Title can't exceed 100 characters")
    @NotBlank(message = "Title is required")
    private String title;
}