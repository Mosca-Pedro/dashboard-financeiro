package com.dashboardfinanceiro.dto;

import com.dashboardfinanceiro.entity.AIInsight;

import java.time.LocalDateTime;

public class AIInsightResponseDTO {

    private Long id;
    private String agentName;
    private String insightText;
    private AIInsight.InsightStatus status;
    private LocalDateTime createdAt;

    public AIInsightResponseDTO(AIInsight insight) {
        this.id = insight.getId();
        this.agentName = insight.getAgentName();
        this.insightText = insight.getInsightText();
        this.status = insight.getStatus();
        this.createdAt = insight.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getInsightText() {
        return insightText;
    }

    public AIInsight.InsightStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}