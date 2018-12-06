package com.nokia.library.nokiainnovativeproject.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class BookDetails implements Serializable {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 10, max = 13, message = "ISBN must be 10-13 numbers long")
	@NotBlank(message = "ISBN is required")
	private String isbn;

	@Length(max = 100, message = "Title can't exceed 100 characters")
	@NotBlank(message = "Title is required")
	private String title;

	@Size(max = 1000, message = "Description can't exceed 1000 characters")
	private String description;

	@Size(max = 1000, message = "Cover picture URL can't exceed 1000 characters")
	private String coverPictureUrl;

	@Past(message = "Publication date should be a past date")
	private Date publicationDate;

	@Size(max = 100, message = "Table of contents URL can't exceed 100 characters")
	private String tableOfContents;

	@ManyToMany(cascade = {
			CascadeType.MERGE,
            CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinTable(name = "book_details_authors",
			joinColumns = @JoinColumn(name = "book_details_id"),
			inverseJoinColumns = @JoinColumn(name = "author_id"))
	private List<Author> authors;

	@ManyToMany(cascade = {
			CascadeType.MERGE,
            CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinTable(name = "book_details_categories",
			joinColumns = @JoinColumn(name = "book_details_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<BookCategory> categories;

	@OneToMany(cascade = {
			CascadeType.MERGE,
            CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "book_details_id")
	private List<Review> reviews;

	@OneToMany(mappedBy = "bookDetails",
			cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	private List<Book> books;
}
