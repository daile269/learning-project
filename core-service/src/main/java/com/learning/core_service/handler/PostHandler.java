package com.learning.core_service.handler;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.exception.AppException;
import com.learning.core_service.exception.ErrorCode;
import com.learning.core_service.exception.ResourceNotFoundException;
import com.learning.core_service.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostHandler {

    @DubboReference
    private final PostService postService;

    public List<PostDTO> getALlPosts() {
        return postService.getPosts();
    }

    public PostDTO getPostById(Long id) {
        try {

            return postService.findPostById(id);
        }catch (ResourceNotFoundException ex){

            throw new AppException(ErrorCode.POST_NOT_FOUND);
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

    public PostDTO updateImage(Long id, byte[] imageBytes, String originalFilename, String contentType) throws IOException {
        return postService.updateImage(id,imageBytes,originalFilename,contentType);
    }

}
