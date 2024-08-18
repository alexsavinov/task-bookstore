package org.example.bookstore.repository;

import org.example.bookstore.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setName("Test Genre");
        genre = genreRepository.save(genre);
    }

    @Test
    void findAllReturnsListOfGenres() {
        List<Genre> genres = genreRepository.findAll();
        assertNotNull(genres);
        assertFalse(genres.isEmpty());
    }

    @Test
    void findByIdReturnsGenreIfExists() {
        Optional<Genre> foundGenre = genreRepository.findById(genre.getId());
        assertTrue(foundGenre.isPresent());
        assertEquals("Test Genre", foundGenre.get().getName());
    }

    @Test
    void findByIdReturnsEmptyIfNotExists() {
        Optional<Genre> foundGenre = genreRepository.findById(999L);
        assertFalse(foundGenre.isPresent());
    }

    @Test
    void saveSavesAndReturnsGenre() {
        Genre newGenre = new Genre();
        newGenre.setName("New Genre");
        Genre savedGenre = genreRepository.save(newGenre);
        assertNotNull(savedGenre);
        assertEquals("New Genre", savedGenre.getName());
    }

    @Test
    void deleteDeletesGenre() {
        genreRepository.deleteById(genre.getId());
        Optional<Genre> foundGenre = genreRepository.findById(genre.getId());
        assertFalse(foundGenre.isPresent());
    }
}