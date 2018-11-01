package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;

import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
class AuthorititiesPK implements Serializable {

    @Setter
    @NotNull(message = "The user's id is required")
    @Size(max = 10, message = "The user's id must be 0-10 characters length")
    protected Long user_id;

    @Setter
    @NotNull(message = "The user's role is required")
    @Size(max = 15, message = "The user's role must be 0-15 characters length")
    protected String role;
}

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Authoritities implements Serializable {

    @EmbeddedId
    private AuthorititiesPK authorititiesPK;

    @Setter
    @ManyToOne( cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH },
                fetch = FetchType.LAZY,
                optional = false)
    @JoinColumn(name = "user_id",
                insertable = false,
                updatable = false)
    private Users user;
}