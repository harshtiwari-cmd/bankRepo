package com.example.card.services;

import com.example.card.constrants.dto.UserRequestDTO;
import com.example.card.constrants.dto.UserResponseDTO;
import com.example.card.constrants.entity.User;

public interface UserService {

    UserResponseDTO saveUser(UserRequestDTO user);
}
