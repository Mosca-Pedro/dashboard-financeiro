package com.dashboardfinanceiro.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPortfolioReport(String toEmail, String userName, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Seu Relatório de Carteira - Dashboard Financeiro");
            helper.setText(
                    "Olá, " + userName + "!\n\n" +
                            "Segue em anexo o relatório atualizado da sua carteira de investimentos.\n\n" +
                            "Atenciosamente,\nDashboard Financeiro Inteligente"
            );

            helper.addAttachment("relatorio-carteira.pdf", new org.springframework.core.io.ByteArrayResource(pdfBytes));

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }
}