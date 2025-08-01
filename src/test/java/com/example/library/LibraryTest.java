package test.java.com.example.library;

import com.example.library.Book;
import com.example.library.Library;
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