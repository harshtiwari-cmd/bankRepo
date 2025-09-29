package com.example.card.services.impl;

import com.example.card.constrants.dto.UserRequestDTO;
import com.example.card.constrants.dto.UserResponseDTO;
import com.example.card.constrants.entity.User;
import com.example.card.constrants.mapper.UserMapper;
import com.example.card.repository.UserRepository;
import com.example.card.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO saveUser(UserRequestDTO user) {
        User entity = userMapper.toEntity(user);
        User save = userRepository.save(entity);
        return userMapper.toDto(save);
    }
}
