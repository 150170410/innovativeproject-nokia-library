package com.nokia.library.nokiainnovativeproject.entities;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Author implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@NotNull(message = "Author's name is required")
	@Size(max = 25, message = "The author's name must be 0-25 characters long")
	private String authorName;

	@Setter
	@NotNull(message = "Author's surname is required")
	@Size(min = 3, max = 25, message = "The author's surname must be 3-25 characters long")
	private String authorSurname;

	@Setter
	@Size(max = 200, message = "Author description should have no more than 200 characters")
	private String authorDescription;
}
