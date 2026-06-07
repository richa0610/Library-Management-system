package com.example.richa.libraryManagementSystem.repository;

import com.example.richa.libraryManagementSystem.model.Book;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByStudentId(@Positive Integer studentId);  // select * from book where student_id=?

    @Transactional
    @Modifying
    @Query("update Book b set b.student = ?2 where b.id = ?1")
    void updateBookAvailability(Integer bookId, Integer studentId);
}
