package com.blog.services;

import java.util.List;
import com.blog.payload.UserDTO;

public interface UserService {
	
	UserDTO registerNewUser(UserDTO user);
	
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(UserDTO user, Integer userId);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllUser();
    void deleteUser(Integer userId);
}
