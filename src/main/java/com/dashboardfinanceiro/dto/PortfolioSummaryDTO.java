package com.dashboardfinanceiro.dto;

import java.math.BigDecimal;
import java.util.List;

public class PortfolioSummaryDTO {

    private BigDecimal totalInvested;
    private List<PortfolioAssetDTO> assets;

    public PortfolioSummaryDTO(BigDecimal totalInvested, List<PortfolioAssetDTO> assets) {
        this.totalInvested = totalInvested;
        this.assets = assets;
    }

    public BigDecimal getTotalInvested() {
        return totalInvested;
    }

    public List<PortfolioAssetDTO> getAssets() {
        return assets;
    }
}