package com.nokia.library.nokiainnovativeproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookDetails implements Serializable {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 10, max = 13, message = "ISBN must be 10 or 13 numbers long")
	@NotBlank(message = "ISBN is required")
	@Pattern(regexp = "(([0-9Xx][- ]*){13}|([0-9Xx][- ]*){10})", message = "ISBN is not valid")
	protected String isbn;

	@Size(max = 1000, message = "Title can't exceed 1000 characters")
	@NotBlank(message = "Title is required")
	protected String title;

	@Size(max = 20000, message = "Description can't exceed 20000 characters")
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

	@ColumnDefault("true")
	protected  Boolean isRemovable;
}
