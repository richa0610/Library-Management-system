package com.example.richa.libraryManagementSystem.dtos;

import com.example.richa.libraryManagementSystem.model.Author;
import com.example.richa.libraryManagementSystem.model.Book;
import com.example.richa.libraryManagementSystem.model.Genre;
import lombok.*;

//-------Lombok dependencies-----------
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {

    private String  name;
    private Genre genre;
    private Author author;

    public static BookResponse from(Book book){
        return BookResponse.builder()
                .name(book.getName())
                .genre(book.getGenre())
                .author(book.getAuthor())
                .build();
    }
}
