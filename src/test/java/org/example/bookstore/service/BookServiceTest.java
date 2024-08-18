package org.example.bookstore.service;

import org.example.bookstore.model.Author;
import org.example.bookstore.model.Book;
import org.example.bookstore.model.Genre;
import org.example.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Author author;
    private Genre genre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        genre = new Genre();
        genre.setId(1L);
        genre.setName("Test Genre");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPrice(10.0);
        book.setQuantity(5);
    }

    @Test
    void getAllBooksReturnsListOfBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        List<Book> books = bookService.getAllBooks();
        assertNotNull(books);
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookByIdReturnsBookIfExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<Book> foundBook = bookService.getBookById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals("Test Book", foundBook.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookByIdReturnsEmptyIfNotExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Book> foundBook = bookService.getBookById(1L);
        assertFalse(foundBook.isPresent());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void saveBookSavesAndReturnsBook() {
        when(bookRepository.save(book)).thenReturn(book);
        Book savedBook = bookService.saveBook(book);
        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void deleteBookDeletesBook() {
        doNothing().when(bookRepository).deleteById(1L);
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void searchBooksByTitleReturnsListOfBooks() {
        when(bookRepository.findByTitleContaining("Test")).thenReturn(Arrays.asList(book));
        List<Book> books = bookService.searchBooksByTitle("Test");
        assertNotNull(books);
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findByTitleContaining("Test");
    }

    @Test
    void searchBooksByAuthorReturnsListOfBooks() {
        when(bookRepository.findByAuthorNameContaining("Test Author")).thenReturn(Arrays.asList(book));
        List<Book> books = bookService.searchBooksByAuthor("Test Author");
        assertNotNull(books);
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findByAuthorNameContaining("Test Author");
    }

    @Test
    void searchBooksByGenreReturnsListOfBooks() {
        when(bookRepository.findByGenreNameContaining("Test Genre")).thenReturn(Arrays.asList(book));
        List<Book> books = bookService.searchBooksByGenre("Test Genre");
        assertNotNull(books);
        assertEquals(1, books.size());
        verify(bookRepository, times(1)).findByGenreNameContaining("Test Genre");
    }
}