package org.example.bookstore.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    @Test
    void genreGettersAndSetters() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Test Genre");

        assertEquals(1L, genre.getId());
        assertEquals("Test Genre", genre.getName());
    }

    @Test
    void genreEqualsAndHashCode() {
        Genre genre1 = new Genre();
        genre1.setId(1L);
        genre1.setName("Test Genre");

        Genre genre2 = new Genre();
        genre2.setId(1L);
        genre2.setName("Test Genre");

        assertEquals(genre1, genre2);
        assertEquals(genre1.hashCode(), genre2.hashCode());
    }

    @Test
    void genreToString() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Test Genre");

        String expected = "Genre(id=1, name=Test Genre, books=null)";
        assertEquals(expected, genre.toString());
    }
}