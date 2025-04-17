package com.learing.auth_service.service;

import com.learing.auth_service.entity.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);

    User createUser(User user);

    User updateUser(User user,Long id);

    List<User> getUser();

    void deleteUser(Long id);
}
