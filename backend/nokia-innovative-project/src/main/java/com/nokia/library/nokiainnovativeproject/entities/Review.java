package com.nokia.library.nokiainnovativeproject.entities;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String comment;

    @Setter
    private Date addDate;

    @Setter
    @ManyToOne( cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH },
                fetch = FetchType.LAZY  )
    @JoinColumn(name = "user_id")
    private Users user;
}
