package com.blog.entities;

import jakarta.persistence.Entity; // JPA annotation to indicate this class is an entity and should be mapped to a database table
import jakarta.persistence.GeneratedValue; // JPA annotation to specify the generation strategy for primary keys
import jakarta.persistence.GenerationType; // JPA enum for specifying different generation strategies for primary keys
import jakarta.persistence.Id; // JPA annotation to specify the primary key of an entity
import lombok.Getter; // Lombok annotation to automatically generate getter methods for all fields
import lombok.NoArgsConstructor; // Lombok annotation to automatically generate a no-argument constructor
import lombok.Setter; // Lombok annotation to automatically generate setter methods for all fields

@Getter // Lombok annotation to generate getters for all fields
@Setter // Lombok annotation to generate setters for all fields
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor
@Entity // Specifies that this class is an entity and will be mapped to a database table
public class Role {
	@Id // Specifies the primary key of an entity
	private Integer id; // Field to store the unique identifier for each role
	
	private String name; // Field to store the name of the role
}
