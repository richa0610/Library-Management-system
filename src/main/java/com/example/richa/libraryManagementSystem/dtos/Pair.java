package com.example.richa.libraryManagementSystem.dtos;

import com.example.richa.libraryManagementSystem.model.Book;
import com.example.richa.libraryManagementSystem.model.Student;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pair {

    private Student student;
    private Book book;
}
