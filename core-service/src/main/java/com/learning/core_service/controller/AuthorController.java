package com.learning.core_service.controller;

import com.learning.core_service.dto.AuthorDTO;
import com.learning.core_service.dto.response.ApiResponse;
import com.learning.core_service.handler.AuthorHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorHandler authorHandler;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getALlProducts(){
        List<AuthorDTO> AuthorList = authorHandler.getALlAuthors();
        return ResponseEntity.ok(AuthorList);
    }

    @GetMapping("/{id}")
    public ApiResponse<AuthorDTO> getAuthorById(@PathVariable Long id){
        return ApiResponse.<AuthorDTO>builder()
                .code(200)
                .result(authorHandler.getAuthorById(id))
                .message("Thông tin Author")
                .build();
    }
    @PostMapping
    public ApiResponse<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO AuthorDTO){
        return ApiResponse.<AuthorDTO>builder()
                .code(200)
                .result(authorHandler.createAuthor(AuthorDTO))
                .message("Thêm thành công")
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<AuthorDTO> updateAuthor(@Valid @RequestBody AuthorDTO AuthorDTO,@PathVariable Long id){
        return ApiResponse.<AuthorDTO>builder()
                .code(200)
                .result(authorHandler.updateAuthor(AuthorDTO,id))
                .message("Cập nhật thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id){
        authorHandler.deleteAuthor(id);
        return ResponseEntity.ok("Xóa thành công");
    }

}
