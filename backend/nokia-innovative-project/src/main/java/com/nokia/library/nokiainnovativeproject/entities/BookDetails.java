package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Size(min = 10, max = 13, message = "ISBN must be 10-13 numbers length")
	@NotNull(message = "The ISBN can't be null")
	private Integer isbn;

	@Setter
	@Size(max = 30, message = "The title must have at least 30 characters")
	@NotNull(message = "The title is required")
	private String title;

	@Setter
	@Size(max = 250, message = "The title must have at least 250 characters")
	private String description;

	@Setter
	@Size(max = 100, message = "The cover picture URL must have at least 100 characters")
	private String coverPictureUrl;

	@Setter
	@Past(message = "The rental date should be past")
	private Date dateOfPublication;

	@Setter
	@Size(max = 100, message = "The table of contents URL must have at least 100 characters")
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