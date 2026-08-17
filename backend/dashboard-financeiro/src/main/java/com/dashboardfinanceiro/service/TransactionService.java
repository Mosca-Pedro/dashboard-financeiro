package com.dashboardfinanceiro.service;

import com.dashboardfinanceiro.dto.TransactionRequestDTO;
import com.dashboardfinanceiro.dto.TransactionResponseDTO;
import com.dashboardfinanceiro.entity.Transaction;
import com.dashboardfinanceiro.entity.User;
import com.dashboardfinanceiro.repository.TransactionRepository;
import com.dashboardfinanceiro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TransactionService(
            TransactionRepository transactionRepository,
            UserRepository userRepository,
            SimpMessagingTemplate messagingTemplate) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
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
        TransactionResponseDTO response = new TransactionResponseDTO(saved);

        messagingTemplate.convertAndSend("/topic/users/" + userId + "/transactions", response);

        return response;
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