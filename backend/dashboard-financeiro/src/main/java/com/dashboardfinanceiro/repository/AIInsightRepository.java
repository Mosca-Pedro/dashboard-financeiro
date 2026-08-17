package com.dashboardfinanceiro.repository;

import com.dashboardfinanceiro.entity.AIInsight;
import com.dashboardfinanceiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIInsightRepository extends JpaRepository<AIInsight, Long> {

    List<AIInsight> findByUserOrderByCreatedAtDesc(User user);

    List<AIInsight> findByUserAndStatus(User user, AIInsight.InsightStatus status);

}