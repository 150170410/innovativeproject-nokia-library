package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Author implements Serializable {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Author's name is required")
	@Size(max = 300, message = "The author's name must be 0-300 characters long")
	private String authorName;

	@NotBlank(message = "Author's surname is required")
	@Size(max = 300, message = "The author's surname must be 1-25 characters long")
	private String authorSurname;

	@Size(max = 10000, message = "Author description should have no more than 10000 characters")
	private String authorDescription;
}
