package com.dashboardfinanceiro.dto;

import com.dashboardfinanceiro.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDTO {

    private Long id;
    private Transaction.TransactionType type;
    private String assetSymbol;
    private BigDecimal amount;
    private BigDecimal pricePerUnit;
    private LocalDateTime createdAt;

    public TransactionResponseDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.assetSymbol = transaction.getAssetSymbol();
        this.amount = transaction.getAmount();
        this.pricePerUnit = transaction.getPricePerUnit();
        this.createdAt = transaction.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public Transaction.TransactionType getType() {
        return type;
    }

    public String getAssetSymbol() {
        return assetSymbol;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}