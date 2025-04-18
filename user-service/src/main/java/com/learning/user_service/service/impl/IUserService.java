package com.learning.user_service.service.impl;


import com.learning.user_service.dto.UserDTO;
import com.learning.user_service.entity.User;
import com.learning.user_service.exception.AppException;
import com.learning.user_service.exception.ErrorCode;
import com.learning.user_service.mapper.UserMapper;
import com.learning.user_service.repository.UserRepository;
import com.learning.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Override
    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTS) );
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO createUser(User user) {
        checkUserExist(user);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(User user, Long id) {
        userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTS));
        user.setId(id);
        user.setPassword(encoder.encode(user.getPassword()));
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> userlist= userRepository.findAll();
        return userlist.stream().map(user -> userMapper.toDTO(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void checkUserExist(User user){
        User checkUser = userRepository.findByUsername(user.getUsername());
        if(checkUser!=null) throw new AppException(ErrorCode.USER_IS_EXISTS);
    }
}
