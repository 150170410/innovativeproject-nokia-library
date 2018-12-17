package com.nokia.library.nokiainnovativeproject.entities;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class BookStatus {
	private static Integer AVAILABLE = 0;
	private static Integer BORROWED = 1;
	private static Integer RESERVED = 2;
	private static Integer UNKNOWN = 2;

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String status;

}
