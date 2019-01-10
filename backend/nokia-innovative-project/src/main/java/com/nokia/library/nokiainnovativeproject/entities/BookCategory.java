package com.nokia.library.nokiainnovativeproject.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class BookCategory implements Serializable {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Book category name is required")
	@Size(max = 50, message = "Book category name can't exceed 50 characters")
	private String bookCategoryName;

	@ColumnDefault("true")
	private Boolean isRemovable;
}
