package com.learning.core_service.controller;

import com.learning.core_service.dto.AuthorDTO;
import com.learning.core_service.dto.response.ApiResponse;
import com.learning.core_service.handler.AuthorHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorHandler authorHandler;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getALlAuthor(){
        List<AuthorDTO> AuthorList = authorHandler.getALlAuthors();
        return ResponseEntity.ok(AuthorList);
    }

    @GetMapping("/{id}")
    public ApiResponse<AuthorDTO> getAuthorById(@PathVariable Long id){
        return ApiResponse.<AuthorDTO>builder()
                .code(200)
                .result(authorHandler.getAuthorById(id))
                .message("Author Information")
                .build();
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO AuthorDTO){
        return ApiResponse.<AuthorDTO>builder()
                .code(200)
                .result(authorHandler.createAuthor(AuthorDTO))
                .message("New Author added successfully")
                .build();
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<AuthorDTO> updateAuthor(@Valid @RequestBody AuthorDTO AuthorDTO,@PathVariable Long id){
        return ApiResponse.<AuthorDTO>builder()
                .code(200)
                .result(authorHandler.updateAuthor(AuthorDTO,id))
                .message("Update success")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id){
        authorHandler.deleteAuthor(id);
        return ResponseEntity.ok("Delete Success");
    }

}
