package com.learning.user_service.mapper;

import com.learning.user_service.dto.UserDTO;
import com.learning.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDTO toDTO(User user);


    User toEntity(UserDTO userDTO);
}
