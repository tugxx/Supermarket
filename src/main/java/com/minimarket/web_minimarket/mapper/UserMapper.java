package com.minimarket.web_minimarket.mapper;

import com.minimarket.web_minimarket.dto.UserRequestDTO;
import com.minimarket.web_minimarket.dto.UserResponseDTO;
import com.minimarket.web_minimarket.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "userEmail", source = "userEmail")
    @Mapping(target = "role", source = "userRole")
    UserResponseDTO userToUserResponse(User user);

    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "userEmail", source = "userEmail")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "userRole", source = "role")
    User userRequestToUser(UserRequestDTO userDTO);
}
