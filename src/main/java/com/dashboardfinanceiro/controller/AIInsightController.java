package com.dashboardfinanceiro.controller;

import com.dashboardfinanceiro.config.SecurityUtils;
import com.dashboardfinanceiro.dto.AIInsightResponseDTO;
import com.dashboardfinanceiro.entity.AIInsight;
import com.dashboardfinanceiro.service.AIAnalystService;
import com.dashboardfinanceiro.service.AIInsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AIInsightController {

    private final AIInsightService aiInsightService;
    private final AIAnalystService aiAnalystService;

    @Autowired
    public AIInsightController(AIInsightService aiInsightService, AIAnalystService aiAnalystService) {
        this.aiInsightService = aiInsightService;
        this.aiAnalystService = aiAnalystService;
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

    @PostMapping("/api/insights/generate")
    public ResponseEntity<AIInsightResponseDTO> generate() {
        Long userId = SecurityUtils.getAuthenticatedUserId();
        AIInsight insight = aiAnalystService.generateInsight(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AIInsightResponseDTO(insight));
    }
}