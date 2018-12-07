package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Review implements Serializable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Comment is required")
    @Size(max = 300, message = "Comment can exceed 300 characters")
    private String comment;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private Date creationDate;

    @Setter(AccessLevel.NONE)
    @UpdateTimestamp
    private Date lastEditionDate;

    @ManyToOne( cascade = {CascadeType.MERGE,
                            CascadeType.PERSIST,},
                fetch = FetchType.LAZY  )
    @JoinColumn(name = "user_id")
    private User user;
}
