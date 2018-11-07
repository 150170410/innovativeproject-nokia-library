package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reservation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@NotNull(message = "The rental date should be defined")
	@PastOrPresent(message = "The rental date should be present or past")
	private Date rentalDate;

	@Setter
	@NotNull(message = "The return date should be defined")
	@Future(message = "The return date should be future")
	private Date returnDate;

	@Setter
	@ManyToOne(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Setter
	@ManyToOne(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "book_catalog_number")
	private Book book;

}
