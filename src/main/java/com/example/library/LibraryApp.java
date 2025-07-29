package com.example.library;

import java.util.List;
import java.util.Scanner;

/**
 * Main class for the library application
 */
public class LibraryApp {
    private Library library;
    private Scanner scanner;

    public LibraryApp() {
        this.library = new Library("City Library");
        this.scanner = new Scanner(System.in);
        
        // Add some sample books
        initializeSampleBooks();
    }

    private void initializeSampleBooks() {
        library.addBook(new Book("The Lord of the Rings", "J.R.R. Tolkien", "978-0261103573", 1954));
        library.addBook(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "978-0747532699", 1997));
        library.addBook(new Book("1984", "George Orwell", "978-0451524935", 1949));
        library.addBook(new Book("The Little Prince", "Antoine de Saint-Exupéry", "978-0156012195", 1943));
    }

    public void run() {
        System.out.println("Welcome to the " + library.getName() + "!");
        System.out.println("Total books: " + library.getTotalBooksCount());
        System.out.println("Available books: " + library.getAvailableBooksCount());
        System.out.println();

        while (true) {
            showMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    listAllBooks();
                    break;
                case 2:
                    searchByTitle();
                    break;
                case 3:
                    searchByAuthor();
                    break;
                case 4:
                    addNewBook();
                    break;
                case 5:
                    System.out.println("Thank you for visiting!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
            System.out.println();
        }
    }

    private void showMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1. List all books");
        System.out.println("2. Search by title");
        System.out.println("3. Search by author");
        System.out.println("4. Add a new book");
        System.out.println("5. Exit");
        System.out.print("Your choice: ");
    }

    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void listAllBooks() {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("There are no books in the library.");
            return;
        }
        
        System.out.println("\nAll books:");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("%d. %s - %s (%d) [%s]\n", 
                i + 1, 
                book.getTitle(), 
                book.getAuthor(), 
                book.getPublicationYear(),
                book.isAvailable() ? "Available" : "Borrowed"
            );
        }
    }

    private void searchByTitle() {
        System.out.print("Enter the title to search for: ");
        String title = scanner.nextLine();
        
        List<Book> books = library.findBooksByTitle(title);
        if (books.isEmpty()) {
            System.out.println("No books found with this title.");
            return;
        }
        
        System.out.println("\nResults:");
        books.forEach(book -> System.out.println("- " + book.getTitle() + " - " + book.getAuthor()));
    }

    private void searchByAuthor() {
        System.out.print("Enter the author to search for: ");
        String author = scanner.nextLine();
        
        List<Book> books = library.findBooksByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("No books found by this author.");
            return;
        }
        
        System.out.println("\nResults:");
        books.forEach(book -> System.out.println("- " + book.getTitle() + " - " + book.getAuthor()));
    }

    private void addNewBook() {
        System.out.println("Add a new book:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Publication year: ");
        
        try {
            int year = Integer.parseInt(scanner.nextLine());
            Book newBook = new Book(title, author, isbn, year);
            
            if (library.addBook(newBook)) {
                System.out.println("Book added successfully!");
            } else {
                System.out.println("The book already exists in the library (same ISBN).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid year format!");
        }
    }

    public static void main(String[] args) {
        LibraryApp app = new LibraryApp();
        app.run();
    }
}