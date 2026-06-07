package com.example.richa.libraryManagementSystem.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionUtils {

    @Value("${books.max-allowed}")
    @Getter
    Integer maxAllowedBooksForIssurance;
}
