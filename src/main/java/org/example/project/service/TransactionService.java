package org.example.project.service;

import org.example.project.dto.TransferRequest;
import org.example.project.entity.Account;
import org.example.project.entity.Transaction;
import org.example.project.repository.AccountRepository;
import org.example.project.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public String transfer(TransferRequest request) {
        // Tạm thời hardcode userId=1 (sau này sẽ lấy từ SecurityContext)
        Account source = accountRepository.findByUserId(1L)
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        Account target = accountRepository.findByAccountNumber(request.getTargetAccountNumber())
                .orElseThrow(() -> new RuntimeException("Target account not found"));

        if (source.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        source.setBalance(source.getBalance().subtract(request.getAmount()));
        target.setBalance(target.getBalance().add(request.getAmount()));

        Transaction tx = new Transaction();
        tx.setFromAccount(source);
        tx.setToAccount(target);
        tx.setAmount(request.getAmount());
        transactionRepository.save(tx);

        accountRepository.save(source);
        accountRepository.save(target);

        return "Transfer successful";
    }
}