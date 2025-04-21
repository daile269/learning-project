package com.learning.core_service.handler;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Post;
import com.learning.core_service.exception.ResourceNotFoundException;
import com.learning.core_service.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostHandler {
    private final PostService postService;

    public List<Post> getALlPosts() {
        return postService.getPosts();
    }

    public Post getPostById(Long id) {
        try {

            return postService.findPostById(id);
        }catch (ResourceNotFoundException ex){

            throw new ResourceNotFoundException("Post not found");
        }

    }

    public Post createPost(@Valid Post post) {
        return postService.createPost(post);
    }

    public Post updatePost(@Valid Post post, Long id) {
        return postService.updatePost(post,id);
    }
    public void deletePost(Long id){
        postService.deletePost(id);
    }
}
