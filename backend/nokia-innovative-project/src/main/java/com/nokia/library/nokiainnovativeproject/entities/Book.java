package com.nokia.library.nokiainnovativeproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book implements Serializable {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 100, message = "Signature can't exceed 100 characters")
	@NotBlank(message = "Signature is required")
	protected String signature;

	@Size(max = 5000, message = "Comments can't exceed 5000 characters")
	protected String comments;

	@ManyToOne(cascade = {CascadeType.MERGE,
			CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "book_status_id")
	protected BookStatus status;

	@ManyToOne(cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST},
			fetch = FetchType.EAGER)
	@JoinColumn(name = "book_details_id")
	protected BookDetails bookDetails;

	@CreationTimestamp
	protected LocalDateTime availableDate;

	private Long currentOwnerId;

	@NotNull
	@OneToMany(cascade = {CascadeType.MERGE,
			CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private List<BookOwnerId> ownersId;
}
