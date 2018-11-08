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
    @NotNull(message = "The city name is required")
    @Size(min = 3, max = 25, message = "Build name must be 3-25 characters length")
    private String city;

    @Setter
    @NotNull(message = "The build name is required")
    @Size(min = 3, max = 30, message = "Build name must be 3-30 characters length")
    private String building;
}
