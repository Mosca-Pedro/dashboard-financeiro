package com.dashboardfinanceiro.repository;

import com.dashboardfinanceiro.entity.Transaction;
import com.dashboardfinanceiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserOrderByCreatedAtDesc(User user);

    List<Transaction> findByUserAndAssetSymbol(User user, String assetSymbol);

}