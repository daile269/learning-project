package com.learning.core_service.mapper;

import com.learning.core_service.dto.AuthorDTO;
import com.learning.core_service.dto.PostDTO;
import com.learning.core_service.entity.Author;
import com.learning.core_service.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "postList", source = "posts")
    AuthorDTO toDTO(Author author);

    Author toEntity(AuthorDTO authorDTO);

}
