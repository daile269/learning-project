package com.learning.core_service.controller;

import com.learning.core_service.entity.Author;
import com.learning.core_service.entity.Post;
import com.learning.core_service.handler.PostHandler;
import com.learning.core_service.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(PostController.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostHandler postHandler;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetPostById() throws Exception {
        Author author = new Author(1L,"Author test","author1@gmail.com","Author",null);
        Post post = new Post(1L,"Post test","This is post test",author,1L,null);
        when(postHandler.getPostById(1L)).thenReturn(post);
        mockMvc.perform(get("/api/posts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Post test"));

    }
}
