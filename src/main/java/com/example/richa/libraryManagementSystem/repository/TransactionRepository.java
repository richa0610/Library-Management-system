package com.example.richa.libraryManagementSystem.repository;

import com.example.richa.libraryManagementSystem.model.Transaction;
import com.example.richa.libraryManagementSystem.model.TxnStatus;
import com.example.richa.libraryManagementSystem.model.TxnType;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Transaction findTopByStudentIdAndBookIdAndTxnTypeAndTxnStatusOrderByIdDesc( Integer studentId,
                                                                                 Integer bookId,
                                                                                TxnType txnType,
                                                                                TxnStatus txnStatus);
}
