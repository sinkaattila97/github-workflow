# 2. Branch-ek és Új Funkciók Fejlesztése

## 2.1 Branch Stratégia Megértése

A Git branch-ek lehetővé teszik, hogy párhuzamosan dolgozz különböző funkciókon anélkül, hogy befolyásolnád a fő kódot.

### Általános Branch Nevezési Konvenciók:
- `feature/funkció-neve` - új funkciók
- `bugfix/hiba-neve` - hibák javítása
- `hotfix/kritikus-hiba` - sürgős javítások
- `docs/dokumentáció` - dokumentáció frissítések

## 2.2 Első Feature Branch: Könyv Kölcsönzés

### Branch létrehozása és váltás:

```powershell
# Győződj meg róla, hogy a main branch-ben vagy
git checkout main

# Frissítsd a helyi main branch-et
git pull origin main

# Új feature branch létrehozása
git checkout -b feature/book-borrowing

# Ellenőrizd, hogy a megfelelő branch-ben vagy
git branch
```

### 2.2.1 Library.java Módosítása

Adjuk hozzá a kölcsönzési funkcionalitást. Módosítsd a `src\main\java\com\example\library\Library.java` fájlt:

```java
// Új metódusok hozzáadása a Library osztályhoz (a meglévő metódusok után):

    /**
     * Könyv kölcsönzése ISBN alapján
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
        return false; // Nem található vagy már kölcsönzött
    }

    /**
     * Könyv visszahozása ISBN alapján
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
        return false; // Nem található vagy már elérhető
    }

    /**
     * Könyv keresése ISBN alapján
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
     * Kölcsönzött könyvek listája
     */
    public List<Book> getBorrowedBooks() {
        return books.stream()
                .filter(book -> !book.isAvailable())
                .collect(Collectors.toList());
    }
```

### 2.2.2 LibraryApp.java Frissítése

Módosítsd a `src\main\java\com\example\library\LibraryApp.java` fájlt:

```java
// Cseréld ki a showMenu() metódust:
    private void showMenu() {
        System.out.println("Mit szeretnél csinálni?");
        System.out.println("1. Összes könyv listázása");
        System.out.println("2. Keresés cím alapján");
        System.out.println("3. Keresés szerző alapján");
        System.out.println("4. Új könyv hozzáadása");
        System.out.println("5. Könyv kölcsönzése");
        System.out.println("6. Könyv visszahozása");
        System.out.println("7. Kölcsönzött könyvek listája");
        System.out.println("8. Kilépés");
        System.out.print("Választásod: ");
    }

// Frissítsd a run() metódust switch részét:
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
                    System.out.println("Köszönjük a látogatást!");
                    return;
                default:
                    System.out.println("Érvénytelen választás!");
            }

// Új metódusok hozzáadása a LibraryApp osztályhoz:
    private void borrowBook() {
        System.out.print("Add meg a kölcsönözni kívánt könyv ISBN-jét: ");
        String isbn = scanner.nextLine();
        
        Book book = library.findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Nem található könyv ezzel az ISBN-nel.");
            return;
        }
        
        if (!book.isAvailable()) {
            System.out.println("A könyv már ki van kölcsönözve.");
            return;
        }
        
        if (library.borrowBook(isbn)) {
            System.out.println("Könyv sikeresen kikölcsönözve: " + book.getTitle());
        } else {
            System.out.println("Hiba történt a kölcsönzés során.");
        }
    }

    private void returnBook() {
        System.out.print("Add meg a visszahozni kívánt könyv ISBN-jét: ");
        String isbn = scanner.nextLine();
        
        Book book = library.findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Nem található könyv ezzel az ISBN-nel.");
            return;
        }
        
        if (book.isAvailable()) {
            System.out.println("Ez a könyv jelenleg is elérhető.");
            return;
        }
        
        if (library.returnBook(isbn)) {
            System.out.println("Könyv sikeresen visszahozva: " + book.getTitle());
        } else {
            System.out.println("Hiba történt a visszahozás során.");
        }
    }

    private void listBorrowedBooks() {
        List<Book> borrowedBooks = library.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("Jelenleg nincsenek kikölcsönzött könyvek.");
            return;
        }
        
        System.out.println("\nKikölcsönzött könyvek:");
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
```

### 2.2.3 Commit a Feature Branch-en

```powershell
# Fájlok hozzáadása
git add .

# Commit a változtatásokkal
git commit -m "Feature: Könyv kölcsönzési rendszer hozzáadása

- Kölcsönzés ISBN alapján
- Könyv visszahozási funkció  
- Kölcsönzött könyvek listázása
- Könyv keresés ISBN alapján
- Frissített felhasználói felület új opciókkal"

# Feature branch push-olása GitHub-ra
git push -u origin feature/book-borrowing
```

## 2.3 Második Feature Branch: Könyv Értékelések

### Új branch létrehozása a main-ről:

```powershell
# Váltás vissza main-re
git checkout main

# Új feature branch
git checkout -b feature/book-ratings
```

### 2.3.1 Book.java Módosítása Értékelésekkel

Adjunk hozzá értékelési rendszert. Módosítsd a `Book.java` fájlt:

```java
// Új mezők hozzáadása a Book osztályhoz (a meglévők után):
    private List<Integer> ratings;
    private List<String> reviews;

// Konstruktor módosítása:
    public Book(String title, String author, String isbn, int publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.isAvailable = true;
        this.ratings = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

// Új metódusok hozzáadása:
    /**
     * Értékelés hozzáadása (1-5 skálán)
     */
    public boolean addRating(int rating, String review) {
        if (rating < 1 || rating > 5) {
            return false;
        }
        
        ratings.add(rating);
        if (review != null && !review.trim().isEmpty()) {
            reviews.add(review.trim());
        } else {
            reviews.add("Nincs írott értékelés");
        }
        return true;
    }

    /**
     * Átlagos értékelés számítása
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
     * Értékelések száma
     */
    public int getRatingCount() {
        return ratings.size();
    }

    /**
     * Összes értékelés és vélemény
     */
    public List<String> getAllReviews() {
        List<String> allReviews = new ArrayList<>();
        for (int i = 0; i < ratings.size(); i++) {
            String review = String.format("Értékelés: %d/5 - %s", 
                ratings.get(i), 
                i < reviews.size() ? reviews.get(i) : "Nincs vélemény"
            );
            allReviews.add(review);
        }
        return allReviews;
    }

// toString metódus frissítése:
    @Override
    public String toString() {
        String ratingInfo = getRatingCount() > 0 ? 
            String.format(", avg rating: %.1f (%d értékelés)", getAverageRating(), getRatingCount()) : 
            ", nincs értékelés";
        
        return String.format("Book{title='%s', author='%s', isbn='%s', year=%d, available=%s%s}",
                title, author, isbn, publicationYear, isAvailable, ratingInfo);
    }
```

Ne felejts el hozzáadni az import-ot a fájl tetejére:
```java
import java.util.ArrayList;
import java.util.List;
```

### 2.3.2 LibraryApp.java Frissítése Értékelésekkel

```java
// showMenu() metódus frissítése:
    private void showMenu() {
        System.out.println("Mit szeretnél csinálni?");
        System.out.println("1. Összes könyv listázása");
        System.out.println("2. Keresés cím alapján");
        System.out.println("3. Keresés szerző alapján");
        System.out.println("4. Új könyv hozzáadása");
        System.out.println("5. Könyv kölcsönzése");
        System.out.println("6. Könyv visszahozása");
        System.out.println("7. Kölcsönzött könyvek listája");
        System.out.println("8. Könyv értékelése");
        System.out.println("9. Könyv értékeléseinek megtekintése");
        System.out.println("10. Kilépés");
        System.out.print("Választásod: ");
    }

// Switch-case frissítése:
                case 8:
                    rateBook();
                    break;
                case 9:
                    viewBookRatings();
                    break;
                case 10:
                    System.out.println("Köszönjük a látogatást!");
                    return;

// Új metódusok:
    private void rateBook() {
        System.out.print("Add meg az értékelni kívánt könyv ISBN-jét: ");
        String isbn = scanner.nextLine();
        
        Book book = library.findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Nem található könyv ezzel az ISBN-nel.");
            return;
        }
        
        System.out.println("Könyv: " + book.getTitle() + " - " + book.getAuthor());
        System.out.print("Értékelés (1-5): ");
        
        try {
            int rating = Integer.parseInt(scanner.nextLine());
            if (rating < 1 || rating > 5) {
                System.out.println("Az értékelés 1 és 5 között kell legyen!");
                return;
            }
            
            System.out.print("Vélemény (opcionális): ");
            String review = scanner.nextLine();
            
            if (book.addRating(rating, review)) {
                System.out.println("Értékelés sikeresen hozzáadva!");
            } else {
                System.out.println("Hiba történt az értékelés hozzáadása során.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Érvénytelen értékelés formátum!");
        }
    }

    private void viewBookRatings() {
        System.out.print("Add meg a könyv ISBN-jét: ");
        String isbn = scanner.nextLine();
        
        Book book = library.findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("Nem található könyv ezzel az ISBN-nel.");
            return;
        }
        
        System.out.println("\n=== " + book.getTitle() + " ===");
        System.out.println("Szerző: " + book.getAuthor());
        
        if (book.getRatingCount() == 0) {
            System.out.println("Ennek a könyvnek még nincsenek értékelései.");
            return;
        }
        
        System.out.printf("Átlagos értékelés: %.1f/5 (%d értékelés)\n", 
            book.getAverageRating(), book.getRatingCount());
        
        System.out.println("\nÖsszes értékelés:");
        List<String> reviews = book.getAllReviews();
        for (int i = 0; i < reviews.size(); i++) {
            System.out.println((i + 1) + ". " + reviews.get(i));
        }
    }
```

### 2.3.3 Commit az Értékelési Feature-re

```powershell
# Ellenőrzés
git status

# Fájlok hozzáadása
git add .

# Commit
git commit -m "Feature: Könyv értékelési rendszer

- Értékelés hozzáadása 1-5 skálán
- Vélemények írása könyvekhez
- Átlagos értékelés számítása
- Összes értékelés megtekintése
- Frissített toString metódus az értékelésekkel"

# Push
git push -u origin feature/book-ratings
```

## 2.4 Tesztelés

### Mindkét funkcio tesztelése külön-külön:

```powershell
# Book-borrowing branch tesztelése
git checkout feature/book-borrowing
javac -d . src/main/java/com/example/library/*.java
java com.example.library.LibraryApp

# Book-ratings branch tesztelése  
git checkout feature/book-ratings
javac -d . src/main/java/com/example/library/*.java
java com.example.library.LibraryApp
```

### Konfliktusos helyzet létrehozása (tanulási célból)

Ez egy szándékos konfliktus lesz a két branch között, hogy megtanuld kezelni őket.

```powershell
# Váltás a book-borrowing branch-re
git checkout feature/book-borrowing

# Módosítsuk a LibraryApp.java-t - változtassuk meg a welcome üzenetet
```

Módosítsd a `run()` metódusban a welcome üzenetet:

```java
public void run() {
    System.out.println("Üdvözöl a " + library.getName() + " - Kölcsönzési Rendszerrel!");
    // ... többi kód változatlan
}
```

```powershell
git add .
git commit -m "Update: Kölcsönzési rendszer hangsúlyozása a welcome üzenetben"
git push origin feature/book-borrowing
```

Most váltás a másik branch-re és ott is változtatás:

```powershell
git checkout feature/book-ratings

# Módosítsuk ugyanazt a sort másként
```

Módosítsd a `run()` metódusban a welcome üzenetet:

```java
public void run() {
    System.out.println("Üdvözöl a " + library.getName() + " - Értékelési Rendszerrel!");
    // ... többi kód változatlan
}
```

```powershell
git add .
git commit -m "Update: Értékelési rendszer hangsúlyozása a welcome üzenetben"
git push origin feature/book-ratings
```

## Következő lépések

Most hogy van két feature branch-ed különböző funkciókkal és egy szándékos konfliktussal, továbbléphetünk a pull request-ek létrehozására. Folytasd a [03-PULL-REQUEST.md](03-PULL-REQUEST.md) dokumentummal!
