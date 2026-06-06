package com.example.richa.libraryManagementSystem.repository;

import com.example.richa.libraryManagementSystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {

    Author findByEmail(String email);
}
