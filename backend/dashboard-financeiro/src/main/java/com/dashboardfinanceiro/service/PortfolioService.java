package com.dashboardfinanceiro.service;

import com.dashboardfinanceiro.dto.PortfolioAssetDTO;
import com.dashboardfinanceiro.dto.PortfolioSummaryDTO;
import com.dashboardfinanceiro.entity.Transaction;
import com.dashboardfinanceiro.entity.User;
import com.dashboardfinanceiro.repository.TransactionRepository;
import com.dashboardfinanceiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public PortfolioService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public PortfolioSummaryDTO getSummary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        List<Transaction> transactions = transactionRepository.findByUserOrderByCreatedAtDesc(user);

        Map<String, BigDecimal> quantityByAsset = new LinkedHashMap<>();
        Map<String, BigDecimal> costByAsset = new LinkedHashMap<>();

        for (Transaction tx : transactions) {
            if (tx.getType() == Transaction.TransactionType.BUY) {
                String asset = tx.getAssetSymbol();
                BigDecimal currentQty = quantityByAsset.getOrDefault(asset, BigDecimal.ZERO);
                BigDecimal currentCost = costByAsset.getOrDefault(asset, BigDecimal.ZERO);

                BigDecimal txCost = tx.getAmount().multiply(tx.getPricePerUnit());

                quantityByAsset.put(asset, currentQty.add(tx.getAmount()));
                costByAsset.put(asset, currentCost.add(txCost));

            } else if (tx.getType() == Transaction.TransactionType.SELL) {
                String asset = tx.getAssetSymbol();
                BigDecimal currentQty = quantityByAsset.getOrDefault(asset, BigDecimal.ZERO);
                BigDecimal currentCost = costByAsset.getOrDefault(asset, BigDecimal.ZERO);

                if (currentQty.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal avgPrice = currentCost.divide(currentQty, 8, RoundingMode.HALF_UP);
                    BigDecimal costReduction = tx.getAmount().multiply(avgPrice);

                    quantityByAsset.put(asset, currentQty.subtract(tx.getAmount()));
                    costByAsset.put(asset, currentCost.subtract(costReduction));
                }
            }
        }

        List<PortfolioAssetDTO> assets = new ArrayList<>();
        BigDecimal totalInvested = BigDecimal.ZERO;

        for (String asset : quantityByAsset.keySet()) {
            BigDecimal quantity = quantityByAsset.get(asset);

            if (quantity.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal cost = costByAsset.get(asset);
                BigDecimal avgPrice = cost.divide(quantity, 8, RoundingMode.HALF_UP);

                assets.add(new PortfolioAssetDTO(asset, quantity, avgPrice, cost));
                totalInvested = totalInvested.add(cost);
            }
        }

        return new PortfolioSummaryDTO(totalInvested, assets);
    }
}