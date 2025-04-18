package com.learning.user_service.service;

import com.learning.user_service.dto.UserDTO;
import com.learning.user_service.entity.User;

import java.util.List;

public interface UserService {
    UserDTO findUserById(Long id);

    UserDTO createUser(User user);

    UserDTO updateUser(User user,Long id);

    List<UserDTO> getUsers();

    void deleteUser(Long id);

    User getUserByUsername(String username);
}
