package com.learing.auth_service.service.impl;


import com.learing.auth_service.entity.User;
import com.learing.auth_service.exception.ResourceNotFoundException;
import com.learing.auth_service.repository.UserRepository;
import com.learing.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Id:" + id) );
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Long id) {
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
