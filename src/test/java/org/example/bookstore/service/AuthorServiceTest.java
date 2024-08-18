package org.example.bookstore.service;

import org.example.bookstore.model.Author;
import org.example.bookstore.repository.AuthorRepository;
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

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");
    }

    @Test
    void getAllAuthorsReturnsListOfAuthors() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author));
        List<Author> authors = authorService.getAllAuthors();
        assertNotNull(authors);
        assertEquals(1, authors.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void getAuthorByIdReturnsAuthorIfExists() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Optional<Author> foundAuthor = authorService.getAuthorById(1L);
        assertTrue(foundAuthor.isPresent());
        assertEquals("Test Author", foundAuthor.get().getName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void getAuthorByIdReturnsEmptyIfNotExists() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Author> foundAuthor = authorService.getAuthorById(1L);
        assertFalse(foundAuthor.isPresent());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void saveAuthorSavesAndReturnsAuthor() {
        when(authorRepository.save(author)).thenReturn(author);
        Author savedAuthor = authorService.saveAuthor(author);
        assertNotNull(savedAuthor);
        assertEquals("Test Author", savedAuthor.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void deleteAuthorDeletesAuthor() {
        doNothing().when(authorRepository).deleteById(1L);
        authorService.deleteAuthor(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }
}