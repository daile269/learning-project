package com.learning.user_service.controller;

import com.learning.user_service.dto.UserDTO;
import com.learning.user_service.dto.response.ApiResponse;
import com.learning.user_service.handler.UserHandler;
import com.learning.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserHandler userHandler;

    @PostMapping
    public ApiResponse<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        return ApiResponse.<UserDTO>builder()
                .code(200)
                .result(userHandler.createUser(userDTO))
                .message("Thêm mới thành công User")
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id){
        return ApiResponse.<UserDTO>builder()
                .code(200)
                .result(userHandler.updateUser(userDTO,id))
                .message("Cập nhật thành công User")
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
                .message("Thông tin User")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @GetMapping("/username/{username}")
    public ApiResponse<UserDTO> getUserByUsername(@PathVariable String username){
        return ApiResponse.<UserDTO>builder()
                .code(200)
                .message("Thành công")
                .result(userHandler.getUserByUsername(username))
                .build();
    }
}
