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
        System.out.println("Welcome to the " + library.getName() + " - Book Borrowing System!");
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
                    borrowBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    listBorrowedBooks();
                    break;
                case 8:
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
        System.out.println("5. Borrow a book");
        System.out.println("6. Return a book");
        System.out.println("7. List borrowed books");
        System.out.println("8. Exit");
        System.out.print("Your choice: ");
    }

    private void borrowBook() {
        System.out.print("Enter the ISBN of the book you want to borrow: ");
        String isbn = scanner.nextLine();
        
        Book book = library.findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("No book found with this ISBN.");
            return;
        }
        
        if (!book.isAvailable()) {
            System.out.println("The book is already borrowed.");
            return;
        }
        
        if (library.borrowBook(isbn)) {
            System.out.println("Book successfully borrowed: " + book.getTitle());
        } else {
            System.out.println("An error occurred during borrowing.");
        }
    }

    private void returnBook() {
        System.out.print("Enter the ISBN of the book you want to return: ");
        String isbn = scanner.nextLine();
        
        Book book = library.findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("No book found with this ISBN.");
            return;
        }
        
        if (book.isAvailable()) {
            System.out.println("This book is already available.");
            return;
        }
        
        if (library.returnBook(isbn)) {
            System.out.println("Book successfully returned: " + book.getTitle());
        } else {
            System.out.println("An error occurred during return.");
        }
    }

    private void listBorrowedBooks() {
        List<Book> borrowedBooks = library.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("There are currently no borrowed books.");
            return;
        }
        
        System.out.println("\nBorrowed books:");
        for (int i = 0; i < borrowedBooks.size(); i++) {
            Book book = borrowedBooks.get(i);
            System.out.printf("%d. %s - %s (ISBN: %s)\n", 
                i + 1, 
                book.getTitle(), 
                book.getAuthor(),
                book.getIsbn()
            );
        }
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