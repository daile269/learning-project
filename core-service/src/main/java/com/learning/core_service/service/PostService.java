package com.learning.core_service.service;

import com.learning.core_service.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostDTO findPostById(Long id);

    PostDTO createPost(PostDTO postDTO);

    PostDTO updatePost(PostDTO postDTO,Long id);

    List<PostDTO> getPosts();

    void deletePost(Long id);

    PostDTO updateImage(Long id, MultipartFile image) throws IOException;
}
