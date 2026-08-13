package com.dashboardfinanceiro.service;

import com.dashboardfinanceiro.dto.UserRegisterDTO;
import com.dashboardfinanceiro.dto.UserResponseDTO;
import com.dashboardfinanceiro.entity.User;
import com.dashboardfinanceiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO register(UserRegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setBaseCurrency(dto.getBaseCurrency());

        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser);
    }
}