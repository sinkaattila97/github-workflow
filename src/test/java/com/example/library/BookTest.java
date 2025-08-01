package test.java.com.example.library;

//package com.example.library;

import com.example.library.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Book class
 */
@DisplayName("Book Class Tests")
class BookTest {
    
    private Book book;
    
    @BeforeEach
    void setUp() {
        book = new Book("Test Title", "Test Author", "978-0123456789", 2020);
    }
    
    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        
        @Test
        @DisplayName("Should create book with valid parameters")
        void shouldCreateBookWithValidParameters() {
            // Given & When
            Book newBook = new Book("Java Guide", "John Doe", "978-1234567890", 2023);
            
            // Then
            assertEquals("Java Guide", newBook.getTitle());
            assertEquals("John Doe", newBook.getAuthor());
            assertEquals("978-1234567890", newBook.getIsbn());
            assertEquals(2023, newBook.getPublicationYear());
            assertTrue(newBook.isAvailable());
            assertEquals(0, newBook.getRatingCount());
        }
        
        @Test
        @DisplayName("Should initialize book as available by default")
        void shouldInitializeBookAsAvailableByDefault() {
            assertTrue(book.isAvailable());
        }
        
        @Test
        @DisplayName("Should initialize empty ratings list")
        void shouldInitializeEmptyRatingsList() {
            assertEquals(0, book.getRatingCount());
            assertEquals(0.0, book.getAverageRating());
        }
    }
    
    @Nested
    @DisplayName("Availability Tests")
    class AvailabilityTests {
        
        @Test
        @DisplayName("Should set book as unavailable")
        void shouldSetBookAsUnavailable() {
            // When
            book.setAvailable(false);
            
            // Then
            assertFalse(book.isAvailable());
        }
        
        @Test
        @DisplayName("Should set book as available")
        void shouldSetBookAsAvailable() {
            // Given
            book.setAvailable(false);
            
            // When
            book.setAvailable(true);
            
            // Then
            assertTrue(book.isAvailable());
        }
    }
    
    @Nested
    @DisplayName("Rating Tests")
    class RatingTests {
        
        @Test
        @DisplayName("Should add valid rating successfully")
        void shouldAddValidRatingSuccessfully() {
            // When
            boolean result = book.addRating(4, "Great book!");
            
            // Then
            assertTrue(result);
            assertEquals(1, book.getRatingCount());
            assertEquals(4.0, book.getAverageRating());
        }
        
        @Test
        @DisplayName("Should reject rating below 1")
        void shouldRejectRatingBelow1() {
            // When
            boolean result = book.addRating(0, "Bad rating");
            
            // Then
            assertFalse(result);
            assertEquals(0, book.getRatingCount());
        }
        
        @Test
        @DisplayName("Should reject rating above 5")
        void shouldRejectRatingAbove5() {
            // When
            boolean result = book.addRating(6, "Too high rating");
            
            // Then
            assertFalse(result);
            assertEquals(0, book.getRatingCount());
        }
        
        @Test
        @DisplayName("Should calculate correct average rating")
        void shouldCalculateCorrectAverageRating() {
            // When
            book.addRating(5, "Excellent");
            book.addRating(3, "Good");
            book.addRating(4, "Very good");
            
            // Then
            assertEquals(3, book.getRatingCount());
            assertEquals(4.0, book.getAverageRating(), 0.01);
        }
        
        @Test
        @DisplayName("Should handle null review")
        void shouldHandleNullReview() {
            // When
            boolean result = book.addRating(4, null);
            
            // Then
            assertTrue(result);
            assertEquals(1, book.getRatingCount());
            assertTrue(book.getAllReviews().get(0).contains("Nincs írott értékelés"));
        }
        
        @Test
        @DisplayName("Should handle empty review")
        void shouldHandleEmptyReview() {
            // When
            boolean result = book.addRating(3, "   ");
            
            // Then
            assertTrue(result);
            assertTrue(book.getAllReviews().get(0).contains("Nincs írott értékelés"));
        }
        
        @Test
        @DisplayName("Should return all reviews correctly")
        void shouldReturnAllReviewsCorrectly() {
            // When
            book.addRating(5, "Amazing book!");
            book.addRating(3, "It was okay");
            
            // Then
            var reviews = book.getAllReviews();
            assertEquals(2, reviews.size());
            assertTrue(reviews.get(0).contains("5/5") && reviews.get(0).contains("Amazing book!"));
            assertTrue(reviews.get(1).contains("3/5") && reviews.get(1).contains("It was okay"));
        }
    }
    
    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsAndHashCodeTests {
        
        @Test
        @DisplayName("Should be equal when ISBN is same")
        void shouldBeEqualWhenIsbnIsSame() {
            // Given
            Book book1 = new Book("Title1", "Author1", "978-0123456789", 2020);
            Book book2 = new Book("Title2", "Author2", "978-0123456789", 2021);
            
            // Then
            assertEquals(book1, book2);
            assertEquals(book1.hashCode(), book2.hashCode());
        }
        
        @Test
        @DisplayName("Should not be equal when ISBN is different")
        void shouldNotBeEqualWhenIsbnIsDifferent() {
            // Given
            Book book1 = new Book("Same Title", "Same Author", "978-0123456789", 2020);
            Book book2 = new Book("Same Title", "Same Author", "978-9876543210", 2020);
            
            // Then
            assertNotEquals(book1, book2);
        }
        
        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            assertEquals(book, book);
        }
        
        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            assertNotEquals(null, book);
        }
        
        @Test
        @DisplayName("Should not be equal to different class")
        void shouldNotBeEqualToDifferentClass() {
            assertNotEquals("string", book);
        }
    }
    
    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {
        
        @Test
        @DisplayName("Should include all basic information")
        void shouldIncludeAllBasicInformation() {
            // When
            String result = book.toString();
            
            // Then
            assertTrue(result.contains("Test Title"));
            assertTrue(result.contains("Test Author"));
            assertTrue(result.contains("978-0123456789"));
            assertTrue(result.contains("2020"));
            assertTrue(result.contains("available=true"));
        }
        
        @Test
        @DisplayName("Should show no rating information when no ratings")
        void shouldShowNoRatingInformationWhenNoRatings() {
            // When
            String result = book.toString();
            
            // Then
            assertTrue(result.contains("nincs értékelés"));
        }
        
        @Test
        @DisplayName("Should show rating information when ratings exist")
        void shouldShowRatingInformationWhenRatingsExist() {
            // Given
            book.addRating(4, "Good");
            book.addRating(5, "Excellent");
            
            // When
            String result = book.toString();
            
            // Then
            assertTrue(result.contains("avg rating: 4.5"));
            assertTrue(result.contains("2 értékelés"));
        }
    }
}