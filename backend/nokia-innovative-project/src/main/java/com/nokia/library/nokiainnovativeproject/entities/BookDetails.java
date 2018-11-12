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
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Size(min = 10, max = 13, message = "ISBN must be 10-13 numbers long")
	@NotNull(message = "ISBN is required")
	private String isbn;

	@Setter
	@Length(max = 30, message = "Title can't exceed 30 characters")
	@NotNull(message = "Title is required")
	private String title;

	@Setter
	@Size(max = 250, message = "Description can't exceed 250 characters")
	private String description;

	@Setter
	@Size(max = 100, message = "Cover picture URL can't exceed 100 characters")
	private String coverPictureUrl;

	@Setter
	@Past(message = "Rental date should be a past date")
	private Date dateOfPublication;

	@Setter
	@Size(max = 100, message = "Table of contents URL can't exceed 100 characters")
	private String tableOfContents;

	@Setter
	@ManyToMany(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "authors")
	private List<Author> authors;

	@Setter
	@ManyToMany(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "book_category_id")
	private List<BookCategory> categories;

	@Setter
	@OneToMany(cascade = {CascadeType.DETACH,
			CascadeType.MERGE,
			CascadeType.PERSIST,
			CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private List<Review> reviews;
}