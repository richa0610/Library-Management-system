package com.example.richa.libraryManagementSystem.service;

import com.example.richa.libraryManagementSystem.model.Author;
import com.example.richa.libraryManagementSystem.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public Author getOrCreateAuthor(Author author) {
        Author authorFromDB = this.authorRepository.findByEmail(author.getEmail());

        if (authorFromDB == null) {
            // we need to create  the author because it doesn't exist
            authorFromDB = createAuthor(author);
        }
        return authorFromDB;
    }

    private Author createAuthor(Author author) {
        return this.authorRepository.save(author);
    }
}
