package org.example.bookstore.repository;

import org.example.bookstore.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setName("Test Author");
        author = authorRepository.save(author);
    }

    @Test
    void findAllReturnsListOfAuthors() {
        List<Author> authors = authorRepository.findAll();
        assertNotNull(authors);
        assertFalse(authors.isEmpty());
    }

    @Test
    void findByIdReturnsAuthorIfExists() {
        Optional<Author> foundAuthor = authorRepository.findById(author.getId());
        assertTrue(foundAuthor.isPresent());
        assertEquals("Test Author", foundAuthor.get().getName());
    }

    @Test
    void findByIdReturnsEmptyIfNotExists() {
        Optional<Author> foundAuthor = authorRepository.findById(999L);
        assertFalse(foundAuthor.isPresent());
    }

    @Test
    void saveSavesAndReturnsAuthor() {
        Author newAuthor = new Author();
        newAuthor.setName("New Author");
        Author savedAuthor = authorRepository.save(newAuthor);
        assertNotNull(savedAuthor);
        assertEquals("New Author", savedAuthor.getName());
    }

    @Test
    void deleteDeletesAuthor() {
        authorRepository.deleteById(author.getId());
        Optional<Author> foundAuthor = authorRepository.findById(author.getId());
        assertFalse(foundAuthor.isPresent());
    }
}