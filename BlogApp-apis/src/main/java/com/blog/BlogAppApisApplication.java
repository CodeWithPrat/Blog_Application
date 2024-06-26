// This package contains all the classes related to the blog application
package com.blog;

// Importing necessary Java and Spring libraries
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

// This annotation marks this class as a Spring Boot application
@SpringBootApplication
// This annotation provides API documentation using Swagger
@OpenAPIDefinition(info = @Info(title = "Blog API", version = "1.0", description = "API documentation for Blog application"))
// This class is the main entry point of the application and implements CommandLineRunner to run some code at startup
public class BlogAppApisApplication implements CommandLineRunner {

    // Automatically inject (provide) a PasswordEncoder object
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // Automatically inject (provide) a RoleRepo object
    @Autowired
    private RoleRepo roleRepo;

    // The main method that starts the Spring application
    public static void main(String[] args) {
        SpringApplication.run(BlogAppApisApplication.class, args);
    }

    // This method creates and returns a ModelMapper object, which is used to map between different object types
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // This method runs automatically when the application starts
    @Override
    public void run(String... args) throws Exception {
        // Print an encoded version of the password "Aksh@12345"
        System.out.println(this.passwordEncoder.encode("Aksh@12345"));

        try {
            // Create an admin role
            Role role = new Role();
            role.setId(AppConstants.ADMIN_USER);
            role.setName("ADMIN_USER");
            
            // Create a normal user role
            Role role1 = new Role();
            role1.setId(AppConstants.NORMAL_USER);
            role1.setName("NORMAL_USER");
            
            // Create a list of roles
            List<Role> roles = List.of(role, role1);
            
            // Save all roles to the database
            List<Role> result = this.roleRepo.saveAll(roles);
            
            // Print the name of each saved role
            result.forEach(r -> {
                System.out.println(r.getName());
            });
        }
        catch (Exception e) {
            // If an error occurs, print the error details
            e.printStackTrace();
        }
    }
}