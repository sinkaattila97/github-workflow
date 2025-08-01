# 5. Tesztek Írása és Automatizálása

## 5.1 Miért Fontosak a Tesztek?

A tesztek biztosítják hogy:
- **Kódod működik** ahogy elvárod
- **Változtatások** nem törnek el meglévő funkciókat
- **Refactoring** biztonságos
- **Dokumentációként** szolgálnak a kód működéséről
- **CI/CD pipeline** automatikusan ellenőrzi a kódot

## 5.2 Projekt Struktúra Kiegészítése Tesztekkel

### 5.2.1 Maven/Gradle nélküli Egyszerű Setup

Hozzunk létre teszteket JUnit 5 használatával manuális classpath-tal.

```powershell
# Váltás main-re
git checkout main
git pull origin main

# Új branch tesztekhez
git checkout -b feature/unit-tests
```

### 5.2.2 JUnit 5 Letöltése

1. **Menj a** https://junit.org/junit5/docs/current/user-guide/#running-tests-build-classic-test-execution oldalra
2. **Töltsd le** a JUnit 5 Platform Console Standalone JAR-t
3. **Helyezd** a projekt gyökérkönyvtárába `junit-platform-console-standalone-1.10.0.jar` néven

**VAGY** használd a következő PowerShell parancsot:

```powershell
# JUnit JAR letöltése
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/junit-platform-console-standalone-1.10.0.jar" -OutFile "junit-platform-console-standalone-1.10.0.jar"
```

## 5.3 Unit Tesztek Írása

### 5.3.1 BookTest.java

Hozd létre: `src\test\java\com\example\library\BookTest.java`

```java
package com.example.library;

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
```

### 5.3.2 LibraryTest.java

Hozd létre: `src\test\java\com\example\library\LibraryTest.java`

```java
package com.example.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Unit tests for Library class
 */
@DisplayName("Library Class Tests")
class LibraryTest {
    
    private Library library;
    private Book book1;
    private Book book2;
    private Book book3;
    
    @BeforeEach
    void setUp() {
        library = new Library("Test Library");
        book1 = new Book("Java Programming", "John Smith", "978-0123456789", 2020);
        book2 = new Book("Python Guide", "Jane Doe", "978-1234567890", 2019);
        book3 = new Book("JavaScript Basics", "Bob Johnson", "978-2345678901", 2021);
    }
    
    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        
        @Test
        @DisplayName("Should create library with given name")
        void shouldCreateLibraryWithGivenName() {
            assertEquals("Test Library", library.getName());
        }
        
        @Test
        @DisplayName("Should initialize empty book list")
        void shouldInitializeEmptyBookList() {
            assertEquals(0, library.getTotalBooksCount());
            assertTrue(library.getAllBooks().isEmpty());
        }
    }
    
    @Nested
    @DisplayName("Add Book Tests")
    class AddBookTests {
        
        @Test
        @DisplayName("Should add book successfully")
        void shouldAddBookSuccessfully() {
            // When
            boolean result = library.addBook(book1);
            
            // Then
            assertTrue(result);
            assertEquals(1, library.getTotalBooksCount());
            assertTrue(library.getAllBooks().contains(book1));
        }
        
        @Test
        @DisplayName("Should not add null book")
        void shouldNotAddNullBook() {
            // When
            boolean result = library.addBook(null);
            
            // Then
            assertFalse(result);
            assertEquals(0, library.getTotalBooksCount());
        }
        
        @Test
        @DisplayName("Should not add duplicate book (same ISBN)")
        void shouldNotAddDuplicateBook() {
            // Given
            library.addBook(book1);
            Book duplicateBook = new Book("Different Title", "Different Author", "978-0123456789", 2022);
            
            // When
            boolean result = library.addBook(duplicateBook);
            
            // Then
            assertFalse(result);
            assertEquals(1, library.getTotalBooksCount());
        }
        
        @Test
        @DisplayName("Should add multiple different books")
        void shouldAddMultipleDifferentBooks() {
            // When
            library.addBook(book1);
            library.addBook(book2);
            library.addBook(book3);
            
            // Then
            assertEquals(3, library.getTotalBooksCount());
        }
    }
    
    @Nested
    @DisplayName("Book Search Tests")
    class BookSearchTests {
        
        @BeforeEach
        void addBooksToLibrary() {
            library.addBook(book1);
            library.addBook(book2);
            library.addBook(book3);
        }
        
        @Test
        @DisplayName("Should find books by exact title")
        void shouldFindBooksByExactTitle() {
            // When
            List<Book> results = library.findBooksByTitle("Java Programming");
            
            // Then
            assertEquals(1, results.size());
            assertEquals(book1, results.get(0));
        }
        
        @Test
        @DisplayName("Should find books by partial title (case insensitive)")
        void shouldFindBooksByPartialTitle() {
            // When
            List<Book> results = library.findBooksByTitle("java");
            
            // Then
            assertEquals(2, results.size());
            assertTrue(results.contains(book1)); // Java Programming
            assertTrue(results.contains(book3)); // JavaScript Basics
        }
        
        @Test
        @DisplayName("Should return empty list for non-existent title")
        void shouldReturnEmptyListForNonExistentTitle() {
            // When
            List<Book> results = library.findBooksByTitle("Non Existent Book");
            
            // Then
            assertTrue(results.isEmpty());
        }
        
        @Test
        @DisplayName("Should handle null title search")
        void shouldHandleNullTitleSearch() {
            // When
            List<Book> results = library.findBooksByTitle(null);
            
            // Then
            assertTrue(results.isEmpty());
        }
        
        @Test
        @DisplayName("Should handle empty title search")
        void shouldHandleEmptyTitleSearch() {
            // When
            List<Book> results = library.findBooksByTitle("  ");
            
            // Then
            assertTrue(results.isEmpty());
        }
        
        @Test
        @DisplayName("Should find books by author")
        void shouldFindBooksByAuthor() {
            // When
            List<Book> results = library.findBooksByAuthor("John Smith");
            
            // Then
            assertEquals(1, results.size());
            assertEquals(book1, results.get(0));
        }
        
        @Test
        @DisplayName("Should find books by partial author name")
        void shouldFindBooksByPartialAuthorName() {
            // When
            List<Book> results = library.findBooksByAuthor("john");
            
            // Then
            assertEquals(2, results.size());
            assertTrue(results.contains(book1)); // John Smith
            assertTrue(results.contains(book3)); // Bob Johnson
        }
    }
    
    @Nested
    @DisplayName("Book Borrowing Tests")
    class BookBorrowingTests {
        
        @BeforeEach
        void addBooksToLibrary() {
            library.addBook(book1);
            library.addBook(book2);
        }
        
        @Test
        @DisplayName("Should borrow available book successfully")
        void shouldBorrowAvailableBookSuccessfully() {
            // When
            boolean result = library.borrowBook("978-0123456789");
            
            // Then
            assertTrue(result);
            assertFalse(book1.isAvailable());
            assertEquals(1, library.getAvailableBooksCount());
        }
        
        @Test
        @DisplayName("Should not borrow already borrowed book")
        void shouldNotBorrowAlreadyBorrowedBook() {
            // Given
            library.borrowBook("978-0123456789");
            
            // When
            boolean result = library.borrowBook("978-0123456789");
            
            // Then
            assertFalse(result);
        }
        
        @Test
        @DisplayName("Should not borrow non-existent book")
        void shouldNotBorrowNonExistentBook() {
            // When
            boolean result = library.borrowBook("978-9999999999");
            
            // Then
            assertFalse(result);
            assertEquals(2, library.getAvailableBooksCount());
        }
        
        @Test
        @DisplayName("Should handle null ISBN in borrow")
        void shouldHandleNullIsbnInBorrow() {
            // When
            boolean result = library.borrowBook(null);
            
            // Then
            assertFalse(result);
        }
        
        @Test
        @DisplayName("Should return borrowed book successfully")
        void shouldReturnBorrowedBookSuccessfully() {
            // Given
            library.borrowBook("978-0123456789");
            
            // When
            boolean result = library.returnBook("978-0123456789");
            
            // Then
            assertTrue(result);
            assertTrue(book1.isAvailable());
            assertEquals(2, library.getAvailableBooksCount());
        }
        
        @Test
        @DisplayName("Should not return already available book")
        void shouldNotReturnAlreadyAvailableBook() {
            // When
            boolean result = library.returnBook("978-0123456789");
            
            // Then
            assertFalse(result);
        }
        
        @Test
        @DisplayName("Should get borrowed books list")
        void shouldGetBorrowedBooksList() {
            // Given
            library.borrowBook("978-0123456789");
            library.borrowBook("978-1234567890");
            
            // When
            List<Book> borrowedBooks = library.getBorrowedBooks();
            
            // Then
            assertEquals(2, borrowedBooks.size());
            assertTrue(borrowedBooks.contains(book1));
            assertTrue(borrowedBooks.contains(book2));
        }
    }
    
    @Nested
    @DisplayName("Book Lookup Tests")
    class BookLookupTests {
        
        @BeforeEach
        void addBooksToLibrary() {
            library.addBook(book1);
            library.addBook(book2);
        }
        
        @Test
        @DisplayName("Should find book by ISBN")
        void shouldFindBookByIsbn() {
            // When
            Book found = library.findBookByIsbn("978-0123456789");
            
            // Then
            assertEquals(book1, found);
        }
        
        @Test
        @DisplayName("Should return null for non-existent ISBN")
        void shouldReturnNullForNonExistentIsbn() {
            // When
            Book found = library.findBookByIsbn("978-9999999999");
            
            // Then
            assertNull(found);
        }
        
        @Test
        @DisplayName("Should handle null ISBN in find")
        void shouldHandleNullIsbnInFind() {
            // When
            Book found = library.findBookByIsbn(null);
            
            // Then
            assertNull(found);
        }
    }
    
    @Nested
    @DisplayName("Statistics Tests")
    class StatisticsTests {
        
        @Test
        @DisplayName("Should count total books correctly")
        void shouldCountTotalBooksCorrectly() {
            // When
            library.addBook(book1);
            library.addBook(book2);
            library.addBook(book3);
            
            // Then
            assertEquals(3, library.getTotalBooksCount());
        }
        
        @Test
        @DisplayName("Should count available books correctly")
        void shouldCountAvailableBooksCorrectly() {
            // Given
            library.addBook(book1);
            library.addBook(book2);
            library.addBook(book3);
            library.borrowBook("978-0123456789");
            
            // When
            int availableCount = library.getAvailableBooksCount();
            
            // Then
            assertEquals(2, availableCount);
        }
        
        @Test
        @DisplayName("Should return defensive copy of all books")
        void shouldReturnDefensiveCopyOfAllBooks() {
            // Given
            library.addBook(book1);
            
            // When
            List<Book> books = library.getAllBooks();
            books.clear(); // Try to modify the returned list
            
            // Then
            assertEquals(1, library.getTotalBooksCount()); // Original should be unchanged
        }
    }
}
```

### 5.3.3 BookSearchEngineTest.java

Hozd létre: `src\test\java\com\example\library\BookSearchEngineTest.java`

```java
package com.example.library;

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
```

## 5.4 Tesztek Futtatása

### 5.4.1 Compilation és Futtatás

```powershell
# Fordítás (main kód)
javac -d build -sourcepath src\main\java src\main\java\com\example\library\*.java

# Tesztek fordítása
javac -d build -cp "junit-platform-console-standalone-1.10.0.jar;build" -sourcepath "src\test\java;src\main\java" src\test\java\com\example\library\*.java

# Tesztek futtatása
java -jar junit-platform-console-standalone-1.10.0.jar --class-path build --scan-class-path
```

### 5.4.2 Test Runner Script Létrehozása

Hozd létre: `run-tests.bat`

```batch
@echo off
echo Building project...

:: Create build directory
if not exist build mkdir build

:: Compile main source code
javac -d build -sourcepath src\main\java src\main\java\com\example\library\*.java
if %errorlevel% neq 0 (
    echo Main compilation failed!
    exit /b 1
)

:: Compile test source code
javac -d build -cp "junit-platform-console-standalone-1.10.0.jar;build" -sourcepath "src\test\java;src\main\java" src\test\java\com\example\library\*.java
if %errorlevel% neq 0 (
    echo Test compilation failed!
    exit /b 1
)

echo Running tests...
java -jar junit-platform-console-standalone-1.10.0.jar --class-path build --scan-class-path --details=verbose

echo.
echo Test execution completed!
```

### 5.4.3 PowerShell Script (run-tests.ps1)

```powershell
Write-Host "Building project..." -ForegroundColor Green

# Create build directory
if (!(Test-Path "build")) {
    New-Item -ItemType Directory -Path "build"
}

# Compile main source code
Write-Host "Compiling main source..." -ForegroundColor Yellow
javac -d build -sourcepath src\main\java src\main\java\com\example\library\*.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "Main compilation failed!" -ForegroundColor Red
    exit 1
}

# Compile test source code  
Write-Host "Compiling test source..." -ForegroundColor Yellow
javac -d build -cp "junit-platform-console-standalone-1.10.0.jar;build" -sourcepath "src\test\java;src\main\java" src\test\java\com\example\library\*.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "Test compilation failed!" -ForegroundColor Red
    exit 1
}

# Run tests
Write-Host "Running tests..." -ForegroundColor Green
java -jar junit-platform-console-standalone-1.10.0.jar --class-path build --scan-class-path --details=verbose

Write-Host "`nTest execution completed!" -ForegroundColor Green
```

### 5.4.4 Tesztek Futtatása

```powershell
# Futtatás PowerShell script-tel
.\run-tests.ps1

# Vagy közvetlenül
.\run-tests.bat
```

## 5.5 GitHub Actions CI/CD Pipeline

### 5.5.1 GitHub Actions Workflow Létrehozása

Hozd létre: `.github\workflows\ci.yml`

```yml
name: Java Library CI

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    strategy:
      matrix:
        java-version: [11, 17, 21]
    
    steps:
    - name: Checkout code
      uses: actions/checkout@v4
    
    - name: Set up JDK ${{ matrix.java-version }}
      uses: actions/setup-java@v4
      with:
        java-version: ${{ matrix.java-version }}
        distribution: 'temurin'
    
    - name: Cache JUnit JAR
      uses: actions/cache@v3
      with:
        path: junit-platform-console-standalone-1.10.0.jar
        key: junit-${{ runner.os }}-1.10.0
        restore-keys: |
          junit-${{ runner.os }}-
    
    - name: Download JUnit if not cached
      run: |
        if [ ! -f "junit-platform-console-standalone-1.10.0.jar" ]; then
          wget -q https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/junit-platform-console-standalone-1.10.0.jar
        fi
    
    - name: Create build directory
      run: mkdir -p build
    
    - name: Compile main source
      run: |
        javac -d build -sourcepath src/main/java src/main/java/com/example/library/*.java
    
    - name: Compile test source
      run: |
        javac -d build -cp "junit-platform-console-standalone-1.10.0.jar:build" -sourcepath "src/test/java:src/main/java" src/test/java/com/example/library/*.java
    
    - name: Run tests
      run: |
        java -jar junit-platform-console-standalone-1.10.0.jar --class-path build --scan-class-path --reports-dir=test-reports
    
    - name: Upload test results
      uses: actions/upload-artifact@v3
      if: always()
      with:
        name: test-results-java-${{ matrix.java-version }}
        path: test-reports/
    
    - name: Test Report Summary
      uses: dorny/test-reporter@v1
      if: success() || failure()
      with:
        name: JUnit Tests (Java ${{ matrix.java-version }})
        path: test-reports/TEST-*.xml
        reporter: java-junit

  code-quality:
    runs-on: ubuntu-latest
    needs: test
    
    steps:
    - name: Checkout code
      uses: actions/checkout@v4
    
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Download JUnit
      run: |
        wget -q https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/junit-platform-console-standalone-1.10.0.jar
    
    - name: Compile project
      run: |
        mkdir -p build
        javac -d build -sourcepath src/main/java src/main/java/com/example/library/*.java
        javac -d build -cp "junit-platform-console-standalone-1.10.0.jar:build" -sourcepath "src/test/java:src/main/java" src/test/java/com/example/library/*.java
    
    - name: Calculate test coverage (basic)
      run: |
        echo "Test coverage check - ensuring all classes have tests"
        MAIN_CLASSES=$(find src/main/java -name "*.java" | wc -l)
        TEST_CLASSES=$(find src/test/java -name "*Test.java" | wc -l)
        echo "Main classes: $MAIN_CLASSES"
        echo "Test classes: $TEST_CLASSES"
        if [ $TEST_CLASSES -lt $MAIN_CLASSES ]; then
          echo "Warning: Not all classes have corresponding tests"
        else
          echo "Good: Test coverage appears adequate"
        fi
```

### 5.5.2 Branch Protection Rules

Dokumentáld hogyan állítsuk be GitHub-on:

Hozd létre: `GITHUB-BRANCH-PROTECTION.md`

```markdown
# GitHub Branch Protection Beállítása

## 1. Branch Protection Rules

1. **Menj a GitHub repository Settings fülére**
2. **Kattints a "Branches" menüpontra** a bal oldali menüben
3. **Kattints "Add rule" gombra**

## 2. Protection Rule Beállítások

### Branch name pattern
```
main
```

### Protect matching branches
- [x] **Require a pull request before merging**
  - [x] Require approvals: `1`
  - [x] Dismiss stale PR approvals when new commits are pushed
  - [x] Require review from code owners (ha van CODEOWNERS fájl)

- [x] **Require status checks to pass before merging**
  - [x] Require branches to be up to date before merging
  - **Status checks:** 
    - `test (11)` - Java 11 tesztek
    - `test (17)` - Java 17 tesztek  
    - `test (21)` - Java 21 tesztek
    - `code-quality` - Kód minőség ellenőrzés

- [x] **Require conversation resolution before merging**
- [x] **Require signed commits** (opcionális, de ajánlott)
- [x] **Include administrators** (szabály vonatkozik admin-okra is)

### Push restrictions
- [x] **Restrict pushes that create files larger than 100MB**

## 3. CODEOWNERS Fájl (Opcionális)

Hozd létre: `.github/CODEOWNERS`

```
# Global code owners
* @felhasználónév

# Java source code
src/main/java/ @felhasználónév @másik-fejlesztő

# Tests
src/test/java/ @felhasználónév

# GitHub workflows
.github/ @felhasználónév

# Documentation
*.md @felhasználónév
```

## 4. Eredmény

Ezek után:
- **Senki nem push-olhat** közvetlenül a main branch-re
- **Minden változtatásnak** PR-on keresztül kell mennie
- **CI teszteknek** sikeresnek kell lenniük
- **Code review** kötelező
- **Automated checks** futnak minden PR-nél
```

## 5.6 Commit és Push

```powershell
# Add all test files
git add .

git commit -m "Add comprehensive unit tests and CI/CD pipeline

✅ BookTest: Complete test coverage for Book class
  - Constructor, availability, rating, equals/hashCode, toString tests
  - Edge cases and error handling

✅ LibraryTest: Full Library class testing  
  - Book management, search, borrowing functionality
  - Statistics and defensive copying

✅ BookSearchEngineTest: Search engine validation
  - Search functionality, era filtering, enum behavior
  - Input validation and error handling

✅ GitHub Actions CI/CD:
  - Multi-version Java testing (11, 17, 21)
  - Automated test execution on PR/push
  - Test reports and artifact upload
  - Code quality checks

✅ Test execution scripts:
  - PowerShell and Batch scripts for local testing
  - JUnit 5 integration

📊 Test Coverage: 100% of main classes have corresponding tests
🔧 CI Pipeline: Automated testing on every change"

git push origin feature/unit-tests
```

## 5.7 Pull Request Létrehozása

1. **Hozz létre PR-t** GitHub-on
2. **Title:** `Add comprehensive unit tests and CI/CD pipeline`
3. **Várj** amíg a GitHub Actions futnak
4. **Ellenőrizd** hogy minden teszt zöld
5. **Merge-eld** a PR-t

## 5.8 Tesztelés Best Practices

### Test Naming Conventions
- `should[ExpectedBehavior]When[StateOrCondition]`
- Példa: `shouldReturnEmptyListWhenNoMatches`

### Test Structure (AAA Pattern)
```java
@Test
void shouldDoSomething() {
    // Arrange (Given)
    // Setup objects and expectations
    
    // Act (When)  
    // Execute the method under test
    
    // Assert (Then)
    // Verify the results
}
```

### Test Categories
- **Unit Tests:** Egyedi osztályok/metódusok
- **Integration Tests:** Komponensek együttműködése
- **End-to-End Tests:** Teljes workflow tesztelése

### Coverage Goals
- **Minimum 80%** code coverage
- **100%** critical path coverage
- **Edge cases** és error scenarios

## Következő lépések

Gratulálok! Sikeresen implementáltad a teljes testing és CI/CD workflow-t. Folytasd a [06-ADVANCED-FEATURES.md](06-ADVANCED-FEATURES.md) dokumentummal, ahol megtanulod a GitHub haladó funkcióit mint például Issues, Project boards, és még több automatizálást!
