package com.learning.core_service.mapper;

import com.learning.core_service.dto.AuthorDTO;
import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Author;
import com.learning.core_service.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface AuthorMapper {
    @Mapping(target = "postList", source = "posts")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "updatedBy", source = "updatedBy")
    AuthorDTO toDTO(Author author);

    Author toEntity(AuthorDTO authorDTO);

}
