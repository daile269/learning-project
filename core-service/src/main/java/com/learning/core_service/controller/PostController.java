package com.learning.core_service.controller;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.dto.response.ApiResponse;
import com.learning.core_service.handler.PostHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                .message("Post Information")
                .build();
    }
    @PostMapping
    public ApiResponse<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return ApiResponse.<PostDTO>builder()
                .code(200)
                .result(postHandler.createPost(postDTO))
                .message("New post added successfully")
                .build();
    }
    @PutMapping("/{id}")
    public ApiResponse<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO,@PathVariable Long id){
        return ApiResponse.<PostDTO>builder()
                .code(200)
                .result(postHandler.updatePost(postDTO,id))
                .message("Update success")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        postHandler.deletePost(id);
        return ResponseEntity.ok("Delete success");
    }

    @PatchMapping("/upload-image/{id}")
    public ApiResponse<PostDTO> uploadImage(@PathVariable Long id
            ,@RequestParam("image") MultipartFile image) throws IOException {
        postHandler.updateImage(id, image);
        return ApiResponse.<PostDTO>builder()
                .code(200)
                .result(postHandler.updateImage(id,image))
                .message("Update image success")
                .build();
    }


}
