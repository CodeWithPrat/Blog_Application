package com.blog.entities;

// Importing necessary Jakarta Persistence API and Lombok annotations
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marks this class as a JPA entity (a class that represents a table in a database)
@Entity
// Lombok annotations to automatically generate setters, getters, and a no-argument constructor
@Setter
@Getter
@NoArgsConstructor
// Specifies the name of the database table for this entity
@Table(name = "Comments")
public class Comment {

    // Marks this field as the primary key
    @Id
    // Specifies that the ID should be automatically generated
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Represents the content of the comment
    private String content;

    // Defines a many-to-one relationship with the Post entity
    @ManyToOne
    private Post post;
}