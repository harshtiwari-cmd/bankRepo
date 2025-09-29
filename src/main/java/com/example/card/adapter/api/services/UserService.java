package com.example.card.adapter.api.services;

import com.example.card.domain.dto.UserRequestDTO;
import com.example.card.domain.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO saveUser(UserRequestDTO user);
}
