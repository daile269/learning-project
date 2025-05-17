package com.learning.core_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO implements Serializable {

    private Long id;

    @NotBlank(message = "{post.title.notBlank}")
    private String title;

    @NotBlank(message = "{post.content.notBlank}")
    private String content;

    @NotNull(message = "{javax.validation.constraints.NotBlank.message}")
    private Long authorId;

    private LocalDateTime createTime;
    private String imageUrl;
    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String updatedBy;
}
