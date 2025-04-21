package com.learning.core_service.service;

import com.learning.core_service.entity.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);

    User createUser(User user);

    User updateUser(User user,Long id);

    List<User> getUser();

    void deleteUser(Long id);
}
