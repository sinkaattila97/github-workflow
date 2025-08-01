# 3. Pull Request Létrehozása és Kezelése

## 3.1 Mi a Pull Request?

A Pull Request (PR) egy mechanizmus, amellyel jelzed, hogy készen állsz egy branch változtatásainak beépítésére a fő branch-be. Ez lehetőséget ad:
- **Code review-ra** - mások átnézhetik a kódot
- **Tesztelésre** - automatikus tesztek futtatására
- **Megbeszélésre** - kommentelni és javítani a kódot

## 3.2 Első Pull Request: Book Borrowing Feature

### 3.2.1 Pull Request Létrehozása GitHub-on

1. **Menj a GitHub repository-dba** böngészőben
2. **Észre fogod venni** hogy GitHub jelzi az új branch-eket egy sárga sávban
3. **Kattints a "Compare & pull request" gombra** a `feature/book-borrowing` branch mellett

**VAGY** manuálisan:
1. **Kattints a "Pull requests" fülre**
2. **Kattints a "New pull request" gombra**
3. **Base branch:** `main`
4. **Compare branch:** `feature/book-borrowing`

### 3.2.2 Pull Request Adatok Kitöltése

**Title (Cím):**
```
Feature: Könyv kölcsönzési rendszer hozzáadása
```

**Description (Leírás):**
```markdown
## Összefoglaló
Ez a PR hozzáadja a könyv kölcsönzési funkcionalitást a könyvtár alkalmazáshoz.

## Változtatások
- ✅ Könyv kölcsönzése ISBN alapján
- ✅ Könyv visszahozási funkció
- ✅ Kölcsönzött könyvek listázása
- ✅ Könyv keresés ISBN alapján
- ✅ Frissített felhasználói menü

## Tesztelés
- [x] Manuális tesztelés helyi környezetben
- [x] Összes új funkció működik
- [x] Meglévő funkciók nem sérültek

## Képernyőképek/Példák
Új menüpontok:
- 5. Könyv kölcsönzése
- 6. Könyv visszahozása  
- 7. Kölcsönzött könyvek listája

## Checklist
- [x] Kód clean és dokumentált
- [x] Nincs hardcoded érték
- [x] Error handling hozzáadva
- [ ] Unit tesztek írva (következő PR-ben)
```

**További beállítások:**
- **Reviewers:** Ha van csapattársad, add hozzá
- **Assignees:** Saját magad
- **Labels:** `enhancement`, `feature`

### 3.2.3 Pull Request Létrehozása

1. **Kattints a "Create pull request" gombra**
2. **GitHub automatikusan ellenőrzi** hogy merge-elhető-e
3. **Várd meg** hogy megjelenjen a PR oldal

## 3.3 Pull Request Részleteinek Áttekintése

### 3.3.1 Files Changed Fül

1. **Kattints a "Files changed" fülre**
2. **Tekintsd át** minden módosítást:
   - Zöld vonalak: hozzáadott kód
   - Piros vonalak: törölt kód
   - Szürke vonalak: változatlan kontextus

### 3.3.2 Checks Fül (ha vannak automatikus tesztek)

Később fogjuk beállítani a GitHub Actions-t, de most nézd meg hogy:
- **"Some checks haven't completed yet"** vagy
- **"All checks have passed"** üzenet jelenik meg

## 3.4 Második Pull Request: Book Ratings Feature

### 3.4.1 PR Létrehozása

Ugyanúgy mint az előbb, de ezúttal a `feature/book-ratings` branch-hez:

**Title:**
```
Feature: Könyv értékelési rendszer
```

**Description:**
```markdown
## Összefoglaló
Könyv értékelési és véleményezési rendszer hozzáadása.

## Változtatások
- ✅ Értékelés hozzáadása 1-5 skálán
- ✅ Vélemények írása könyvekhez  
- ✅ Átlagos értékelés számítása
- ✅ Összes értékelés megtekintése
- ✅ Book toString frissítése értékelési infóval

## Új Book metódusok
- `addRating(int rating, String review)` - értékelés hozzáadása
- `getAverageRating()` - átlag számítás
- `getRatingCount()` - értékelések száma
- `getAllReviews()` - összes vélemény

## Új LibraryApp funkciók
- Könyv értékelése (menüpont 8)
- Értékelések megtekintése (menüpont 9)

## Tesztelés
- [x] Különböző értékelések hozzáadása
- [x] Átlag számítás ellenőrzése
- [x] Érvénytelen input kezelése

## Függőségek
Ez a PR független a borrowing system-től, de később integrálni fogjuk.
```

## 3.5 Merge-elés Sorrendje

### 3.5.1 Első PR Merge-elése (Book Borrowing)

Mivel ez a simpler funkció, először ezt merge-eljük:

1. **Menj vissza az első PR-hez**
2. **Ellenőrizd** hogy minden rendben van
3. **Kattints a "Merge pull request" gombra**
4. **Választd a "Create a merge commit" opciót**
5. **Kattints a "Confirm merge" gombra**
6. **Delete branch:** Kattints a "Delete branch" gombra a PR merge után

### 3.5.2 Local Cleanup

```powershell
# Váltás main-re
git checkout main

# Main frissítése
git pull origin main

# Töröld a helyi feature branch-et
git branch -d feature/book-borrowing

# Ellenőrizd a branch-eket
git branch -a
```

## 3.6 Konfliktus Kezelése a Második PR-nél

### 3.6.1 Konfliktus Felismerése

1. **Menj a második PR-hez** (`feature/book-ratings`)
2. **Látni fogod** hogy konfliktus van:
   - "This branch has conflicts that must be resolved"
   - "Resolve conflicts" gomb megjelenik

### 3.6.2 Konfliktus Megoldása GitHub-on

**Opció 1: GitHub Web Interface**
1. **Kattints a "Resolve conflicts" gombra**
2. **Szerkesztő megnyílik** a konfliktusos fájllal
3. **Látod a konfliktus jelölőket:**
   ```
   <<<<<<< feature/book-ratings
   System.out.println("Üdvözöl a " + library.getName() + " - Értékelési Rendszerrel!");
   =======
   System.out.println("Üdvözöl a " + library.getName() + " - Kölcsönzési Rendszerrel!");
   >>>>>>> main
   ```
4. **Módosítsd** egy kombinált verzióra:
   ```java
   System.out.println("Üdvözöl a " + library.getName() + " - Kölcsönzési és Értékelési Rendszerrel!");
   ```
5. **Töröld** a konfliktus jelölőket (`<<<<<<<`, `=======`, `>>>>>>>`)
6. **Kattints a "Mark as resolved" gombra**
7. **Kattints a "Commit merge" gombra**

**Opció 2: Lokális Megoldás**
```powershell
# Váltás a feature branch-re
git checkout feature/book-ratings

# Main branch változtatásainak behúzása
git pull origin main

# Konfliktus megoldása szövegszerkesztőben
# Szerkeszd a LibraryApp.java fájlt és válaszd ki a megfelelő verziót

# A megoldás commitolása
git add .
git commit -m "Resolve conflict: Combine borrowing and rating system messages"
git push origin feature/book-ratings
```

### 3.6.3 PR Finalizálása

1. **Frissítsd a PR leírást** ha szükséges
2. **Ellenőrizd** hogy most már merge-elhető
3. **Merge-eld a PR-t**
4. **Töröld a branch-et**

## 3.7 Final Local Cleanup

```powershell
# Main frissítése
git checkout main
git pull origin main

# Feature branch törlése
git branch -d feature/book-ratings

# Összes branch ellenőrzése
git branch -a

# Remote tracking branch-ek tisztítása
git remote prune origin
```

## 3.8 Kombinált Alkalmazás Tesztelése

Most hogy mindkét feature be van merge-elve:

```powershell
# Fordítás és futtatás
javac -d . src/main/java/com/example/library/*.java
java com.example.library.LibraryApp
```

**Tesztelendő funkciók:**
1. ✅ Könyv hozzáadása
2. ✅ Könyv kölcsönzése  
3. ✅ Könyv értékelése
4. ✅ Kölcsönzött könyvek listája
5. ✅ Értékelések megtekintése
6. ✅ Könyv visszahozása

## 3.9 Best Practices Pull Request-ekhez

### PR Title és Description
- **Világos cím** ami leírja mit csinál
- **Részletes leírás** ami elmagyarázza a MIÉRT-et
- **Checkbox lista** a változtatásokról
- **Tesztelési információ**

### PR Mérete
- **Kis PR-ek jobbak** mint nagyok
- **Egy funkció per PR** általában
- **Max 400-500 sor** változtatás ideális

### Commit Messages
- **Értelmes commit üzenetek**
- **Consistent formátum** használata
- **Többsoros leírás** ha komplex a változtatás

## Következő lépések

Most hogy tudod hogyan készíts és merge-elj PR-eket, folytasd a [04-CODE-REVIEW.md](04-CODE-REVIEW.md) dokumentummal, ahol megtanulod hogyan adj és fogadj code review-kat!
