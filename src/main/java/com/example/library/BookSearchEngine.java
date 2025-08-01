package com.example.library;

import java.util.*;

/**
 * Advanced book search engine with relevance-based scoring
 * 
 * This class provides sophisticated search functionality for books
 * including relevance scoring and era-based filtering.
 */
public class BookSearchEngine {
    
    // Relevance score constants
    private static final int EXACT_TITLE_MATCH_SCORE = 10;
    private static final int EXACT_AUTHOR_MATCH_SCORE = 8;
    private static final int TITLE_CONTAINS_SCORE = 5;
    private static final int AUTHOR_CONTAINS_SCORE = 3;
    private static final int MODERN_BOOK_BONUS = 2;
    private static final int AVAILABLE_BONUS = 1;
    private static final int MINIMUM_RELEVANCE_SCORE = 3;
    
    // Era constants
    private static final int MODERN_BOOK_THRESHOLD = 2000;
    
    /**
     * Book era enumeration for filtering
     */
    public enum BookEra {
        NEW(2010, Integer.MAX_VALUE, "New books (after 2010)"),
        OLD(Integer.MIN_VALUE, 1980, "Old books (before 1980)"),
        CLASSIC(Integer.MIN_VALUE, 1950, "Classic books (before 1950)");
        
        private final int minYear;
        private final int maxYear;
        private final String description;
        
        BookEra(int minYear, int maxYear, String description) {
            this.minYear = minYear;
            this.maxYear = maxYear;
            this.description = description;
        }
        
        public boolean matches(int year) {
            return year >= minYear && year < maxYear;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Search books with relevance-based scoring
     * 
     * @param books List of books to search in (must not be null)
     * @param query Search query (must not be null or empty)
     * @return List of books sorted by relevance score (descending)
     */
    public static List<Book> search(List<Book> books, String query) {
        // Input validation
        if (books == null || query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Book> results = new ArrayList<>();
        String cleanQuery = query.trim();
        
        // Filter books by minimum relevance score
        for (Book book : books) {
            int score = calculateRelevanceScore(book, cleanQuery);
            if (score >= MINIMUM_RELEVANCE_SCORE) {
                results.add(book);
            }
        }
        
        // Sort by relevance score (descending)
        results.sort((book1, book2) -> Integer.compare(
            calculateRelevanceScore(book2, cleanQuery),
            calculateRelevanceScore(book1, cleanQuery)
        ));
        
        return results;
    }
    
    /**
     * Calculate relevance score for a book based on search query
     * 
     * Scoring system:
     * - Exact title match: 10 points
     * - Exact author match: 8 points  
     * - Title contains query: 5 points
     * - Author contains query: 3 points
     * - Modern book (after 2000): +2 points
     * - Available book: +1 point
     * 
     * @param book Book to score
     * @param query Search query
     * @return Relevance score (higher = more relevant)
     */
    private static int calculateRelevanceScore(Book book, String query) {
        int score = 0;
        String queryLower = query.toLowerCase();
        String titleLower = book.getTitle().toLowerCase();
        String authorLower = book.getAuthor().toLowerCase();
        
        // Exact matches have highest priority
        if (titleLower.equals(queryLower)) {
            score += EXACT_TITLE_MATCH_SCORE;
        } else if (titleLower.contains(queryLower)) {
            score += TITLE_CONTAINS_SCORE;
        }
        
        if (authorLower.equals(queryLower)) {
            score += EXACT_AUTHOR_MATCH_SCORE;
        } else if (authorLower.contains(queryLower)) {
            score += AUTHOR_CONTAINS_SCORE;
        }
        
        // Bonus points for modern and available books
        if (book.getPublicationYear() > MODERN_BOOK_THRESHOLD) {
            score += MODERN_BOOK_BONUS;
        }
        
        if (book.isAvailable()) {
            score += AVAILABLE_BONUS;
        }
        
        return score;
    }
    
    /**
     * Filter books by era
     * 
     * @param books List of books to filter
     * @param era Era to filter by
     * @return List of books matching the era criteria
     */
    public static List<Book> filterBooksByEra(List<Book> books, BookEra era) {
        if (books == null || era == null) {
            return new ArrayList<>();
        }
        
        List<Book> filtered = new ArrayList<>();
        for (Book book : books) {
            if (era.matches(book.getPublicationYear())) {
                filtered.add(book);
            }
        }
        
        return filtered;
    }
    
    /**
     * Get all available eras with their descriptions
     * 
     * @return Map of era to description
     */
    public static Map<BookEra, String> getAvailableEras() {
        Map<BookEra, String> eras = new LinkedHashMap<>();
        for (BookEra era : BookEra.values()) {
            eras.put(era, era.getDescription());
        }
        return eras;
    }
}
