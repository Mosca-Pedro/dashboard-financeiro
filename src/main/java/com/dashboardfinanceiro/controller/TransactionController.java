package com.dashboardfinanceiro.controller;

import com.dashboardfinanceiro.dto.TransactionRequestDTO;
import com.dashboardfinanceiro.dto.TransactionResponseDTO;
import com.dashboardfinanceiro.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(
            @PathVariable Long userId,
            @Valid @RequestBody TransactionRequestDTO dto) {
        TransactionResponseDTO response = transactionService.create(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> list(@PathVariable Long userId) {
        List<TransactionResponseDTO> transactions = transactionService.listByUser(userId);
        return ResponseEntity.ok(transactions);
    }
}