package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    @NotNull(message = "City name is required")
    @Size(min = 3, max = 25, message = "City name must be 3-25 characters long")
    private String city;

    @Setter
    @NotNull(message = "Building name is required")
    @Size(min = 3, max = 30, message = "Building name must be 3-30 characters long")
    private String building;
}
