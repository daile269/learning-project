package com.learning.user_service.handler;

import com.learning.user_service.dto.UserDTO;
import com.learning.user_service.exception.ResourceNotFoundException;
import com.learning.user_service.service.impl.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHandler {
    private final IUserService userService;

    public List<UserDTO> getALlUsers() {
        return userService.getUsers();
    }

    public UserDTO getUserById(Long id) {
        try {

            return userService.findUserById(id);
        }catch (ResourceNotFoundException ex){

            throw new ResourceNotFoundException("User not found");
        }

    }

    public UserDTO createUser(UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    public UserDTO updateUser(UserDTO userDTO, Long id) {
        return userService.updateUser(userDTO,id);
    }
    public void deleteUser(Long id){
        userService.deleteUser(id);
    }
    public UserDTO getUserByUsername(String username){
        return userService.getUserByUsername(username);
    }
}
