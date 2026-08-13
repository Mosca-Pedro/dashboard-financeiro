package com.dashboardfinanceiro.service;

import com.dashboardfinanceiro.dto.TransactionRequestDTO;
import com.dashboardfinanceiro.dto.TransactionResponseDTO;
import com.dashboardfinanceiro.entity.Transaction;
import com.dashboardfinanceiro.entity.User;
import com.dashboardfinanceiro.repository.TransactionRepository;
import com.dashboardfinanceiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public TransactionResponseDTO create(Long userId, TransactionRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType(dto.getType());
        transaction.setAssetSymbol(dto.getAssetSymbol());
        transaction.setAmount(dto.getAmount());
        transaction.setPricePerUnit(dto.getPricePerUnit());

        Transaction saved = transactionRepository.save(transaction);
        return new TransactionResponseDTO(saved);
    }

    public List<TransactionResponseDTO> listByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return transactionRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(TransactionResponseDTO::new)
                .collect(Collectors.toList());
    }
}

