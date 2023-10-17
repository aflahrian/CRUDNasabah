package com.example.demo.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

	
	@Query("SELECT COALESCE(SUM(t.amount), 0) AS totalAmount FROM Transaction t WHERE t.accountId = :accountId AND t.description = :description")
	BigDecimal getCoalesceSumAmountByDescriptionAndAccountId(@Param("accountId") long accountId, @Param("description") String description);

	  @Query("SELECT t FROM Transaction t " +
	           "WHERE t.accountId = :accountId " +
	           "AND t.transactionDate >= :startDate " +
	           "AND t.transactionDate <= :endDate")
	    List<Transaction> findTransactionsByAccountIdAndDateRange(
	            @Param("accountId") long accountId,
	            @Param("startDate") Date startDate,
	            @Param("endDate") Date endDate
	    );

}
