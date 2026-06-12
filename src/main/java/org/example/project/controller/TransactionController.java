package org.example.project.controller;

import org.example.project.dto.TransferRequest;
import org.example.project.dto.TransferResponse;
import org.example.project.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'ADMIN', 'STAFF')")
    public ResponseEntity<Map<String, Object>> transfer(@RequestBody TransferRequest request) {

        TransferResponse result = transactionService.transfer(request);

        // Tạo response theo đúng format SRS
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Chuyển tiền thành công");
        response.put("data", result);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
}