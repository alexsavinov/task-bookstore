package org.example.bookstore.controller;

import org.example.bookstore.model.Author;
import org.example.bookstore.model.Book;
import org.example.bookstore.model.Genre;
import org.example.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;
    private Author author;
    private Genre genre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

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
    void getAllBooksReturnsListOfBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book));
        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookByIdReturnsBookIfExists() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        mockMvc.perform(get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookByIdReturnsNotFoundIfNotExists() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void createBookCreatesAndReturnsBook() throws Exception {
        when(bookService.saveBook(any(Book.class))).thenReturn(book);
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Test Book\", \"author\": {\"id\": 1}, \"genre\": {\"id\": 1}, \"price\": 10.0, \"quantity\": 5}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"));
        verify(bookService, times(1)).saveBook(any(Book.class));
    }

    @Test
    void updateBookUpdatesAndReturnsBookIfExists() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(bookService.saveBook(any(Book.class))).thenReturn(book);
        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Updated Book\", \"author\": {\"id\": 1}, \"genre\": {\"id\": 1}, \"price\": 15.0, \"quantity\": 10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"));
        verify(bookService, times(1)).getBookById(1L);
        verify(bookService, times(1)).saveBook(any(Book.class));
    }

    @Test
    void updateBookReturnsNotFoundIfNotExists() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Updated Book\", \"author\": {\"id\": 1}, \"genre\": {\"id\": 1}, \"price\": 15.0, \"quantity\": 10}"))
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void deleteBookDeletesBookIfExists() throws Exception {
        doNothing().when(bookService).deleteBook(1L);
        mockMvc.perform(delete("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void searchBooksByTitleReturnsListOfBooks() throws Exception {
        when(bookService.searchBooksByTitle("Test Book")).thenReturn(Arrays.asList(book));
        mockMvc.perform(get("/api/books/search")
                .param("title", "Test Book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));
        verify(bookService, times(1)).searchBooksByTitle("Test Book");
    }

    @Test
    void searchBooksByAuthorReturnsListOfBooks() throws Exception {
        when(bookService.searchBooksByAuthor("Test Author")).thenReturn(Arrays.asList(book));
        mockMvc.perform(get("/api/books/search")
                .param("author", "Test Author")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author.name").value("Test Author"));
        verify(bookService, times(1)).searchBooksByAuthor("Test Author");
    }

    @Test
    void searchBooksByGenreReturnsListOfBooks() throws Exception {
        when(bookService.searchBooksByGenre("Test Genre")).thenReturn(Arrays.asList(book));
        mockMvc.perform(get("/api/books/search")
                .param("genre", "Test Genre")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].genre.name").value("Test Genre"));
        verify(bookService, times(1)).searchBooksByGenre("Test Genre");
    }

    @Test
    void searchBooksReturnsAllBooksIfNoParams() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book));
        mockMvc.perform(get("/api/books/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));
        verify(bookService, times(1)).getAllBooks();
    }
}