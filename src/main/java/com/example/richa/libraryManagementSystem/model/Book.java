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

//-------------JPA dependencies------------
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @JoinColumn
    @ManyToOne
    private Author author;

    @JoinColumn
    @ManyToOne
    private Student student;

}
