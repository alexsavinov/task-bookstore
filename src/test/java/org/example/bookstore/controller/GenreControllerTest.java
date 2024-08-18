package org.example.bookstore.controller;

import org.example.bookstore.model.Genre;
import org.example.bookstore.service.GenreService;
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

class GenreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GenreService genreService;

    @InjectMocks
    private GenreController genreController;

    private Genre genre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();

        genre = new Genre();
        genre.setId(1L);
        genre.setName("Test Genre");
    }

    @Test
    void getAllGenresReturnsListOfGenres() throws Exception {
        when(genreService.getAllGenres()).thenReturn(Arrays.asList(genre));
        mockMvc.perform(get("/api/genres")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Genre"));
        verify(genreService, times(1)).getAllGenres();
    }

    @Test
    void getGenreByIdReturnsGenreIfExists() throws Exception {
        when(genreService.getGenreById(1L)).thenReturn(Optional.of(genre));
        mockMvc.perform(get("/api/genres/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Genre"));
        verify(genreService, times(1)).getGenreById(1L);
    }

    @Test
    void getGenreByIdReturnsNotFoundIfNotExists() throws Exception {
        when(genreService.getGenreById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/genres/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(genreService, times(1)).getGenreById(1L);
    }

    @Test
    void createGenreCreatesAndReturnsGenre() throws Exception {
        when(genreService.saveGenre(any(Genre.class))).thenReturn(genre);
        mockMvc.perform(post("/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Genre\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Genre"));
        verify(genreService, times(1)).saveGenre(any(Genre.class));
    }

    @Test
    void updateGenreUpdatesAndReturnsGenreIfExists() throws Exception {
        when(genreService.getGenreById(1L)).thenReturn(Optional.of(genre));
        when(genreService.saveGenre(any(Genre.class))).thenReturn(genre);
        mockMvc.perform(put("/api/genres/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Genre\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Genre"));
        verify(genreService, times(1)).getGenreById(1L);
        verify(genreService, times(1)).saveGenre(any(Genre.class));
    }

    @Test
    void updateGenreReturnsNotFoundIfNotExists() throws Exception {
        when(genreService.getGenreById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/genres/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Updated Genre\"}"))
                .andExpect(status().isNotFound());
        verify(genreService, times(1)).getGenreById(1L);
    }

    @Test
    void deleteGenreDeletesGenreIfExists() throws Exception {
        doNothing().when(genreService).deleteGenre(1L);
        mockMvc.perform(delete("/api/genres/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(genreService, times(1)).deleteGenre(1L);
    }
}