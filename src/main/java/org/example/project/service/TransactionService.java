package org.example.project.service;

import org.example.project.dto.TransferRequest;
import org.example.project.dto.TransferResponse;
import org.example.project.entity.Account;
import org.example.project.entity.Transaction;
import org.example.project.exception.InsufficientBalanceException;
import org.example.project.repository.AccountRepository;
import org.example.project.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransferResponse transfer(TransferRequest request) {

        // Tìm tài khoản nguồn và đích
        Account source = accountRepository.findByAccountNumber(request.getSourceAccountNumber())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản nguồn: " + request.getSourceAccountNumber()));

        Account target = accountRepository.findByAccountNumber(request.getTargetAccountNumber())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản đích: " + request.getTargetAccountNumber()));

        BigDecimal amount = request.getAmount();

        // Kiểm tra số dư
        if (source.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Số dư không đủ để thực hiện giao dịch");
        }

        // Thực hiện chuyển tiền
        BigDecimal sourceNewBalance = source.getBalance().subtract(amount);
        BigDecimal targetNewBalance = target.getBalance().add(amount);

        source.setBalance(sourceNewBalance);
        target.setBalance(targetNewBalance);

        accountRepository.save(source);
        accountRepository.save(target);

        // Tạo Transaction
        Transaction transaction = new Transaction();
        transaction.setFromAccount(source);
        transaction.setToAccount(target);
        transaction.setAmount(amount);
        transaction.setTransactionType("TRANSFER");
        // timestamp sẽ tự set nhờ @PrePersist

        transactionRepository.save(transaction);

        // Trả về response đầy đủ thông tin
        return new TransferResponse(
                source.getAccountNumber(),
                target.getAccountNumber(),
                amount,
                sourceNewBalance,
                targetNewBalance,
                transaction.getId().toString(),   // Dùng ID của Transaction
                LocalDateTime.now(),
                "Chuyển khoản thành công"
        );
    }
}