package com.nokia.library.nokiainnovativeproject.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
@EntityListeners(AuditingEntityListener.class)
public class Book implements Serializable {

	@Id
	@Column(name = "book_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "book_name")
	private String title;

	@Column(name = "author_name")
	private String authorName;

	@Column(name = "author_surname")
	private String authorSurname;

}