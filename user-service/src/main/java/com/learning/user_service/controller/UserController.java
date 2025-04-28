package com.learning.user_service.controller;

import com.learning.user_service.dto.UserDTO;
import com.learning.user_service.dto.response.ApiResponse;
import com.learning.user_service.handler.UserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserHandler userHandler;

    @PostMapping
    public ApiResponse<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){

        return ApiResponse.<UserDTO>builder()
                .code(200)
                .result(userHandler.createUser(userDTO))
                .message("New user added successfully")
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id){
        return ApiResponse.<UserDTO>builder()
                .code(200)
                .result(userHandler.updateUser(userDTO,id))
                .message("User information updated successfully")
                .build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userHandler.getALlUsers());
    }
    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable Long id) {
        return ApiResponse.<UserDTO>builder()
                .code(200)
                .result(userHandler.getUserById(id))
                .message("User information")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userHandler.deleteUser(id);
        return ResponseEntity.ok("Delete success");
    }

    @GetMapping("/username/{username}")
    public ApiResponse<UserDTO> getUserByUsername(@PathVariable String username){
        return ApiResponse.<UserDTO>builder()
                .code(200)
                .message("Success")
                .result(userHandler.getUserByUsername(username))
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO){
        userHandler.register(userDTO);
        return ResponseEntity.ok("Register Success");
    }
}
