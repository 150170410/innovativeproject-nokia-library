package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Book implements Serializable {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long catalogNumber;

	@Size(max = 5000, message = "Comments can't exceed 5000 characters")
	private String comments;

	@ManyToOne(cascade = { CascadeType.MERGE,
			CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "book_details_id")
	private BookDetails bookDetails;
}
