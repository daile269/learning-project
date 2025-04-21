package com.learning.core_service.handler;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Post;
import com.learning.core_service.exception.ResourceNotFoundException;
import com.learning.core_service.service.PostService;
import com.learning.core_service.service.impl.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostHandler {
    private final IPostService postService;

    public List<PostDTO> getALlPosts() {
        return postService.getPosts();
    }

    public PostDTO getPostById(Long id) {
        try {

            return postService.findPostById(id);
        }catch (ResourceNotFoundException ex){

            throw new ResourceNotFoundException("Post not found");
        }

    }

    public PostDTO createPost(PostDTO postDTO) {
        return postService.createPost(postDTO);
    }

    public PostDTO updatePost(PostDTO postDTO, Long id) {
        return postService.updatePost(postDTO,id);
    }
    public void deletePost(Long id){
        postService.deletePost(id);
    }
}
