package com.blog.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority; // Interface representing an authority granted to an Authentication object
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Basic implementation of GrantedAuthority with a string representation
import org.springframework.security.core.userdetails.UserDetails; // Provides core user information

import jakarta.persistence.CascadeType; // Enum defining cascade operations in JPA
import jakarta.persistence.Column; // Specifies the mapped column for a persistent property or field
import jakarta.persistence.Entity; // Specifies that this class is an entity and will be mapped to a database table
import jakarta.persistence.FetchType; // Enum defining fetch strategies in JPA
import jakarta.persistence.GeneratedValue; // Specifies that the primary key value will be generated automatically
import jakarta.persistence.GenerationType; // Specifies the primary key generation strategies
import jakarta.persistence.Id; // Specifies the primary key of an entity
import jakarta.persistence.JoinColumn; // Specifies a column for joining an entity association
import jakarta.persistence.JoinTable; // Specifies a join table for a many-to-many association
import jakarta.persistence.ManyToMany; // Specifies a many-to-many association
import jakarta.persistence.OneToMany; // Specifies a one-to-many association
import jakarta.persistence.Table; // Specifies the primary table for an entity
import lombok.Getter; // Lombok annotation to generate getters for all fields
import lombok.NoArgsConstructor; // Lombok annotation to generate a no-argument constructor
import lombok.Setter; // Lombok annotation to generate setters for all fields

@Entity // Specifies that this class is an entity and will be mapped to a database table
@Table(name = "users") // Specifies the table name for the entity
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@Getter // Lombok annotation to generate getters for all fields
@Setter // Lombok annotation to generate setters for all fields
public class User implements UserDetails { // Implements UserDetails to integrate with Spring Security
	@Id // Specifies the primary key of an entity
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the generation strategy for primary keys
	@Column(nullable = false, updatable = false) // Specifies column properties
	private Integer id; // Field to store the unique identifier for each user

	@Column(name = "user_name", nullable = false) // Specifies column properties and maps to a different column name
	private String name; // Field to store the user's name

	@Column(nullable = false, unique = true) // Specifies column properties and ensures email is unique
	private String email; // Field to store the user's email

	@Column(nullable = false) // Specifies column properties
	private String password; // Field to store the user's password

	private String about; // Field to store additional information about the user

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // Defines a one-to-many relationship with posts
	private List<Post> posts = new ArrayList<>(); // Field to store the user's posts

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Defines a many-to-many relationship with roles
	@JoinTable(name = "user_role", // Specifies the join table for the relationship
		joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"), // Defines the join column for the user
		inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")) // Defines the join column for the role
	private Set<Role> roles = new HashSet<>(); // Field to store the user's roles

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Maps roles to granted authorities required by Spring Security
		List<SimpleGrantedAuthority> authorities = this.roles.stream()
			.map(role -> new SimpleGrantedAuthority(role.getName()))
			.collect(Collectors.toList());
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email; // Uses email as the username for authentication
	}
}
