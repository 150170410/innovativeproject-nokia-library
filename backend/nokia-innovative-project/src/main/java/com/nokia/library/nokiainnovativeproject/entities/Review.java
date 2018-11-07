package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotNull(message = "The comment is required")
    @Size(max = 300, message = "A comment can have a maximum of 300 characters")
    private String comment;

    @Setter
    @NotNull(message = "The date of the comment is required")
    @Past(message = "The date of the comment must be present")
    private Date addDate;

    @Setter
    @ManyToOne( cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH },
                fetch = FetchType.LAZY  )
    @JoinColumn(name = "user_id")
    private User user;
}
