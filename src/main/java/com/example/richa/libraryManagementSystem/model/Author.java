package com.example.richa.libraryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.id.IntegralDataTypeHolder;

import java.util.Date;

//-------Lombok dependencies-----------
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

//-------------JPA dependencies------------
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private String country;

    @CreationTimestamp
    private Date createdOn;

}
