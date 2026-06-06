package com.example.richa.libraryManagementSystem.service;

import com.example.richa.libraryManagementSystem.dtos.Pair;
import com.example.richa.libraryManagementSystem.dtos.TxnRequest;
import com.example.richa.libraryManagementSystem.model.Book;
import com.example.richa.libraryManagementSystem.model.Student;
import com.example.richa.libraryManagementSystem.model.Transaction;
import com.example.richa.libraryManagementSystem.model.TxnType;
import com.example.richa.libraryManagementSystem.repository.BookRepository;
import com.example.richa.libraryManagementSystem.repository.StudentRepository;
import com.example.richa.libraryManagementSystem.repository.TransactionRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookService bookService;

    @Autowired
    StudentService studentService;


    public Transaction initiateTxn(TxnRequest txnRequest) throws BadRequestException {
        Pair bookStudentPair = getBookStudentPair(txnRequest.getBookId(), txnRequest.getStudentId());

        Transaction transaction = Transaction.builder()
                .book(bookStudentPair.getBook())
                .student(bookStudentPair.getStudent())
                .txnType(txnRequest.getTxnType())
                .build();

        return transactionRepository.save(transaction);
    }

    private Pair getBookStudentPair(Integer bookId, Integer studentId) throws BadRequestException {
        Book book = this.bookService.getBookDetailsV2(bookId);
        Student student = this.studentService.getStudentDetails(studentId);

        if (book == null || student == null) {
            throw new BadRequestException("either bookId or studentId is invalid");
        }

        return Pair.builder()
                .book(book)
                .student(student)
                .build();
    }
}
