package com.dashboardfinanceiro.service;

import com.dashboardfinanceiro.dto.GroqChatRequest;
import com.dashboardfinanceiro.dto.GroqChatResponse;
import com.dashboardfinanceiro.dto.PortfolioAssetDTO;
import com.dashboardfinanceiro.dto.PortfolioSummaryDTO;
import com.dashboardfinanceiro.entity.AIInsight;
import com.dashboardfinanceiro.entity.User;
import com.dashboardfinanceiro.repository.AIInsightRepository;
import com.dashboardfinanceiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class AIAnalystService {

    private final PortfolioService portfolioService;
    private final AIInsightRepository aiInsightRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Value("${ai.api-key}")
    private String apiKey;

    @Value("${ai.base-url}")
    private String baseUrl;

    @Value("${ai.model}")
    private String model;

    @Autowired
    public AIAnalystService(
            PortfolioService portfolioService,
            AIInsightRepository aiInsightRepository,
            UserRepository userRepository,
            RestTemplate restTemplate) {
        this.portfolioService = portfolioService;
        this.aiInsightRepository = aiInsightRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public AIInsight generateInsight(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        PortfolioSummaryDTO summary = portfolioService.getSummary(userId);

        if (summary.getAssets().isEmpty()) {
            throw new IllegalStateException("Carteira vazia, sem dados para analisar");
        }

        String prompt = buildPrompt(summary);
        String analysisText = callGroq(prompt);

        AIInsight insight = new AIInsight();
        insight.setUser(user);
        insight.setAgentName("Analista de Risco IA");
        insight.setInsightText(analysisText);
        insight.setStatus(AIInsight.InsightStatus.UNREAD);

        return aiInsightRepository.save(insight);
    }

    private String buildPrompt(PortfolioSummaryDTO summary) {
        StringBuilder sb = new StringBuilder();
        sb.append("Você é um analista financeiro especializado em criptoativos. ");
        sb.append("Analise a carteira abaixo e escreva um parecer curto (máximo 4 frases) em português, ");
        sb.append("destacando riscos de concentração (se algum ativo representa mais de 50% do total investido) ");
        sb.append("e sugerindo rebalanceamento se necessário. Seja direto e objetivo.\n\n");
        sb.append("Total investido: R$ ").append(summary.getTotalInvested()).append("\n");
        sb.append("Ativos:\n");

        for (PortfolioAssetDTO asset : summary.getAssets()) {
            double percentage = asset.getTotalInvested()
                    .divide(summary.getTotalInvested(), 4, java.math.RoundingMode.HALF_UP)
                    .doubleValue() * 100;

            sb.append("- ").append(asset.getAssetSymbol())
                    .append(": quantidade ").append(asset.getQuantity())
                    .append(", preço médio R$ ").append(asset.getAveragePrice())
                    .append(", ").append(String.format("%.1f", percentage)).append("% da carteira\n");
        }

        return sb.toString();
    }

    private String callGroq(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        List<GroqChatRequest.GroqMessage> messages = Collections.singletonList(
                new GroqChatRequest.GroqMessage("user", prompt)
        );

        GroqChatRequest request = new GroqChatRequest(model, messages);
        HttpEntity<GroqChatRequest> entity = new HttpEntity<>(request, headers);

        GroqChatResponse response = restTemplate.postForObject(
                baseUrl + "/chat/completions",
                entity,
                GroqChatResponse.class
        );

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            throw new IllegalStateException("Resposta vazia da IA");
        }

        return response.getChoices().get(0).getMessage().getContent();
    }
}