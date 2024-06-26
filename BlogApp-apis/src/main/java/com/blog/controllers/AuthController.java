package com.blog.controllers;

// Importing necessary Spring and application-specific classes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.exceptions.ApiException;
import com.blog.payload.JwtAuthRequest;
import com.blog.payload.JwtAuthResponse;
import com.blog.payload.UserDTO;
import com.blog.security.JwtTokenHelper;
import com.blog.services.UserService;

// Marks this class as a REST controller
@RestController
// Base URL for all end-points in this controller
@RequestMapping("/api/v1/auth/")
public class AuthController {
    
    // Auto-wiring (injecting) required services and components
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    // End-point for user login
    
	//The createToken method (/login end-point):
	//Receives login credentials in the request body.
	//Authenticates the user.
	//Generates a JWT token for the authenticated user.
	//Returns the token in the response.
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request
            ) throws Exception {
        // Authenticate the user
        this.authenticate(request.getUsername(), request.getPassword());
        
        // Load user details
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        
        // Generate JWT token
        String token = this.jwtTokenHelper.generateToken(userDetails);
        
        // Create and return the response with the token
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    // Helper method to authenticate user
    
//    The authenticate method:
//    	Attempts to authenticate the user with the provided user-name and password.
//    	Throws an exception if authentication fails.
    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e) {
            System.out.println("Invalid details!!");
            throw new ApiException("Invalid username or password");
        }
    }
    
    // End-point for registering a new user
    
//    The registerUser method (/register end-point):
//    	Receives user details in the request body.
//    	Registers a new user using the user service.
//    	Returns the created user details.
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO registerNewUser = this.userService.registerNewUser(userDTO);
        return new ResponseEntity<UserDTO>(registerNewUser, HttpStatus.CREATED);
    }
}