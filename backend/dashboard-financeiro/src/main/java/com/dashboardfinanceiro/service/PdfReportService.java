package com.dashboardfinanceiro.service;

import com.dashboardfinanceiro.dto.PortfolioAssetDTO;
import com.dashboardfinanceiro.dto.PortfolioSummaryDTO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfReportService {

    public byte[] generatePortfolioReport(String userName, PortfolioSummaryDTO summary) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font subtitleFont = new Font(Font.HELVETICA, 11, Font.NORMAL);
            Font headerFont = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);
            Font cellFont = new Font(Font.HELVETICA, 10, Font.NORMAL);

            Paragraph title = new Paragraph("Relatório de Carteira", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            String dataGeracao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            Paragraph subtitle = new Paragraph("Gerado para " + userName + " em " + dataGeracao, subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

            Paragraph totalTitle = new Paragraph("Total Investido: R$ " + formatMoney(summary.getTotalInvested()), new Font(Font.HELVETICA, 13, Font.BOLD));
            totalTitle.setSpacingAfter(15);
            document.add(totalTitle);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            String[] headers = {"Ativo", "Quantidade", "Preço Médio", "Total Investido"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(new Color(15, 23, 42));
                cell.setPadding(8);
                table.addCell(cell);
            }

            for (PortfolioAssetDTO asset : summary.getAssets()) {
                table.addCell(new PdfPCell(new Phrase(asset.getAssetSymbol(), cellFont)) {{ setPadding(8); }});
                table.addCell(new PdfPCell(new Phrase(formatMoney(asset.getQuantity()), cellFont)) {{ setPadding(8); }});
                table.addCell(new PdfPCell(new Phrase("R$ " + formatMoney(asset.getAveragePrice()), cellFont)) {{ setPadding(8); }});
                table.addCell(new PdfPCell(new Phrase("R$ " + formatMoney(asset.getTotalInvested()), cellFont)) {{ setPadding(8); }});
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }

        return outputStream.toByteArray();
    }

    private String formatMoney(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}