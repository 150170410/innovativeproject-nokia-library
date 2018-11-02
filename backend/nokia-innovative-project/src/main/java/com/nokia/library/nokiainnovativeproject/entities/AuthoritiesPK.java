package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthoritiesPK implements Serializable {

    @Setter
    @NotNull(message = "The user's id is required")
    @Size(max = 10, message = "The user's id must be 0-10 characters length")
    protected Long user_id;

    @Setter
    @NotNull(message = "The user's role is required")
    @Size(max = 15, message = "The user's role must be 0-15 characters length")
    protected String role;
}