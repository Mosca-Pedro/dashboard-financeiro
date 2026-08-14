package com.dashboardfinanceiro.controller;

import com.dashboardfinanceiro.config.SecurityUtils;
import com.dashboardfinanceiro.dto.AIInsightResponseDTO;
import com.dashboardfinanceiro.service.AIInsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AIInsightController {

    private final AIInsightService aiInsightService;

    @Autowired
    public AIInsightController(AIInsightService aiInsightService) {
        this.aiInsightService = aiInsightService;
    }

    @GetMapping("/api/insights")
    public ResponseEntity<List<AIInsightResponseDTO>> list() {
        Long userId = SecurityUtils.getAuthenticatedUserId();
        List<AIInsightResponseDTO> insights = aiInsightService.listByUser(userId);
        return ResponseEntity.ok(insights);
    }

    @PatchMapping("/api/insights/{insightId}/read")
    public ResponseEntity<AIInsightResponseDTO> markAsRead(@PathVariable Long insightId) {
        AIInsightResponseDTO response = aiInsightService.markAsRead(insightId);
        return ResponseEntity.ok(response);
    }
}