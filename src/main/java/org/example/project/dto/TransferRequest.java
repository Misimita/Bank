package org.example.project.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String targetAccountNumber;
    private BigDecimal amount;
}