package com.learning.core_service.controller;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.dto.response.ApiResponse;
import com.learning.core_service.handler.PostHandler;
import com.learning.core_service.temporal.postWorkflow.PostDeletionActivities;
import com.learning.core_service.temporal.postWorkflow.PostDeletionWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostHandler postHandler;

    private final WorkflowClient workflowClient;

    private final PostDeletionActivities postDeletionActivities;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getALlPost(){
        List<PostDTO> postList = postHandler.getALlPosts();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDTO> getPostById(@PathVariable Long id){
        System.out.println("13");
        return ApiResponse.<PostDTO>builder()
                .code(200)
                .result(postHandler.getPostById(id))
                .message("Post Information")
                .build();
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return ApiResponse.<PostDTO>builder()
                .code(200)
                .result(postHandler.createPost(postDTO))
                .message("New post added successfully")
                .build();
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO,@PathVariable Long id){
        return ApiResponse.<PostDTO>builder()
                .code(200)
                .result(postHandler.updatePost(postDTO,id))
                .message("Update success")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        postHandler.getPostById(id);
        PostDeletionWorkflow workflow = workflowClient.newWorkflowStub(
                PostDeletionWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("core-service-task-queue")
                        .setWorkflowId("post-deletion-workflow-"+id)
                        .setWorkflowRunTimeout(Duration.ofMinutes(5))
                        .build()
        );

        WorkflowClient.start(workflow::scheduleDeletion, id);
        return ResponseEntity.ok("Post deletion scheduled successfully for Post ID: " + id);
    }
    @PatchMapping("/{postId}/restore")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> restorePost(@PathVariable Long postId) {
        postDeletionActivities.restorePost(postId);
        return ResponseEntity.ok("Post " + postId + " has been restored.");
    }

    @PatchMapping("/upload-image/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<PostDTO> uploadImage(@PathVariable Long id
            ,@RequestParam("image") MultipartFile image) throws IOException {
        byte[] imageBytes = image.getBytes();
        String fileName = image.getOriginalFilename();
        String contentType = image.getContentType();
        return ApiResponse.<PostDTO>builder()
                .code(200)
                .result(postHandler.updateImage(id,imageBytes,fileName,contentType))
                .message("Update image success")
                .build();
    }


}
