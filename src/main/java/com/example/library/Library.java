package com.example.library;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Library management class
 */
public class Library {
    private List<Book> books;
    private String name;

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    /**
     * Add a book to the library
     */
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }
        
        // Check if a book with this ISBN already exists
        if (books.contains(book)) {
            return false; // Already exists
        }
        
        return books.add(book);
    }

    /**
     * Get all books
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books); // Defensive copy
    }

    /**
     * Find books by title
     */
    public List<Book> findBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Find books by author
     */
    public List<Book> findBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Number of available books
     */
    public int getAvailableBooksCount() {
        return (int) books.stream()
                .filter(Book::isAvailable)
                .count();
    }

    /**
     * Borrow a book by ISBN
     */
    public boolean borrowBook(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && book.isAvailable()) {
                book.setAvailable(false);
                return true;
            }
        }
        return false; // Not found or already borrowed
    }

    /**
     * Return a book by ISBN
     */
    public boolean returnBook(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && !book.isAvailable()) {
                book.setAvailable(true);
                return true;
            }
        }
        return false; // Not found or already available
    }

    /**
     * Find a book by ISBN
     */
    public Book findBookByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return null;
        }
        
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    /**
     * List of borrowed books
     */
    public List<Book> getBorrowedBooks() {
        return books.stream()
                .filter(book -> !book.isAvailable())
                .collect(Collectors.toList());
    }

    /**
     * Total number of books
     */
    public int getTotalBooksCount() {
        return books.size();
    }

    /**
     * Library name
     */
    public String getName() {
        return name;
    }
}