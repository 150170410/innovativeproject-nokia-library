package com.nokia.library.nokiainnovativeproject.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

	@NotBlank(message = "Author's name is required")
	@Size(max = 300, message = "The maximum length of the author's name can't exceed 300 characters")
	private String authorName;

	@NotBlank(message = "Author's surname is required")
	@Size(max = 300, message = "The maximum length of the author's surname can't exceed 300 characters")
	private String authorSurname;

	@Size(max = 10000, message = "Author description should have no more than 10000 characters")
	private String authorDescription;
}
