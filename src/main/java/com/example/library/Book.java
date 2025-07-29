package com.example.library;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a Book entity
 */
public class Book {
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private boolean isAvailable;
    private List<Integer> ratings;
    private List<String> reviews;

    // Constructor
    public Book(String title, String author, String isbn, int publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.isAvailable = true;
        this.ratings = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    /**
     * Add a rating (on a scale of 1-5)
     */
    public boolean addRating(int rating, String review) {
        if (rating < 1 || rating > 5) {
            return false;
        }
        
        ratings.add(rating);
        if (review != null && !review.trim().isEmpty()) {
            reviews.add(review.trim());
        } else {
            reviews.add("No written review");
        }
        return true;
    }

    /**
     * Calculate average rating
     */
    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        
        return ratings.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Number of ratings
     */
    public int getRatingCount() {
        return ratings.size();
    }

    /**
     * All ratings and reviews
     */
    public List<String> getAllReviews() {
        List<String> allReviews = new ArrayList<>();
        for (int i = 0; i < ratings.size(); i++) {
            String review = String.format("Rating: %d/5 - %s", 
                ratings.get(i), 
                i < reviews.size() ? reviews.get(i) : "No review"
            );
            allReviews.add(review);
        }
        return allReviews;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Setters
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // toString method
    @Override
    public String toString() {
        String ratingInfo = getRatingCount() > 0 ? 
            String.format(", avg rating: %.1f (%d ratings)", getAverageRating(), getRatingCount()) : 
            ", no ratings";
        
        return String.format("Book{title='%s', author='%s', isbn='%s', year=%d, available=%s%s}",
                title, author, isbn, publicationYear, isAvailable, ratingInfo);
    }

    // equals and hashCode for proper comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn); // Unique by ISBN
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}