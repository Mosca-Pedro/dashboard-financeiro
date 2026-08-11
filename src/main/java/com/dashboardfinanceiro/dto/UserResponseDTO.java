package com.dashboardfinanceiro.dto;

import com.dashboardfinanceiro.entity.User;

import java.time.LocalDateTime;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String baseCurrency;
    private LocalDateTime createdAt;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.baseCurrency = user.getBaseCurrency();
        this.createdAt = user.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}