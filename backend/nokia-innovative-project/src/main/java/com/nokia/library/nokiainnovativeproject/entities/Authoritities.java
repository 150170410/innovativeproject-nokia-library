package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;

import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
class AuthorititiesPK implements Serializable {

    @Setter
    protected Long user_id;

    @Setter
    protected String role;
}

@Entity
@Getter
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