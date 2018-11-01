package com.nokia.library.nokiainnovativeproject.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	@NotNull(message = "The author's name is required")
	@Size(max = 25, message = "The author's name must be 0-25 characters length")
	private String authorName;

	@Setter
	@NotNull(message = "The author's surname is required")
	@Size(min = 3, max = 25, message = "The author's surname must be 3-25 characters length")
	private String authorSurname;

	@Setter
	@Size(max = 200, message = "The description of the author should have no more than 200 characters")
	private String authorDescription;
}
