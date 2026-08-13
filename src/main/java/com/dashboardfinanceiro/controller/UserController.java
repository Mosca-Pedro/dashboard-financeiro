package com.dashboardfinanceiro.controller;

import com.dashboardfinanceiro.dto.UserRegisterDTO;
import com.dashboardfinanceiro.dto.UserResponseDTO;
import com.dashboardfinanceiro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterDTO dto) {
        UserResponseDTO response = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}