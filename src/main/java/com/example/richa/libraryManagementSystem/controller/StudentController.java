package com.example.richa.libraryManagementSystem.controller;

import com.example.richa.libraryManagementSystem.dtos.StudentRequest;
import com.example.richa.libraryManagementSystem.model.Student;
import com.example.richa.libraryManagementSystem.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/students")
    public Integer createStudent(@Valid @RequestBody StudentRequest studentRequest){
         return this.studentService.createStudent(studentRequest);
    }
    @GetMapping("/students/{studentId}")
    public Student getStudentDetails(@PathVariable("studentId") Integer id){
        return  this.studentService.getStudentDetails(id);
    }
}
