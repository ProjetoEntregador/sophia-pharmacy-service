package com.sophia.sophia_pharmacy_service.services;

import com.sophia.sophia_pharmacy_service.auth.JwtUtil;
import com.sophia.sophia_pharmacy_service.dtos.auth.LoginDto;
import com.sophia.sophia_pharmacy_service.dtos.auth.UserDto;
import com.sophia.sophia_pharmacy_service.dtos.mappers.UserMapper;
import com.sophia.sophia_pharmacy_service.entities.User;
import com.sophia.sophia_pharmacy_service.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil JwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void register(UserDto dto) {

        userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Email já cadastrado");
        });

        userRepository.save(userMapper.toEntity(dto));
    }

    public String login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        return JwtUtil.generateToken(user);
    }
}