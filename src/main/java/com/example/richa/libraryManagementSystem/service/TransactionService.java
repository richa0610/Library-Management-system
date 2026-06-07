package com.example.richa.libraryManagementSystem.service;

import com.example.richa.libraryManagementSystem.dtos.Pair;
import com.example.richa.libraryManagementSystem.dtos.TxnRequest;
import com.example.richa.libraryManagementSystem.model.*;
import com.example.richa.libraryManagementSystem.repository.BookRepository;
import com.example.richa.libraryManagementSystem.repository.StudentRepository;
import com.example.richa.libraryManagementSystem.repository.TransactionRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookService bookService;

    @Autowired
    StudentService studentService;

    @Autowired
    TransactionUtils transactionUtils;

    @Value("${books.return.due-date}")
    Integer returnDueDateThreshold;

    @Value("${books.fine-per-day}")
    Integer finePerDay;


    public Transaction initiateTxn(TxnRequest txnRequest) throws BadRequestException {
        Pair bookStudentPair = getBookStudentPair(txnRequest.getBookId(), txnRequest.getStudentId());

        if (txnRequest.getTxnType() == TxnType.ISSUE) {
            return initiateIssueTxn(txnRequest, bookStudentPair);
        } else {
            return initiateReturnTxn(txnRequest, bookStudentPair);
        }

    }

    private Transaction initiateIssueTxn(TxnRequest txnRequest, Pair bookStudentPair) throws BadRequestException {

        if (bookStudentPair.getBook().getStudent() != null) {
            throw new BadRequestException("Book is already issued to someone ");
        }

        List<Book> issuedBooks = this.bookService.getBooksIssuedToStudent(txnRequest.getStudentId());

        if (issuedBooks != null && issuedBooks.size() >= this.transactionUtils.getMaxAllowedBooksForIssurance()) {
            throw new BadRequestException("Student has already issued maximum number of books");
        }

        //     -------------  Creating transactions -----------
        Transaction transaction = getTxnObjectInPendingState(txnRequest);
        transaction = this.transactionRepository.save(transaction); // created_on

        try {

            this.bookService.issueBookToStudent(txnRequest.getBookId(), txnRequest.getStudentId());
            transaction.setTxnStatus(TxnStatus.COMPLETED);
            transaction = this.transactionRepository.save(transaction);
        } catch (Exception e) {
            e.printStackTrace();
            transaction.setTxnStatus(TxnStatus.FAILED);
            this.transactionRepository.save(transaction);

            bookStudentPair.getBook().setStudent(null);
            this.bookService.createBook(bookStudentPair.getBook());
        }

        return transaction;
    }


    private Transaction initiateReturnTxn(TxnRequest txnRequest, Pair bookStudentPair) throws BadRequestException {


        if(bookStudentPair.getBook().getStudent() == null ||
                (bookStudentPair.getBook().getStudent().getId() != null && !bookStudentPair.getBook().getStudent().getId().equals(txnRequest.getStudentId()))){
            throw new BadRequestException("Book is not issued to the student");
        }

        Transaction transaction=getTxnObjectInPendingState(txnRequest);
        transaction=this.transactionRepository.save(transaction);

        try{
            this.bookService.issueBookToStudent(bookStudentPair.getBook().getId(), null);

            Transaction issueTxn = this.transactionRepository.findTopByStudentIdAndBookIdAndTxnTypeAndTxnStatusOrderByIdDesc(
                    txnRequest.getStudentId(), txnRequest.getBookId(), TxnType.ISSUE, TxnStatus.COMPLETED);

            transaction.setFine(calculateFine(issueTxn.getCreatedOn()));
            transaction.setTxnStatus(TxnStatus.COMPLETED);
            this.transactionRepository.save(transaction);

        }catch (Exception e){
            e.printStackTrace();
            transaction.setTxnStatus(TxnStatus.FAILED);
            transaction.setFine(null);
            this.transactionRepository.save(transaction);

            // roll back the book de-allocation
            bookStudentPair.getBook().setStudent(bookStudentPair.getStudent());
            this.bookService.createBook(bookStudentPair.getBook());
        }

        return transaction;

    }



    private Transaction getTxnObjectInPendingState(TxnRequest txnRequest) {
        return Transaction.builder()
                .book(
                        Book.builder().id(txnRequest.getBookId()).build()
                )
                .student(
                        Student.builder().id(txnRequest.getStudentId()).build()
                )
                .txnType(txnRequest.getTxnType())
                .txnStatus(TxnStatus.PENDING)
                .build();
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

    private long calculateFine(Date issueDate){

        long issueTimeInEpoch = issueDate.getTime();
        long currentTimeInEpoch = System.currentTimeMillis();

        long timeDiff = currentTimeInEpoch - issueTimeInEpoch;

        long daysPassed = TimeUnit.MILLISECONDS.toDays(timeDiff);

        if (daysPassed > returnDueDateThreshold) {
            return (daysPassed - returnDueDateThreshold) * finePerDay;
        }

        return 0;
    }
}
