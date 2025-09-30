package com.example.card.constrants.mapper;

import com.example.card.domain.dto.UserRequestDTO;
import com.example.card.domain.dto.UserResponseDTO;
import com.example.card.constrants.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO requestDTO);
    UserResponseDTO toDto(User user);
}
