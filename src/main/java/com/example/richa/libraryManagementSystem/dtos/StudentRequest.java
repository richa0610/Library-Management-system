package com.example.richa.libraryManagementSystem.dtos;

import com.example.richa.libraryManagementSystem.model.Department;
import com.example.richa.libraryManagementSystem.model.Student;
import lombok.*;

//-------Lombok dependencies-----------
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequest {

    private String name;
    private String email;
    private String rollNumber;
    private Department department;

    public Student to() {
        return Student.builder()
                .name(this.name)
                .email(this.email)
                .rollNumber(this.rollNumber)
                .department(this.department)
                .build();
    }
}
