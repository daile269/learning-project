package com.learning.user_service.controller;

import com.learning.user_service.dto.UserDTO;
import com.learning.user_service.entity.User;
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

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.ok("Thêm mới thành công");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user, @PathVariable Long id){
        return ResponseEntity.ok("Cập nhật thành công");
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
}
