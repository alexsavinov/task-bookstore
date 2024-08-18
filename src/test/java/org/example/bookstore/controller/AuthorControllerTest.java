package org.example.bookstore.controller;

import org.example.bookstore.model.Author;
import org.example.bookstore.service.AuthorService;
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

class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();

        author = new Author();
        author.setId(1L);
        author.setName("Test Author");
    }

    @Test
    void getAllAuthorsReturnsListOfAuthors() throws Exception {
        when(authorService.getAllAuthors()).thenReturn(Arrays.asList(author));
        mockMvc.perform(get("/api/authors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Author"));
        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    void getAuthorByIdReturnsAuthorIfExists() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));
        mockMvc.perform(get("/api/authors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Author"));
        verify(authorService, times(1)).getAuthorById(1L);
    }

    @Test
    void getAuthorByIdReturnsNotFoundIfNotExists() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/authors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(authorService, times(1)).getAuthorById(1L);
    }

    @Test
    void createAuthorCreatesAndReturnsAuthor() throws Exception {
        when(authorService.saveAuthor(any(Author.class))).thenReturn(author);
        mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Author\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Author"));
        verify(authorService, times(1)).saveAuthor(any(Author.class));
    }

    @Test
    void updateAuthorUpdatesAndReturnsAuthorIfExists() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));
        when(authorService.saveAuthor(any(Author.class))).thenReturn(author);
        mockMvc.perform(put("/api/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Author\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Author"));
        verify(authorService, times(1)).getAuthorById(1L);
        verify(authorService, times(1)).saveAuthor(any(Author.class));
    }

    @Test
    void updateAuthorReturnsNotFoundIfNotExists() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/authors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Author\"}"))
                .andExpect(status().isNotFound());
        verify(authorService, times(1)).getAuthorById(1L);
    }

    @Test
    void deleteAuthorDeletesAuthorIfExists() throws Exception {
        doNothing().when(authorService).deleteAuthor(1L);
        mockMvc.perform(delete("/api/authors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(authorService, times(1)).deleteAuthor(1L);
    }
}