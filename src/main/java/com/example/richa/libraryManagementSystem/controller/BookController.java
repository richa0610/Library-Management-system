package com.example.richa.libraryManagementSystem.controller;

import com.example.richa.libraryManagementSystem.dtos.BookRequest;
import com.example.richa.libraryManagementSystem.dtos.BookResponse;
import com.example.richa.libraryManagementSystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/books")
    public Integer createBook(@Valid @RequestBody BookRequest bookRequest){
        return this.bookService.createBook(bookRequest);
    }

    @GetMapping("/books/{bookId}")
    public BookResponse getBookDetails(@PathVariable("bookId")Integer bookId){
        return this.bookService.getBookDetails(bookId);
    }
}
