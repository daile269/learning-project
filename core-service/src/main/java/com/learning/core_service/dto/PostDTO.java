package com.learning.core_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String title;
    private String content;
    private Long authorId;
    private LocalDateTime createTime;
    private String imageUrl;
}
