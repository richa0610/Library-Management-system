package com.example.richa.libraryManagementSystem.service;

import com.example.richa.libraryManagementSystem.dtos.StudentRequest;
import com.example.richa.libraryManagementSystem.model.Student;
import com.example.richa.libraryManagementSystem.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Integer createStudent(@Valid StudentRequest studentRequest) {
        Student student = studentRequest.to();

        student = this.studentRepository.save(student);
        return student.getId();
    }

    public Student getStudentDetails(Integer id) {
        return this.studentRepository.findById(id).orElse(null);
    }
}
