package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookDetails implements Serializable {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 10, max = 13, message = "ISBN must be 10-13 numbers long")
	@NotNull(message = "ISBN is required")
	private String isbn;

	@Length(max = 30, message = "Title can't exceed 30 characters")
	@NotNull(message = "Title is required")
	private String title;

	@Size(max = 250, message = "Description can't exceed 250 characters")
	private String description;

	@Size(max = 100, message = "Cover picture URL can't exceed 100 characters")
	private String coverPictureUrl;

	@Past(message = "Rental date should be a past date")
	private Date dateOfPublication;

	@Size(max = 100, message = "Table of contents URL can't exceed 100 characters")
	private String tableOfContents;

	@ManyToMany(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "authors")
	private List<Author> authors;

	@ManyToMany(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "book_category_id")
	private List<BookCategory> categories;

	@OneToMany(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private List<Review> reviews;
}