package com.dashboardfinanceiro.dto;

import java.math.BigDecimal;

public class PortfolioAssetDTO {

    private String assetSymbol;
    private BigDecimal quantity;
    private BigDecimal averagePrice;
    private BigDecimal totalInvested;

    public PortfolioAssetDTO(String assetSymbol, BigDecimal quantity, BigDecimal averagePrice, BigDecimal totalInvested) {
        this.assetSymbol = assetSymbol;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
        this.totalInvested = totalInvested;
    }

    public String getAssetSymbol() {
        return assetSymbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public BigDecimal getTotalInvested() {
        return totalInvested;
    }
}