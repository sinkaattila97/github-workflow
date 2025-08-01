# 4. Code Review Folyamat

## 4.1 Mi a Code Review?

A Code Review egy folyamat, ahol egy vagy több fejlesztő áttekinti a kód változtatásokat merge előtt. Célja:
- **Hibák korai felismerése**
- **Kód minőség javítása**
- **Tudásmegosztás** a csapaton belül
- **Coding standards** betartása
- **Biztonság** ellenőrzése

## 4.2 Code Review Szimuláció

Mivel egyedül dolgozol, mi fogjuk szimulálni a code review folyamatot. Készítünk egy új feature-t, majd "másik fejlesztőként" review-zzuk.

### 4.2.1 Új Feature Branch: Search Improvements

```powershell
# Váltás main-re és frissítés
git checkout main
git pull origin main

# Új feature branch
git checkout -b feature/search-improvements
```

### 4.2.2 "Problémás" Kód Írása (Szándékosan)

Hozzunk létre egy új fájlt: `src\main\java\com\example\library\BookSearchEngine.java`

```java
package com.example.library;

import java.util.*;

/**
 * Advanced book search engine
 */
public class BookSearchEngine {
    
    // TODO: Ez egy példa "problémás" kódra review célokra
    
    public static List<Book> search(List<Book> books, String query) {
        List<Book> results = new ArrayList<>();
        
        // Problémás kód 1: Nincs null check
        for (Book b : books) {
            // Problémás kód 2: Magic number
            if (calculateRelevanceScore(b, query) > 3) {
                results.add(b);
            }
        }
        
        // Problémás kód 3: Bonyolult és nem hatékony
        for (int i = 0; i < results.size(); i++) {
            for (int j = i + 1; j < results.size(); j++) {
                if (calculateRelevanceScore(results.get(i), query) < 
                    calculateRelevanceScore(results.get(j), query)) {
                    Book temp = results.get(i);
                    results.set(i, results.get(j));
                    results.set(j, temp);
                }
            }
        }
        
        return results;
    }
    
    // Problémás kód 4: Túl bonyolult metódus, nincs dokumentáció
    private static int calculateRelevanceScore(Book book, String query) {
        int score = 0;
        String q = query.toLowerCase();
        String title = book.getTitle().toLowerCase();
        String author = book.getAuthor().toLowerCase();
        
        if (title.equals(q)) score += 10;
        if (author.equals(q)) score += 8;
        if (title.contains(q)) score += 5;
        if (author.contains(q)) score += 3;
        
        // Problémás kód 5: Magic number és nem érthető logika
        if (book.getPublicationYear() > 2000) score += 2;
        if (book.isAvailable()) score += 1;
        
        return score;
    }
    
    // Problémás kód 6: Nem használt metódus
    private static boolean isClassic(Book book) {
        return book.getPublicationYear() < 1950;
    }
    
    // Problémás kód 7: Rossz naming és logic
    public static List<Book> filterBooks(Library lib, String criteria) {
        List<Book> allBooks = lib.getAllBooks();
        List<Book> filtered = new ArrayList<>();
        
        for (Book book : allBooks) {
            // Mi ez a criteria? Nem világos!
            if (criteria.equals("new")) {
                if (book.getPublicationYear() > 2010) {
                    filtered.add(book);
                }
            } else if (criteria.equals("old")) {
                if (book.getPublicationYear() < 1980) {
                    filtered.add(book);
                }
            }
            // Mi van ha criteria nem "new" vagy "old"? Nem kezeli!
        }
        
        return filtered;
    }
}
```

### 4.2.3 Library.java Frissítése az Új Search Engine-nel

Adjuk hozzá a következő metódust a `Library.java` fájlhoz:

```java
    /**
     * Fejlett keresés a BookSearchEngine használatával
     */
    public List<Book> advancedSearch(String query) {
        return BookSearchEngine.search(books, query);
    }

    /**
     * Könyvek szűrése korszak alapján
     */
    public List<Book> filterBooksByEra(String era) {
        return BookSearchEngine.filterBooks(this, era);
    }
```

### 4.2.4 LibraryApp.java Frissítése

Adjunk hozzá új menüpontot:

```java
// showMenu() frissítése:
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
        System.out.println("10. Fejlett keresés");
        System.out.println("11. Szűrés korszak alapján");
        System.out.println("12. Kilépés");
        System.out.print("Választásod: ");
    }

// Switch case frissítése:
                case 10:
                    advancedSearch();
                    break;
                case 11:
                    filterByEra();
                    break;
                case 12:
                    System.out.println("Köszönjük a látogatást!");
                    return;

// Új metódusok:
    private void advancedSearch() {
        System.out.print("Add meg a keresési kifejezést: ");
        String query = scanner.nextLine();
        
        List<Book> results = library.advancedSearch(query);
        if (results.isEmpty()) {
            System.out.println("Nincs találat a keresésre.");
            return;
        }
        
        System.out.println("\nKeresési eredmények:");
        for (Book book : results) {
            System.out.println("- " + book.getTitle() + " - " + book.getAuthor());
        }
    }

    private void filterByEra() {
        System.out.println("Válassz korszakot:");
        System.out.println("1. Új könyvek (2010 után)");
        System.out.println("2. Régi könyvek (1980 előtt)");
        System.out.print("Választásod: ");
        
        String choice = scanner.nextLine();
        String era = "";
        
        if (choice.equals("1")) {
            era = "new";
        } else if (choice.equals("2")) {
            era = "old";
        } else {
            System.out.println("Érvénytelen választás!");
            return;
        }
        
        List<Book> results = library.filterBooksByEra(era);
        if (results.isEmpty()) {
            System.out.println("Nincs könyv ebben a korszakban.");
            return;
        }
        
        System.out.println("\nSzűrt könyvek:");
        for (Book book : results) {
            System.out.println("- " + book.getTitle() + " (" + book.getPublicationYear() + ")");
        }
    }
```

### 4.2.5 Commit és Push

```powershell
git add .
git commit -m "WIP: Advanced search engine implementation

- Added BookSearchEngine class with search functionality
- Integrated with Library class
- Added new menu options for advanced search and era filtering

Note: This is a work-in-progress commit for code review purposes"

git push -u origin feature/search-improvements
```

## 4.3 Pull Request Létrehozása Review Célokra

### 4.3.1 Draft Pull Request

1. **GitHub-on hozz létre** egy Pull Request-et
2. **FONTOS:** Kattints a "Create draft pull request" gombra
3. **Title:** `WIP: Advanced Search Engine Implementation`
4. **Description:**

```markdown
## 🚧 Work in Progress - Code Review Requested

Hozzáadtam egy fejlett keresőmotort a könyvtár alkalmazáshoz. Kérem review-zzátok mielőtt finalizálom!

## Változtatások
- ✅ BookSearchEngine osztály hozzáadása
- ✅ Relevancia alapú keresés
- ✅ Korszak szerinti szűrés
- ✅ Integráció a Library osztállyal
- ✅ Új menüpontok a LibraryApp-ban

## Kérdések Review-hoz
1. **Performance:** Elég gyors-e a jelenlegi keresési algoritmus?
2. **Code Quality:** Van-e javítanivaló a kód struktúrájában?
3. **Error Handling:** Megfelelően kezelem-e a hibás inputokat?
4. **Naming:** Egyértelműek-e a metódus és változó nevek?

## Tesztelés
- [x] Alapvető funkciók működnek
- [ ] Performance tesztek (nagy könyvtárak esetén)
- [ ] Edge case-ek tesztelése
- [ ] Unit tesztek írása

## TODO a Review után
- [ ] Performance optimalizáció ha szükséges
- [ ] Code review comments implementálása  
- [ ] Unit tesztek hozzáadása
- [ ] Dokumentáció finalizálása

**Kérem ne merge-eljétek amíg nincs kész! 🙏**
```

## 4.4 "Code Review" Végrehajtása

Most "másik fejlesztőként" fogjuk review-zni a kódot.

### 4.4.1 Files Changed Áttekintése

1. **Menj a PR "Files changed" fülére**
2. **Kattints végig minden fájlon**
3. **Add hozzá a következő kommenteket** (kattints a sor számára és "+" gombra):

**BookSearchEngine.java - Sor 13 (null check hiánya):**
```
🚨 **Kritikus hiba**: Nincs null check a `books` paraméteren. 
Ha null-t adunk át, NullPointerException lesz.

Javaslat:
```java
if (books == null || query == null) {
    return new ArrayList<>();
}
```
```

**BookSearchEngine.java - Sor 16 (Magic number):**
```
🔧 **Code Quality**: Magic number (3) a relevancia pontszámhoz. 
Definiáld konstansként:

```java
private static final int MINIMUM_RELEVANCE_SCORE = 3;
```
```

**BookSearchEngine.java - Sor 21-30 (Bubble sort):**
```
⚡ **Performance**: Bubble sort O(n²) bonyolultságú, nem hatékony nagy listákhoz.
Használd inkább a Collections.sort()-ot:

```java
results.sort((b1, b2) -> Integer.compare(
    calculateRelevanceScore(b2, query),
    calculateRelevanceScore(b1, query)
));
```
```

**BookSearchEngine.java - Sor 34 (Metódus komplexitás):**
```
📚 **Documentation & Complexity**: Ez a metódus túl bonyolult és nincs dokumentálva.

Javaslatok:
1. Add hozzá JavaDoc comment-et
2. Bontsd fel kisebb metódusokra
3. Definiáld a pontszám rendszert konstansokként
```

**BookSearchEngine.java - Sor 65 (Dead code):**
```
🧹 **Dead Code**: Az `isClassic()` metódus nincs használva sehol. 
Töröld vagy használd fel valahol.
```

**BookSearchEngine.java - Sor 71 (Rossz naming és error handling):**
```
❌ **API Design**: 
1. A `criteria` paraméter nem világos mit vár
2. Nincs error handling ismeretlen criteria-ra
3. Magic string-ek ("new", "old")

Javaslat: Használj enum-ot:
```java
public enum BookEra { NEW, OLD, CLASSIC }
```
```

### 4.4.2 Általános Review Komment

A PR tetején add hozzá:

```markdown
## 🔍 Overall Code Review

Jó munka a keresőmotor alapjaival! Van néhány terület amit javítani kellene:

### ✅ Pozitívumok
- Jó alapstruktúra és osztály felosztás
- A keresési logika alapvetően helyes
- Proper package structure

### 🚨 Kritikus Problémák (Merge előtt javítandó)
1. **Null pointer protection** hiánya
2. **Performance probléma** a bubble sort miatt
3. **API design** problémák a filterBooks metódusban

### 🔧 Javasolt Javítások
1. **Konstansok használata** magic number-ek helyett
2. **Dokumentáció** hozzáadása minden public metódushoz
3. **Dead code** eltávolítása
4. **Enum használata** string konstansok helyett

### 📋 Következő Lépések
Kérlek javítsd ki a kritikus problémákat, majd jelöld "Ready for review"-ként!

**Status:** ❌ Changes requested
```

## 4.5 Review Kommentek Implementálása

Most "visszaváltasz fejlesztőnek" és implementálod a review kommenteket.

### 4.5.1 BookSearchEngine.java Javítása

Cseréld ki az egész fájlt a következőre:

```java
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
        NEW(2010, Integer.MAX_VALUE, "Új könyvek (2010 után)"),
        OLD(Integer.MIN_VALUE, 1980, "Régi könyvek (1980 előtt)"),
        CLASSIC(Integer.MIN_VALUE, 1950, "Klasszikus könyvek (1950 előtt)");
        
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
```

### 4.5.2 Library.java Frissítése

```java
    /**
     * Fejlett keresés a BookSearchEngine használatával
     */
    public List<Book> advancedSearch(String query) {
        return BookSearchEngine.search(books, query);
    }

    /**
     * Könyvek szűrése korszak alapján
     */
    public List<Book> filterBooksByEra(BookSearchEngine.BookEra era) {
        return BookSearchEngine.filterBooksByEra(books, era);
    }
```

### 4.5.3 LibraryApp.java Frissítése

```java
    private void filterByEra() {
        System.out.println("Válassz korszakot:");
        Map<BookSearchEngine.BookEra, String> eras = BookSearchEngine.getAvailableEras();
        
        int index = 1;
        List<BookSearchEngine.BookEra> eraList = new ArrayList<>();
        for (Map.Entry<BookSearchEngine.BookEra, String> entry : eras.entrySet()) {
            System.out.println(index + ". " + entry.getValue());
            eraList.add(entry.getKey());
            index++;
        }
        
        System.out.print("Választásod: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > eraList.size()) {
                System.out.println("Érvénytelen választás!");
                return;
            }
            
            BookSearchEngine.BookEra selectedEra = eraList.get(choice - 1);
            List<Book> results = library.filterBooksByEra(selectedEra);
            
            if (results.isEmpty()) {
                System.out.println("Nincs könyv ebben a korszakban.");
                return;
            }
            
            System.out.println("\nSzűrt könyvek (" + selectedEra.getDescription() + "):");
            for (Book book : results) {
                System.out.println("- " + book.getTitle() + " (" + book.getPublicationYear() + ")");
            }
        } catch (NumberFormatException e) {
            System.out.println("Érvénytelen szám formátum!");
        }
    }
```

### 4.5.4 Javítások Commitolása

```powershell
git add .
git commit -m "Fix: Address code review feedback

✅ Null pointer protection added
✅ Magic numbers replaced with constants  
✅ Bubble sort replaced with Collections.sort
✅ JavaDoc documentation added
✅ Dead code removed
✅ BookEra enum introduced
✅ Better error handling
✅ Improved API design

All critical and suggested issues from code review have been addressed."

git push origin feature/search-improvements
```

## 4.6 Re-review és Approval

### 4.6.1 Review Update

Menj vissza a PR-hez és add hozzá:

```markdown
## 🔄 Re-Review után

### ✅ Javítások Implementálva
- [x] Null pointer protection hozzáadva
- [x] Konstansok bevezetése magic number-ek helyett
- [x] Performance javítva Collections.sort-tal
- [x] Teljes JavaDoc dokumentáció
- [x] Dead code eltávolítva  
- [x] BookEra enum bevezetése
- [x] Jobb error handling

### 🎯 Code Quality
A kód most production-ready minőségű! Tiszta, dokumentált és hatékony.

### 📊 Performance
A Collections.sort O(n log n) bonyolultságú, jelentős javulás a korábbi O(n²)-hez képest.

**Status:** ✅ Approved - Ready to merge!
```

### 4.6.2 Draft eltávolítása

1. **Kattints a "Ready for review" gombra** a PR tetején
2. **Merge-eld a PR-t**
3. **Töröld a branch-et**

## 4.7 Code Review Best Practices

### Review Adás Szempontjai

#### 🔍 Mit Nézz
1. **Functionality** - Működik-e a kód?
2. **Readability** - Érthető-e?
3. **Performance** - Hatékony-e?
4. **Security** - Biztonságos-e?
5. **Testing** - Tesztelhető-e?

#### 💬 Hogyan Kommentezz
- **Konstruktív** legyen, ne csak kritizálj
- **Konkrét javaslatokat** adj
- **Kódrészletekkel** illusztráld a pontot
- **Pozitívumokat** is emeld ki
- **Prioritást** jelezz (kritikus/javasolt/opcionális)

#### ⏰ Mikor Review-zz
- **24-48 órán belül** válaszolj
- **Kis PR-ek** hamarabb
- **Komplex változtatások** több időt igényelnek

### Review Fogadás Szempontjai

#### 😊 Helyes Hozzáállás
- **Nyitott** maradj a feedbackre
- **Tanulási lehetőség**-ként tekints rá
- **Ne vedd személyesen** a kritikát
- **Kérdezz** ha valami nem világos

#### 🔧 Implementálás
- **Minden kritikus** problémát javíts
- **Javaslatok** mérlegelése
- **Új commit**-okkal javíts
- **Válaszolj** a kommentekre

## Következő lépések

Most hogy elsajátítottad a code review folyamatot, folytasd a [05-TESZTEK.md](05-TESZTEK.md) dokumentummal, ahol megtanulod hogyan írj unit teszteket és hogyan állíts be GitHub Actions CI/CD pipeline-t!
