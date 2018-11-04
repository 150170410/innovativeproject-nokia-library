package com.nokia.library.nokiainnovativeproject.entities;

import javax.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Authorities implements Serializable {

    @EmbeddedId
    private AuthoritiesPK authoritiesPK;

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