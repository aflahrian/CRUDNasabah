package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;

@Service
public class TransactionService {

	private final TransactionRepository transactionRepository;
	
	 public TransactionService(TransactionRepository transactionRepository) {
	        this.transactionRepository = transactionRepository;
	    }
	public Transaction createTransaction(Transaction transaction) {
		 return transactionRepository.save(transaction);
	}
	public List<Transaction> getAllTransactions() {
		   return transactionRepository.findAll();
	}
	public BigDecimal getTotalAmountByDescriptionAndAccountId(long accountId, String description) {
		
        BigDecimal totalAmount = transactionRepository.getCoalesceSumAmountByDescriptionAndAccountId(accountId, description);
        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        return totalAmount;
	}
	public List<Transaction> findTransactionsByAccountIdAndDateRange(long accountId, Date startDate, Date endDate) {
		return transactionRepository.findTransactionsByAccountIdAndDateRange(accountId, startDate, endDate);
	}

}
