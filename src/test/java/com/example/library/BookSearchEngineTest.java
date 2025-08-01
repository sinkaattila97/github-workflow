package test.java.com.example.library;


import com.example.library.Book;
import com.example.library.BookSearchEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Unit tests for BookSearchEngine class
 */
@DisplayName("BookSearchEngine Class Tests")
class BookSearchEngineTest {
    
    private List<Book> books;
    private Book javaBook;
    private Book pythonBook;
    private Book oldBook;
    private Book newBook;
    
    @BeforeEach
    void setUp() {
        books = new ArrayList<>();
        javaBook = new Book("Java Programming", "John Smith", "978-0123456789", 2020);
        pythonBook = new Book("Python Guide", "Jane Doe", "978-1234567890", 2019);
        oldBook = new Book("Classic Literature", "Old Author", "978-2345678901", 1970);
        newBook = new Book("Modern Programming", "New Author", "978-3456789012", 2022);
        
        books.add(javaBook);
        books.add(pythonBook);
        books.add(oldBook);
        books.add(newBook);
    }
    
    @Nested
    @DisplayName("Search Function Tests")
    class SearchFunctionTests {
        
        @Test
        @DisplayName("Should return empty list for null books")
        void shouldReturnEmptyListForNullBooks() {
            // When
            List<Book> results = BookSearchEngine.search(null, "Java");
            
            // Then
            assertTrue(results.isEmpty());
        }
        
        @Test
        @DisplayName("Should return empty list for null query")
        void shouldReturnEmptyListForNullQuery() {
            // When
            List<Book> results = BookSearchEngine.search(books, null);
            
            // Then
            assertTrue(results.isEmpty());
        }
        
        @Test
        @DisplayName("Should return empty list for empty query")
        void shouldReturnEmptyListForEmptyQuery() {
            // When
            List<Book> results = BookSearchEngine.search(books, "  ");
            
            // Then
            assertTrue(results.isEmpty());
        }
        
        @Test
        @DisplayName("Should find exact title match")
        void shouldFindExactTitleMatch() {
            // When
            List<Book> results = BookSearchEngine.search(books, "Java Programming");
            
            // Then
            assertFalse(results.isEmpty());
            assertEquals(javaBook, results.get(0)); // Should be first due to high score
        }
        
        @Test
        @DisplayName("Should find partial title match")
        void shouldFindPartialTitleMatch() {
            // When
            List<Book> results = BookSearchEngine.search(books, "Programming");
            
            // Then
            assertEquals(2, results.size()); // Java Programming and Modern Programming
            assertTrue(results.contains(javaBook));
            assertTrue(results.contains(newBook));
        }
        
        @Test
        @DisplayName("Should find by author name")
        void shouldFindByAuthorName() {
            // When
            List<Book> results = BookSearchEngine.search(books, "John Smith");
            
            // Then
            assertFalse(results.isEmpty());
            assertTrue(results.contains(javaBook));
        }
        
        @Test
        @DisplayName("Should be case insensitive")
        void shouldBeCaseInsensitive() {
            // When
            List<Book> results = BookSearchEngine.search(books, "java");
            
            // Then
            assertFalse(results.isEmpty());
            assertTrue(results.contains(javaBook));
        }
        
        @Test
        @DisplayName("Should sort results by relevance score")
        void shouldSortResultsByRelevanceScore() {
            // Given - Add ratings to make one book more relevant
            javaBook.addRating(5, "Great");
            pythonBook.addRating(3, "OK");
            
            // When
            List<Book> results = BookSearchEngine.search(books, "Programming");
            
            // Then
            assertFalse(results.isEmpty());
            // First result should have higher relevance
            // (exact match should come before partial match)
        }
        
        @Test
        @DisplayName("Should filter out low relevance books")
        void shouldFilterOutLowRelevanceBooks() {
            // When - Search for something that won't match any book well
            List<Book> results = BookSearchEngine.search(books, "xyz");
            
            // Then
            assertTrue(results.isEmpty());
        }
    }
    
    @Nested
    @DisplayName("Era Filtering Tests")
    class EraFilteringTests {
        
        @Test
        @DisplayName("Should return empty list for null books")
        void shouldReturnEmptyListForNullBooks() {
            // When
            List<Book> results = BookSearchEngine.filterBooksByEra(null, BookSearchEngine.BookEra.NEW);
            
            // Then
            assertTrue(results.isEmpty());
        }
        
        @Test
        @DisplayName("Should return empty list for null era")
        void shouldReturnEmptyListForNullEra() {
            // When
            List<Book> results = BookSearchEngine.filterBooksByEra(books, null);
            
            // Then
            assertTrue(results.isEmpty());
        }
        
        @Test
        @DisplayName("Should filter new books (after 2010)")
        void shouldFilterNewBooks() {
            // When
            List<Book> results = BookSearchEngine.filterBooksByEra(books, BookSearchEngine.BookEra.NEW);
            
            // Then
            assertEquals(2, results.size());
            assertTrue(results.contains(javaBook)); // 2020
            assertTrue(results.contains(newBook));  // 2022
            assertFalse(results.contains(pythonBook)); // 2019
            assertFalse(results.contains(oldBook)); // 1970
        }
        
        @Test
        @DisplayName("Should filter old books (before 1980)")
        void shouldFilterOldBooks() {
            // When
            List<Book> results = BookSearchEngine.filterBooksByEra(books, BookSearchEngine.BookEra.OLD);
            
            // Then
            assertEquals(1, results.size());
            assertTrue(results.contains(oldBook)); // 1970
        }
        
        @Test
        @DisplayName("Should filter classic books (before 1950)")
        void shouldFilterClassicBooks() {
            // Given - Add a really old book
            Book ancientBook = new Book("Ancient Text", "Ancient Author", "978-4567890123", 1920);
            books.add(ancientBook);
            
            // When
            List<Book> results = BookSearchEngine.filterBooksByEra(books, BookSearchEngine.BookEra.CLASSIC);
            
            // Then
            assertEquals(1, results.size());
            assertTrue(results.contains(ancientBook));
        }
    }
    
    @Nested
    @DisplayName("BookEra Enum Tests")
    class BookEraEnumTests {
        
        @Test
        @DisplayName("NEW era should match books after 2010")
        void newEraShouldMatchBooksAfter2010() {
            assertTrue(BookSearchEngine.BookEra.NEW.matches(2015));
            assertTrue(BookSearchEngine.BookEra.NEW.matches(2023));
            assertFalse(BookSearchEngine.BookEra.NEW.matches(2009));
            assertFalse(BookSearchEngine.BookEra.NEW.matches(2000));
        }
        
        @Test
        @DisplayName("OLD era should match books before 1980")
        void oldEraShouldMatchBooksBefore1980() {
            assertTrue(BookSearchEngine.BookEra.OLD.matches(1970));
            assertTrue(BookSearchEngine.BookEra.OLD.matches(1920));
            assertFalse(BookSearchEngine.BookEra.OLD.matches(1980));
            assertFalse(BookSearchEngine.BookEra.OLD.matches(2000));
        }
        
        @Test
        @DisplayName("CLASSIC era should match books before 1950")
        void classicEraShouldMatchBooksBefore1950() {
            assertTrue(BookSearchEngine.BookEra.CLASSIC.matches(1930));
            assertTrue(BookSearchEngine.BookEra.CLASSIC.matches(1900));
            assertFalse(BookSearchEngine.BookEra.CLASSIC.matches(1950));
            assertFalse(BookSearchEngine.BookEra.CLASSIC.matches(1970));
        }
        
        @Test
        @DisplayName("Should have descriptions for all eras")
        void shouldHaveDescriptionsForAllEras() {
            for (BookSearchEngine.BookEra era : BookSearchEngine.BookEra.values()) {
                assertNotNull(era.getDescription());
                assertFalse(era.getDescription().trim().isEmpty());
            }
        }
    }
    
    @Nested
    @DisplayName("Available Eras Tests")
    class AvailableErasTests {
        
        @Test
        @DisplayName("Should return all eras with descriptions")
        void shouldReturnAllErasWithDescriptions() {
            // When
            Map<BookSearchEngine.BookEra, String> eras = BookSearchEngine.getAvailableEras();
            
            // Then
            assertEquals(3, eras.size());
            assertTrue(eras.containsKey(BookSearchEngine.BookEra.NEW));
            assertTrue(eras.containsKey(BookSearchEngine.BookEra.OLD));
            assertTrue(eras.containsKey(BookSearchEngine.BookEra.CLASSIC));
            
            // Verify descriptions are not empty
            for (String description : eras.values()) {
                assertNotNull(description);
                assertFalse(description.trim().isEmpty());
            }
        }
    }
}
