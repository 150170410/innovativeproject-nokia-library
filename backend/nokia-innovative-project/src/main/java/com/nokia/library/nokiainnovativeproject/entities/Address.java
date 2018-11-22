package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Address implements Serializable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "City name can't be empty")
    private String city;

    @NotEmpty(message = "Building name can't be empty")
    private String building;
}
