package com.learning.core_service.service;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Post;

import java.util.List;

public interface PostService {
    Post findPostById(Long id);

    Post createPost(Post post);

    Post updatePost(Post post,Long id);

    List<Post> getPosts();

    void deletePost(Long id);
}
