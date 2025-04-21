package com.learning.core_service.service.impl;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Author;
import com.learning.core_service.entity.Post;
import com.learning.core_service.exception.AppException;
import com.learning.core_service.exception.ErrorCode;
import com.learning.core_service.exception.ResourceNotFoundException;
import com.learning.core_service.mapper.PostMapper;
import com.learning.core_service.repository.AuthorRepository;
import com.learning.core_service.repository.PostRepository;
import com.learning.core_service.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IPostService implements PostService {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    private final PostMapper postMapper;
    @Override
    public PostDTO findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post is not exits"));
        return postMapper.toDTO(post);
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post =postMapper.toEntity(postDTO);
        Author author =authorRepository.findById(postDTO.getAuthorId()).orElseThrow(() ->
                new ResourceNotFoundException("Author is not exits"));
        post.setAuthor(author);
        postRepository.save(post);
        return postMapper.toDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postMapper.toEntity(postDTO);
        post.setId(id);
        Post post1 = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        Author author =authorRepository.findById(postDTO.getAuthorId()).orElseThrow(() ->
                new ResourceNotFoundException("Author is not exits"));
        post.setAuthor(author);
        post.setCreateTime(post1.getCreateTime());
        postRepository.save(post);
        return postMapper.toDTO(post);
    }

    @Override
    public List<PostDTO> getPosts() {
        return postRepository.findAll().stream().map(post -> postMapper.toDTO(post)).collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long id) {
        postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        postRepository.deleteById(id);
    }
}
