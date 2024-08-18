package org.example.bookstore.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void bookGettersAndSetters() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Test Genre");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPrice(10.0);
        book.setQuantity(5);

        assertEquals(1L, book.getId());
        assertEquals("Test Book", book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(genre, book.getGenre());
        assertEquals(10.0, book.getPrice());
        assertEquals(5, book.getQuantity());
    }

    @Test
    void bookEqualsAndHashCode() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Test Genre");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Test Book");
        book1.setAuthor(author);
        book1.setGenre(genre);
        book1.setPrice(10.0);
        book1.setQuantity(5);

        Book book2 = new Book();
        book2.setId(1L);
        book2.setTitle("Test Book");
        book2.setAuthor(author);
        book2.setGenre(genre);
        book2.setPrice(10.0);
        book2.setQuantity(5);

        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void bookToString() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Test Genre");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPrice(10.0);
        book.setQuantity(5);

        String expected = "Book(id=1, title=Test Book, author=Author(id=1, name=Test Author, books=null), genre=Genre(id=1, name=Test Genre, books=null), price=10.0, quantity=5)";
        assertEquals(expected, book.toString());
    }
}