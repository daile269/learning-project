package com.learning.core_service.controller;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.dto.response.ApiResponse;
import com.learning.core_service.entity.Post;
import com.learning.core_service.handler.PostHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostHandler postHandler;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getALlProducts(){
        List<PostDTO> postList = postHandler.getALlPosts();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDTO> getPostById(@PathVariable Long id){
        return ApiResponse.<PostDTO>builder()
                .code(200)
                .result(postHandler.getPostById(id))
                .message("Thông tin Post")
                .build();
    }
    @PostMapping
    public ApiResponse<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return ApiResponse.<PostDTO>builder()
                .code(200)
                .result(postHandler.createPost(postDTO))
                .message("Thêm thành công")
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO,@PathVariable Long id){
        return ApiResponse.<PostDTO>builder()
                .code(200)
                .result(postHandler.updatePost(postDTO,id))
                .message("Cập nhật thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        postHandler.deletePost(id);
        return ResponseEntity.ok("Xóa thành công");
    }

}
