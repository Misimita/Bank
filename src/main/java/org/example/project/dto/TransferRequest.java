package org.example.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotBlank(message = "Số tài khoản nguồn không được để trống")
    private String sourceAccountNumber;

    @NotBlank(message = "Số tài khoản đích không được để trống")
    private String targetAccountNumber;

    @Positive(message = "Số tiền phải lớn hơn 0")
    private BigDecimal amount;

    // PIN giao dịch (nếu SRS yêu cầu)
    private String pin;
}