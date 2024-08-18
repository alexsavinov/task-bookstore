package org.example.bookstore.repository;

import org.example.bookstore.model.Author;
import org.example.bookstore.model.Book;
import org.example.bookstore.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    private Book book;
    private Author author;
    private Genre genre;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setName("Test Author");
        author = authorRepository.save(author);

        genre = new Genre();
        genre.setName("Test Genre");
        genre = genreRepository.save(genre);

        book = new Book();
        book.setTitle("Test Book");
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPrice(10.0);
        book.setQuantity(5);
        book = bookRepository.save(book);
    }

    @Test
    void findAllReturnsListOfBooks() {
        List<Book> books = bookRepository.findAll();
        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

    @Test
    void findByIdReturnsBookIfExists() {
        Optional<Book> foundBook = bookRepository.findById(book.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getTitle());
    }

    @Test
    void findByIdReturnsEmptyIfNotExists() {
        Optional<Book> foundBook = bookRepository.findById(999L);
        assertFalse(foundBook.isPresent());
    }

    @Test
    void saveSavesAndReturnsBook() {
        Book newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setAuthor(author);
        newBook.setGenre(genre);
        newBook.setPrice(15.0);
        newBook.setQuantity(10);
        Book savedBook = bookRepository.save(newBook);
        assertNotNull(savedBook);
        assertEquals("New Book", savedBook.getTitle());
    }

    @Test
    void deleteDeletesBook() {
        bookRepository.deleteById(book.getId());
        Optional<Book> foundBook = bookRepository.findById(book.getId());
        assertFalse(foundBook.isPresent());
    }
}