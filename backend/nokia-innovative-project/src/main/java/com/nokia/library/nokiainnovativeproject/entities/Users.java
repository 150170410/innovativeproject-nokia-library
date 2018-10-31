package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Setter
    private String firstName;

    @Setter
    private String lastName;

    @Setter
    private String email;

    @Setter
    @OneToOne(  cascade = CascadeType.ALL,
                fetch = FetchType.LAZY  )
    @JoinColumn(name = "address_id")
    private Address address;

    @Setter
    @OneToMany(mappedBy = "user",
                fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH,
                        CascadeType.MERGE,
                        CascadeType.PERSIST,
                        CascadeType.REFRESH})
    private List<Reservation> reservations;

    @Setter
    @OneToMany( mappedBy = "user",
                cascade = { CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH },
                fetch = FetchType.LAZY)
    private List<Review> reviews;
}