package com.dashboardfinanceiro.controller;

import com.dashboardfinanceiro.config.SecurityUtils;
import com.dashboardfinanceiro.dto.PortfolioSummaryDTO;
import com.dashboardfinanceiro.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/api/portfolio/summary")
    public ResponseEntity<PortfolioSummaryDTO> getSummary() {
        Long userId = SecurityUtils.getAuthenticatedUserId();
        PortfolioSummaryDTO summary = portfolioService.getSummary(userId);
        return ResponseEntity.ok(summary);
    }
}