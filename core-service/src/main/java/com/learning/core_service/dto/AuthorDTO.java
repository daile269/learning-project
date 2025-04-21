package com.learning.core_service.dto;

import com.learning.core_service.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private String name;
    private String email;
    private String description;
    private List<Post> postList;
}
