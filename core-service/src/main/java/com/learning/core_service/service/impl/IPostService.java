package com.learning.core_service.service.impl;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Author;
import com.learning.core_service.entity.Post;
import com.learning.core_service.exception.ResourceNotFoundException;
import com.learning.core_service.repository.AuthorRepository;
import com.learning.core_service.repository.PostRepository;
import com.learning.core_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IPostService implements PostService {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post is not exits"));
    }

    @Override
    public Post createPost(Post post) {
        Author author =authorRepository.findById(post.getAuthorId()).orElseThrow(() ->
                new ResourceNotFoundException("Author is not exits"));
        post.setAuthor(author);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post, Long id) {
        post.setId(id);
        Author author =authorRepository.findById(post.getAuthorId()).orElseThrow(() ->
                new ResourceNotFoundException("Author is not exits"));
        post.setAuthor(author);
        return postRepository.save(post);
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
