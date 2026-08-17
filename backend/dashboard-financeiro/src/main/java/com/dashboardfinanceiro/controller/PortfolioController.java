package com.dashboardfinanceiro.controller;

import com.dashboardfinanceiro.config.SecurityUtils;
import com.dashboardfinanceiro.dto.PortfolioSummaryDTO;
import com.dashboardfinanceiro.entity.User;
import com.dashboardfinanceiro.repository.UserRepository;
import com.dashboardfinanceiro.service.EmailService;
import com.dashboardfinanceiro.service.PdfReportService;
import com.dashboardfinanceiro.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PdfReportService pdfReportService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Autowired
    public PortfolioController(
            PortfolioService portfolioService,
            PdfReportService pdfReportService,
            EmailService emailService,
            UserRepository userRepository) {
        this.portfolioService = portfolioService;
        this.pdfReportService = pdfReportService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/portfolio/summary")
    public ResponseEntity<PortfolioSummaryDTO> getSummary() {
        Long userId = SecurityUtils.getAuthenticatedUserId();
        PortfolioSummaryDTO summary = portfolioService.getSummary(userId);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/api/portfolio/report/pdf")
    public ResponseEntity<byte[]> downloadReport() {
        Long userId = SecurityUtils.getAuthenticatedUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        PortfolioSummaryDTO summary = portfolioService.getSummary(userId);
        byte[] pdfBytes = pdfReportService.generatePortfolioReport(user.getName(), summary);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "relatorio-carteira.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @PostMapping("/api/portfolio/report/email")
    public ResponseEntity<String> emailReport() {
        Long userId = SecurityUtils.getAuthenticatedUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        PortfolioSummaryDTO summary = portfolioService.getSummary(userId);
        byte[] pdfBytes = pdfReportService.generatePortfolioReport(user.getName(), summary);

        emailService.sendPortfolioReport(user.getEmail(), user.getName(), pdfBytes);

        return ResponseEntity.ok("Relatório enviado para " + user.getEmail());
    }
}