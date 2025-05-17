package com.learning.service;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Author;
import com.learning.core_service.entity.Post;
import com.learning.core_service.exception.ResourceNotFoundException;
import com.learning.core_service.mapper.PostMapper;
import com.learning.core_service.repository.AuthorRepository;
import com.learning.core_service.repository.PostRepository;
import com.learning.core_service.service.impl.PostServiceImpl;
import com.learning.core_service.untils.S3Service;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private S3Service s3Service;

    @Mock
    private MeterRegistry meterRegistry;

    @Mock
    private Counter createCounter;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(meterRegistry.counter("post.create.count")).thenReturn(createCounter);
        postService = new PostServiceImpl(postRepository, authorRepository, postMapper, s3Service, meterRegistry);
    }

    @Test
    public void testCreatePost_Success() {

        Post mappedPost = new Post();
        mappedPost.setTitle("New Post");
        mappedPost.setContent("New post content");

        when(postMapper.toEntity(any(PostDTO.class))).thenReturn(mappedPost); // PostDTO =-> post

        PostDTO mappedDTO = new PostDTO();
        mappedDTO.setTitle("New Post");
        mappedDTO.setContent("New post content");
        mappedDTO.setAuthorId(1L);

        when(postMapper.toDTO(any(Post.class))).thenReturn(mappedDTO);
        // Arrange
        Author author = new Author();
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("New Post");
        postDTO.setContent("New post content");
        postDTO.setAuthorId(1L);

        Post post = new Post();
        post.setTitle("New Post");
        post.setContent("New post content");

        Post savedPost = new Post();
        savedPost.setTitle("New Post");
        savedPost.setContent("New post content");

        // Mock behavior
        when(authorRepository.findById(postDTO.getAuthorId())).thenReturn(Optional.of(author));
        when(postMapper.toEntity(postDTO)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);


        // Act
        PostDTO result = postService.createPost(postDTO);

        // Assert
        assertNotNull(result);
        assertEquals("New Post", result.getTitle());
        assertEquals("New post content", result.getContent());

        verify(createCounter, times(1)).increment();
    }
    @Test
    public void testUpdatePost() {
        // Arrange
        Long postId = 1L;
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Updated Post");
        postDTO.setContent("Updated content");
        postDTO.setAuthorId(1L);  // Ensure the authorId is set correctly

        Post existingPost = new Post();
        existingPost.setId(postId);
        existingPost.setTitle("Old Post");
        existingPost.setContent("Old content");

        Author author = new Author();
        author.setId(1L);
        author.setName("John Doe");

        // Mock behavior for postRepository and authorRepository
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postMapper.toEntity(postDTO)).thenReturn(existingPost);
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(author));
        when(postRepository.save(existingPost)).thenReturn(existingPost);
        when(postMapper.toDTO(existingPost)).thenReturn(postDTO);

        // Act
        PostDTO result = postService.updatePost(postDTO, postId);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Post", result.getTitle());
        assertEquals("Updated content", result.getContent());
        verify(postRepository, times(1)).save(existingPost);
    }


    @Test
    public void testUpdateImage() throws IOException {
        // Arrange
        Long postId = 1L;
        byte[] imageBytes = new byte[]{1, 2, 3};
        String originalFilename = "image.jpg";
        String contentType = "image/jpeg";
        String imageUrl = "https://s3.com/image.jpg";

        Post post = new Post();
        post.setId(postId);
        post.setTitle("Post with image");

        PostDTO postDTO = new PostDTO();
        postDTO.setImageUrl(imageUrl);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(s3Service.uploadFile(imageBytes, originalFilename, contentType)).thenReturn(imageUrl);
        when(postRepository.save(post)).thenReturn(post);
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        // Act
        PostDTO result = postService.updateImage(postId, imageBytes, originalFilename, contentType);

        // Assert
        assertNotNull(result);
        assertEquals(imageUrl, result.getImageUrl());
        verify(postRepository, times(1)).save(post);
    }


    @Test
    public void testFindPostById_ExistingPost() {
        // Arrange
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Existing Post");
        post.setContent("Content of existing post");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Existing Post");
        postDTO.setContent("Content of existing post");
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        // Act
        PostDTO result = postService.findPostById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Existing Post", result.getTitle());
        assertEquals("Content of existing post", result.getContent());
    }

    @Test
    public void testFindPostById_NotFound() {
        // Arrange
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> postService.findPostById(1L));
    }
}
