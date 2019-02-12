package com.nokia.library.nokiainnovativeproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "\"User\"")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
	private Long id;

	@Size(min = 3, max = 30, message = "User's name must be 3-30 characters long")
	@NotBlank(message = "User's name can't be null and can't contain whitespace")
	private String firstName;

	@Size(min = 3, max = 30, message = "User's surname must be 3-30 characters long")
	@NotBlank(message = "User's surname can't be null and can't contain whitespace")
	private String lastName;

	@Email(message = "Email should be valid")
	@NotBlank(message = "Email can't be empty")
	@Size(max = 255, message = "The maximum size of the email is 255")
	private String email;

	@Getter(AccessLevel.NONE)
	@NotNull(message = "Password can't be null")
	@Size(min = 7, max = 255, message = "Password must be 7-255 characters long")
	private String password;

	@OneToMany(cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST,},
			fetch = FetchType.LAZY)
	@JoinColumn(name = "user_books")
	private List<Book> books;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST,
					CascadeType.MERGE})
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private List<Role> roles;

	private Boolean isAccountActive;
}