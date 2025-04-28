package com.learning.core_service.mapper;

import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "createTime", source = "createTime")
    @Mapping(target = "imageUrl", source = "imageUrl")
    PostDTO toDTO(Post post);

    Post toEntity(PostDTO postDTO);

}
