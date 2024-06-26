package com.blog.entities;

// Importing necessary Java and Jakarta Persistence API classes
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Marks this class as a JPA entity (a class that represents a table in a database)
@Entity
// Lombok annotations to automatically generate constructor, getters, and setters
@NoArgsConstructor
@Getter
@Setter
// Specifies the name of the database table for this entity
@Table(name = "posts")
public class Post {

    // Marks this field as the primary key
    @Id
    // Specifies that the ID should be automatically generated
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    
    // Specifies column details in the database
    @Column(name = "post_title", length = 100, nullable = false)
    private String title;
    
    @Column(name = "post_content", length = 1000)
    private String content;
    
    private String imageName;
    
    private Date addDate;
    
    // Defines a many-to-one relationship with Category entity
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    // Defines a many-to-one relationship with User entity
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    // Defines a one-to-many relationship with Comment entity
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}