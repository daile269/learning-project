package com.learning.core_service.controller;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Author;
import com.learning.core_service.entity.Post;
import com.learning.core_service.handler.PostHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostHandler postHandler;

    @GetMapping
    public ResponseEntity<List<Post>> getALlProducts(){
        List<Post> postList = postHandler.getALlPosts();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postHandler.getPostById(id));
    }
    @PostMapping
    public ResponseEntity<String> createPost(@Valid @RequestBody Post post){
        postHandler.createPost(post);
        return ResponseEntity.ok("Tạo thành công");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@Valid @RequestBody Post post,@PathVariable Long id){
        postHandler.updatePost(post,id);
        return ResponseEntity.ok("Tạo thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        postHandler.deletePost(id);
        return ResponseEntity.ok("Xóa thành công");
    }

}
