package com.dashboardfinanceiro.service;

import com.dashboardfinanceiro.dto.AIInsightResponseDTO;
import com.dashboardfinanceiro.entity.AIInsight;
import com.dashboardfinanceiro.entity.User;
import com.dashboardfinanceiro.repository.AIInsightRepository;
import com.dashboardfinanceiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AIInsightService {

    private final AIInsightRepository aiInsightRepository;
    private final UserRepository userRepository;

    @Autowired
    public AIInsightService(AIInsightRepository aiInsightRepository, UserRepository userRepository) {
        this.aiInsightRepository = aiInsightRepository;
        this.userRepository = userRepository;
    }

    public List<AIInsightResponseDTO> listByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return aiInsightRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(AIInsightResponseDTO::new)
                .collect(Collectors.toList());
    }

    public AIInsightResponseDTO markAsRead(Long insightId) {
        AIInsight insight = aiInsightRepository.findById(insightId)
                .orElseThrow(() -> new IllegalArgumentException("Insight não encontrado"));

        insight.setStatus(AIInsight.InsightStatus.READ);
        AIInsight updated = aiInsightRepository.save(insight);
        return new AIInsightResponseDTO(updated);
    }
}