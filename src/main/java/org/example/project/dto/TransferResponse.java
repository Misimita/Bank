package org.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransferResponse {
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;
    private BigDecimal sourceNewBalance;
    private BigDecimal targetNewBalance;
    private String transactionId;
    private LocalDateTime timestamp;
    private String message;
}