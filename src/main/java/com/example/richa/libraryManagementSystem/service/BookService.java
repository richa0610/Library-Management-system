package com.example.richa.libraryManagementSystem.service;

import com.example.richa.libraryManagementSystem.dtos.BookRequest;
import com.example.richa.libraryManagementSystem.dtos.BookResponse;
import com.example.richa.libraryManagementSystem.model.Author;
import com.example.richa.libraryManagementSystem.model.Book;
import com.example.richa.libraryManagementSystem.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;

    public Integer createBook(@Valid BookRequest bookRequest) {

        Book book = bookRequest.to();
        Author author = book.getAuthor();

        author = this.authorService.getOrCreateAuthor(author);
        book.setAuthor(author);

        createBook(book);

        return book.getId();
    }

    public void createBook(Book book) {
        this.bookRepository.save(book);
    }

    public BookResponse getBookDetails(Integer bookId) {
        Book book = this.bookRepository.findById(bookId).orElse(null);
        return book == null ? null: BookResponse.from(book);
    }

    public Book getBookDetailsV2(Integer bookId) {
       return this.bookRepository.findById(bookId).orElse(null);

    }
}
