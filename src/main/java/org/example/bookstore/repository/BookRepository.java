package org.example.bookstore.repository;

import org.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorNameContaining(String authorName);
    List<Book> findByGenreNameContaining(String genreName);
}