package com.nokia.library.nokiainnovativeproject.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Rental implements Serializable {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Setter(AccessLevel.NONE)
	private Date rentalDate;

	@Setter
	@NotNull(message = "The return date should be defined")
	@Future(message = "The return date should be future")
	private Date returnDate;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_id")
	private User user;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "book_catalog_number")
	private Book book;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Rental )) return false;
		return id != null && id.equals(((Rental) o).id);
	}

}
