package com.dashboardfinanceiro.controller;

import com.dashboardfinanceiro.dto.ForgotPasswordRequestDTO;
import com.dashboardfinanceiro.dto.LoginRequestDTO;
import com.dashboardfinanceiro.dto.LoginResponseDTO;
import com.dashboardfinanceiro.dto.ResetPasswordRequestDTO;
import com.dashboardfinanceiro.entity.User;
import com.dashboardfinanceiro.service.JwtService;
import com.dashboardfinanceiro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        User user = userService.authenticate(dto.getEmail(), dto.getPassword());
        String token = jwtService.generateToken(user.getId(), user.getEmail());

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO dto) {
        userService.forgotPassword(dto.getEmail());
        return ResponseEntity.ok("Se o e-mail existir, um link de recuperação foi enviado.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO dto) {
        userService.resetPassword(dto.getToken(), dto.getNewPassword());
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}