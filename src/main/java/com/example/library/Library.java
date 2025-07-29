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