package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.UserDTO;
import com.blog.repositories.RoleRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepo roleRepo;

    // Create a new user
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = dtoToUser(userDTO); // Map UserDTO to User entity
        User savedUser = userRepo.save(user); // Save user in the repository
        return userToDTO(savedUser); // Map saved User entity back to UserDTO
    }

    // Update an existing user
    @Override
    public UserDTO updateUser(UserDTO userDto, Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)); // Find user by Id or throw exception if not found
        user.setName(userDto.getName()); // Update user details
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser = userRepo.save(user); // Save updated user
        return userToDTO(updatedUser); // Map updated User entity to UserDTO
    }

    // Get user by Id
    @Override
    public UserDTO getUserById(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)); // Find user by Id or throw exception if not found
        return userToDTO(user); // Map User entity to UserDTO
    }

    // Get all users
    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepo.findAll(); // Retrieve all users
        return users.stream().map(this::userToDTO).collect(Collectors.toList()); // Map each User entity to UserDTO and collect into a list
    }

    // Delete a user by Id
    @Override
    public void deleteUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)); // Find user by Id or throw exception if not found
        userRepo.delete(user); // Delete user
    }

    // Map UserDTO to User entity
    private User dtoToUser(UserDTO userDto) {
        return modelMapper.map(userDto, User.class);
    }

    // Map User entity to UserDTO
    private UserDTO userToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    // Register a new user with encoded password and default role
    @Override
    public UserDTO registerNewUser(UserDTO userDto) {
        User user = modelMapper.map(userDto, User.class); // Map UserDTO to User entity
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode the password
        Role role = roleRepo.findById(AppConstants.NORMAL_USER).orElseThrow(); // Retrieve default role or throw exception if not found
        user.getRoles().add(role); // Assign default role to user
        User savedUser = userRepo.save(user); // Save user in the repository
        return modelMapper.map(savedUser, UserDTO.class); // Map saved User entity back to UserDTO
    }
}
