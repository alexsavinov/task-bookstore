package org.example.bookstore.service;

import org.example.bookstore.model.Genre;
import org.example.bookstore.repository.GenreRepository;
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

class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    private Genre genre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genre = new Genre();
        genre.setId(1L);
        genre.setName("Test Genre");
    }

    @Test
    void getAllGenresReturnsListOfGenres() {
        when(genreRepository.findAll()).thenReturn(Arrays.asList(genre));
        List<Genre> genres = genreService.getAllGenres();
        assertNotNull(genres);
        assertEquals(1, genres.size());
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void getGenreByIdReturnsGenreIfExists() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        Optional<Genre> foundGenre = genreService.getGenreById(1L);
        assertTrue(foundGenre.isPresent());
        assertEquals("Test Genre", foundGenre.get().getName());
        verify(genreRepository, times(1)).findById(1L);
    }

    @Test
    void getGenreByIdReturnsEmptyIfNotExists() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Genre> foundGenre = genreService.getGenreById(1L);
        assertFalse(foundGenre.isPresent());
        verify(genreRepository, times(1)).findById(1L);
    }

    @Test
    void saveGenreSavesAndReturnsGenre() {
        when(genreRepository.save(genre)).thenReturn(genre);
        Genre savedGenre = genreService.saveGenre(genre);
        assertNotNull(savedGenre);
        assertEquals("Test Genre", savedGenre.getName());
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    void deleteGenreDeletesGenre() {
        doNothing().when(genreRepository).deleteById(1L);
        genreService.deleteGenre(1L);
        verify(genreRepository, times(1)).deleteById(1L);
    }
}