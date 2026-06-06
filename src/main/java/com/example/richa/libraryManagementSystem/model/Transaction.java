package com.example.richa.libraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

//-------Lombok dependencies-----------
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    private TxnType txnType;

    @Enumerated(value = EnumType.STRING)
    private TxnStatus txnStatus;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @JoinColumn
    @ManyToOne
    private Book book; // book_id

    @JoinColumn
    @ManyToOne
    private Student student;  //student_id

    private Long fine; //only present when the book is returned after the due date
}
