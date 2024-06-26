package com.blog.entities;

// Importing necessary Java and framework classes
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marks this class as a JPA entity (a class that represents a table in a database)
@Entity
// Specifies the name of the database table for this entity
@Table(name = "categories")
// Lombok annotations to automatically generate constructor, getters, and setters
@NoArgsConstructor
@Getter
@Setter
public class Category {

    // Marks this field as the primary key
    @Id
    // Specifies that the ID should be automatically generated
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    
    // Specifies column details in the database
    @Column(name = "title", length = 100, nullable = false)
    
    // Specifies the property name when serializing to JSON
    //@JsonProperty: Specifies the property name to use when this object is serialized to JSON.
    @JsonProperty("title")
    private String categoryTitle;
    
    @Column(name = "description")
    @JsonProperty("description")
    private String categoryDescription;
    
    // Defines a one-to-many relationship with Post entity
//    @OneToMany: Defines a one-to-many relationship with the Post entity:
//    	mappedBy = "category": Indicates that the Post entity has a field named category that owns this relationship.
//    	cascade = CascadeType.ALL: Specifies that all operations (persist, remove, refresh, merge, detach) should be cascaded to the associated Post entities.
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
}