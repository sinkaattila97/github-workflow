# 1. Projekt Setup és Első Commit

## 1.1 GitHub Repository Létrehozása

1. **Menj a GitHub-ra** (https://github.com)
2. **Jelentkezz be** a fiókodba
3. **Kattints a zöld "New" gombra** (vagy a "+" ikonra és "New repository")
4. **Repository beállítások:**
   - Repository name: `github-workflow-learning`
   - Description: `Java projekt GitHub workflow tanulásához`
   - Public vagy Private: `Public` (hogy mások is segíteni tudjanak)
   - Initialize with README: `NE pipáld be!` (mi magunk hozzuk létre)
   - Add .gitignore: `Java`
   - Choose a license: `MIT License`
5. **Kattints a "Create repository" gombra**

## 1.2 Helyi Projekt Létrehozása

### Git Repository Inicializálása

Nyiss egy terminált/PowerShell-t és navigálj a kívánt mappába:

```powershell
# Navigálj a Documents mappába (vagy ahol szeretnéd)
cd "C:\Users\[felhasználónév]\Documents"

# Hozz létre egy mappát a projektnek
mkdir github-workflow-learning
cd github-workflow-learning

# Git repository inicializálása
git init

# GitHub remote hozzáadása (cseréld ki a [felhasználónév]-et!)
git remote add origin https://github.com/[felhasználónév]/github-workflow-learning.git
```

### Projekt Struktúra Létrehozása

```powershell
# Hozd létre a mappa struktúrát
mkdir -p src\main\java\com\example\library
mkdir -p src\test\java\com\example\library
```

## 1.3 Alapvető Fájlok Létrehozása

### 1.3.1 Book.java - Könyv modell osztály

Hozd létre: `src\main\java\com\example\library\Book.java`

```java
package com.example.library;

/**
 * Könyv entitást reprezentáló osztály
 */
public class Book {
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private boolean isAvailable;

    // Konstruktor
    public Book(String title, String author, String isbn, int publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.isAvailable = true; // Alapértelmezetten elérhető
    }

    // Getterek
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

    // Setterek
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // toString metódus
    @Override
    public String toString() {
        return String.format("Book{title='%s', author='%s', isbn='%s', year=%d, available=%s}",
                title, author, isbn, publicationYear, isAvailable);
    }

    // equals és hashCode a megfelelő összehasonlításhoz
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn); // ISBN alapján egyedi
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
```

### 1.3.2 Library.java - Könyvtár kezelő osztály

Hozd létre: `src\main\java\com\example\library\Library.java`

```java
package com.example.library;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Könyvtár kezelő osztály
 */
public class Library {
    private List<Book> books;
    private String name;

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    /**
     * Könyv hozzáadása a könyvtárhoz
     */
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }
        
        // Ellenőrizzük, hogy már van-e ilyen ISBN-nel könyv
        if (books.contains(book)) {
            return false; // Már létezik
        }
        
        return books.add(book);
    }

    /**
     * Összes könyv lekérése
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books); // Védelmi másolat
    }

    /**
     * Könyv keresése cím alapján
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
     * Könyv keresése szerző alapján
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
     * Elérhető könyvek száma
     */
    public int getAvailableBooksCount() {
        return (int) books.stream()
                .filter(Book::isAvailable)
                .count();
    }

    /**
     * Összes könyv száma
     */
    public int getTotalBooksCount() {
        return books.size();
    }

    /**
     * Könyvtár neve
     */
    public String getName() {
        return name;
    }
}
```

### 1.3.3 LibraryApp.java - Főprogram

Hozd létre: `src\main\java\com\example\library\LibraryApp.java`

```java
package com.example.library;

import java.util.List;
import java.util.Scanner;

/**
 * Könyvtár alkalmazás főosztálya
 */
public class LibraryApp {
    private Library library;
    private Scanner scanner;

    public LibraryApp() {
        this.library = new Library("Városi Könyvtár");
        this.scanner = new Scanner(System.in);
        
        // Néhány példa könyv hozzáadása
        initializeSampleBooks();
    }

    private void initializeSampleBooks() {
        library.addBook(new Book("A Gyűrűk Ura", "J.R.R. Tolkien", "978-0261103573", 1954));
        library.addBook(new Book("Harry Potter és a bölcsek köve", "J.K. Rowling", "978-0747532699", 1997));
        library.addBook(new Book("1984", "George Orwell", "978-0451524935", 1949));
        library.addBook(new Book("A kis herceg", "Antoine de Saint-Exupéry", "978-0156012195", 1943));
    }

    public void run() {
        System.out.println("Üdvözöl a " + library.getName() + "!");
        System.out.println("Összes könyv: " + library.getTotalBooksCount());
        System.out.println("Elérhető könyvek: " + library.getAvailableBooksCount());
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
                    System.out.println("Köszönjük a látogatást!");
                    return;
                default:
                    System.out.println("Érvénytelen választás!");
            }
            System.out.println();
        }
    }

    private void showMenu() {
        System.out.println("Mit szeretnél csinálni?");
        System.out.println("1. Összes könyv listázása");
        System.out.println("2. Keresés cím alapján");
        System.out.println("3. Keresés szerző alapján");
        System.out.println("4. Új könyv hozzáadása");
        System.out.println("5. Kilépés");
        System.out.print("Választásod: ");
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
            System.out.println("Nincsenek könyvek a könyvtárban.");
            return;
        }
        
        System.out.println("\nÖsszes könyv:");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("%d. %s - %s (%d) [%s]\n", 
                i + 1, 
                book.getTitle(), 
                book.getAuthor(), 
                book.getPublicationYear(),
                book.isAvailable() ? "Elérhető" : "Kölcsönzött"
            );
        }
    }

    private void searchByTitle() {
        System.out.print("Add meg a keresett címet: ");
        String title = scanner.nextLine();
        
        List<Book> books = library.findBooksByTitle(title);
        if (books.isEmpty()) {
            System.out.println("Nem találtunk könyvet ezzel a címmel.");
            return;
        }
        
        System.out.println("\nTalálatok:");
        books.forEach(book -> System.out.println("- " + book.getTitle() + " - " + book.getAuthor()));
    }

    private void searchByAuthor() {
        System.out.print("Add meg a keresett szerzőt: ");
        String author = scanner.nextLine();
        
        List<Book> books = library.findBooksByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("Nem találtunk könyvet ettől a szerzőtől.");
            return;
        }
        
        System.out.println("\nTalálatok:");
        books.forEach(book -> System.out.println("- " + book.getTitle() + " - " + book.getAuthor()));
    }

    private void addNewBook() {
        System.out.println("Új könyv hozzáadása:");
        System.out.print("Cím: ");
        String title = scanner.nextLine();
        System.out.print("Szerző: ");
        String author = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Kiadási év: ");
        
        try {
            int year = Integer.parseInt(scanner.nextLine());
            Book newBook = new Book(title, author, isbn, year);
            
            if (library.addBook(newBook)) {
                System.out.println("Könyv sikeresen hozzáadva!");
            } else {
                System.out.println("A könyv már létezik a könyvtárban (ugyanaz az ISBN).");
            }
        } catch (NumberFormatException e) {
            System.out.println("Érvénytelen év formátum!");
        }
    }

    public static void main(String[] args) {
        LibraryApp app = new LibraryApp();
        app.run();
    }
}
```

## 1.4 Első Commit és Push

### Git konfigurálása (ha még nem tetted meg):

```powershell
git config --global user.name "A Te Neved"
git config --global user.email "a.te.email@example.com"
```

### Fájlok hozzáadása és commit:

```powershell
# Ellenőrizd a fájlokat
git status

# Minden fájl hozzáadása
git add .

# Első commit
git commit -m "Initial commit: Alapvető könyvtár kezelő alkalmazás

- Book modell osztály ISBN alapú egyediséggel
- Library könyvtár kezelő osztály
- LibraryApp interaktív felhasználói felület
- Alapvető CRUD műveletek könyvekhez
- Keresés cím és szerző alapján"

# Push GitHub-ra
git push -u origin main
```

## 1.5 Ellenőrzés

1. **Menj a GitHub repository-dba** böngészőben
2. **Ellenőrizd, hogy minden fájl feltöltődött**
3. **Nézd meg a commit-ot** - kattints a commit üzenetre
4. **Próbáld ki a kódot helyben:**

```powershell
# Fordítás
javac -d . src/main/java/com/example/library/*.java

# Futtatás
java com.example.library.LibraryApp
```

## Következő lépés

Ha minden rendben van, folytasd a [02-BRANCH-ES-FEATURES.md](02-BRANCH-ES-FEATURES.md) dokumentummal!

## Gyakori Hibák és Megoldások

### "Permission denied" hiba push-nál
- **Megoldás:** Personal Access Token használata jelszó helyett a GitHub-on

### Fájlstruktúra hibák
- **Ellenőrizd:** A Java fájlok a megfelelő package struktúrában vannak-e

### Compilation hibák
- **Ellenőrizd:** Java 11+ telepítve van-e
- **Használd:** `java -version` parancsot az ellenőrzéshez
