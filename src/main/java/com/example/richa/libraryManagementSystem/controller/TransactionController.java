package com.example.richa.libraryManagementSystem.controller;

import com.example.richa.libraryManagementSystem.dtos.TxnRequest;
import com.example.richa.libraryManagementSystem.service.TransactionService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transactions")
    public Integer initiateTxn(@RequestBody @Valid TxnRequest txnRequest) throws BadRequestException {
        return  this.transactionService.initiateTxn(txnRequest).getId();
    }
}
