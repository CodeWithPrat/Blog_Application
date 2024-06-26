// This package contains configuration-related classes for the blog application
package com.blog.config;

// Importing necessary Spring Framework classes
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// This annotation tells Spring that this class is a configuration class
@Configuration
public class PasswordEncoderConfig {

    // This annotation tells Spring to create and manage a PasswordEncoder object
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Create and return a new BCryptPasswordEncoder object
        return new BCryptPasswordEncoder();
    }
}

/*
  The @Configuration annotation marks this class as a source of bean definitions for the Spring application context. 
  This means Spring will use this class to set up some parts of the application.
  
  BCryptPasswordEncoder is a specific implementation of the PasswordEncoder interface. It uses the BCrypt strong hashing function to encode passwords.
  
  By defining this bean, we're telling Spring to use BCrypt for password encoding throughout our application. Whenever a part of the application needs a PasswordEncoder, 
  Spring will provide this BCryptPasswordEncoder.
*/
 