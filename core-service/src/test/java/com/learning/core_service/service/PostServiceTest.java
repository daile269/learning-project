package com.learning.core_service.service;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Author;
import com.learning.core_service.entity.Post;
import com.learning.core_service.exception.ResourceNotFoundException;
import com.learning.core_service.repository.PostRepository;
import com.learning.core_service.service.impl.IPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private IPostService postService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostById(){
        Author author = new Author(1L,"Author test","author1@gmail.com","Author",null);
        Post post = new Post(1L,"Post test","This is post test",author,1L,null);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        PostDTO result = postService.findPostById(1L);
        assertNotNull(result);
        assertEquals("Post test", result.getTitle());

    }
    @Test
    public void testGetPostNotFound(){
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,() ->
                postService.findPostById(1L));
    }
}
