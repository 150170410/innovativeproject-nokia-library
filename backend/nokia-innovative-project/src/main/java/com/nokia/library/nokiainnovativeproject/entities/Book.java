package com.nokia.library.nokiainnovativeproject.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="book")
@EntityListeners(AuditingEntityListener.class)
public class Book implements Serializable {
	
	@Id
	@Column(name="book_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="book_name")
	private String title;
	
	@Column(name="author_name")
	private String authorName;
	
	@Column(name="author_surname")
	private String authorSurname;
}