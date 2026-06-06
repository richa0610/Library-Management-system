package com.example.richa.libraryManagementSystem.dtos;

import com.example.richa.libraryManagementSystem.model.Author;
import com.example.richa.libraryManagementSystem.model.Book;
import com.example.richa.libraryManagementSystem.model.Genre;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

//-------Lombok dependencies-----------
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {

    @NotBlank
    private String name;
    private Genre genre;

    @NotBlank
    private String authorName;

    @NotBlank
    @Email
    private String authorEmail;

    private String authorCountry;


    public Book to(){
        return Book.builder()
                .name(this.name)
                .genre(this.genre)
                .author(
                        Author.builder()
                                .name(this.authorName)
                                .email(this.authorEmail)
                                .country(this.authorCountry)
                                .build()
                )
                .build();
    }
}
