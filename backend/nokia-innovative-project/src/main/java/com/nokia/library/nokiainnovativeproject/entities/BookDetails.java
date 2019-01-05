package com.nokia.library.nokiainnovativeproject.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookDetails implements Serializable {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 10, max = 13, message = "ISBN must be 10-13 numbers long")
	@NotBlank(message = "ISBN is required")
	protected String isbn;

	@Size(max = 100, message = "Title can't exceed 100 characters")
	@NotBlank(message = "Title is required")
	protected String title;

	@Size(max = 2000, message = "Description can't exceed 2000 characters")
	protected String description;

	@Size(max = 1000, message = "Cover picture URL can't exceed 1000 characters")
	protected String coverPictureUrl;

	@Past(message = "Publication date should be a past date")
	protected Date publicationDate;

	@NotNull(message = "At least one book author is required.")
	@ManyToMany(cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinTable(name = "book_details_authors",
			joinColumns = @JoinColumn(name = "book_details_id"),
			inverseJoinColumns = @JoinColumn(name = "author_id"))
	protected List<Author> authors;

	@NotNull(message = "At least one book category is required.")
	@ManyToMany(cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinTable(name = "book_details_categories",
			joinColumns = @JoinColumn(name = "book_details_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	protected List<BookCategory> categories;

}
