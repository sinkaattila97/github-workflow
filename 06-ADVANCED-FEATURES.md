# 6. Haladó GitHub Funkciók

## 6.1 GitHub Issues - Feladat és Hiba Követés

### 6.1.1 Issue Template-ek Létrehozása

Hozd létre: `.github\ISSUE_TEMPLATE\bug_report.yml`

```yml
name: 🐛 Bug Report
description: Report a bug or unexpected behavior
title: "[BUG] "
labels: ["bug", "needs-investigation"]
assignees: []

body:
  - type: markdown
    attributes:
      value: |
        Köszönjük hogy jelenteted a hibát! Kérjük töltsd ki az alábbi mezőket a lehető legrészletesebben.

  - type: input
    id: version
    attributes:
      label: Verzió
      description: Melyik verzióban észlelted a hibát?
      placeholder: "pl. v1.0.0 vagy commit hash"
    validations:
      required: true

  - type: textarea
    id: description
    attributes:
      label: Hiba leírása
      description: Röviden írd le mit tapasztaltál
      placeholder: "A könyv kölcsönzés során..."
    validations:
      required: true

  - type: textarea
    id: reproduction
    attributes:
      label: Reprodukálási lépések
      description: Hogyan lehet újra előidézni a hibát?
      placeholder: |
        1. Lépj a '...' menüpontra
        2. Kattints a '...' gombra
        3. Add meg az '...' értéket
        4. Hibát kapsz
    validations:
      required: true

  - type: textarea
    id: expected
    attributes:
      label: Elvárt viselkedés
      description: Mit vártál hogy történjen?
      placeholder: "A könyvnek kikölcsönzöttnek kellett volna lennie"
    validations:
      required: true

  - type: textarea
    id: actual
    attributes:
      label: Tényleges viselkedés
      description: Mi történt helyette?
      placeholder: "Exception dobódott vagy rossz üzenet jelent meg"
    validations:
      required: true

  - type: textarea
    id: environment
    attributes:
      label: Környezet
      description: |
        Milyen környezetben tapasztaltad a hibát?
      value: |
        - OS: [pl. Windows 11, macOS, Ubuntu]
        - Java verzió: [pl. Java 17]
        - IDE: [pl. IntelliJ IDEA, VS Code]
      render: markdown
    validations:
      required: true

  - type: textarea
    id: additional
    attributes:
      label: További információk
      description: Bármi más ami segíthet (screenshot, log, stb.)
    validations:
      required: false

  - type: checkboxes
    id: checks
    attributes:
      label: Ellenőrzések
      description: Kérjük pipáld be hogy megtetted ezeket
      options:
        - label: Megnéztem hogy nincs-e már hasonló issue
          required: true
        - label: Frissítettem a legújabb verzióra
          required: true
        - label: Elolvastam a dokumentációt
          required: true
```

Hozd létre: `.github\ISSUE_TEMPLATE\feature_request.yml`

```yml
name: ✨ Feature Request
description: Javasolj egy új funkciót
title: "[FEATURE] "
labels: ["enhancement", "needs-discussion"]
assignees: []

body:
  - type: markdown
    attributes:
      value: |
        💡 Köszönjük az ötletet! Minél részletesebben leírod, annál jobban meg tudjuk érteni mit szeretnél.

  - type: textarea
    id: problem
    attributes:
      label: Probléma leírása
      description: Milyen problémát oldana meg ez a funkció?
      placeholder: "Nem tudok... ezért nehéz..."
    validations:
      required: true

  - type: textarea
    id: solution
    attributes:
      label: Javasolt megoldás
      description: Hogyan képzeled el a megoldást?
      placeholder: "Jó lenne ha lehetne..."
    validations:
      required: true

  - type: textarea
    id: alternatives
    attributes:
      label: Alternatív megoldások
      description: Gondoltál-e más megközelítésekre?
      placeholder: "Másik lehetőség lenne..."
    validations:
      required: false

  - type: dropdown
    id: priority
    attributes:
      label: Prioritás
      description: Mennyire fontos ez a funkció?
      options:
        - "Alacsony - nice to have"
        - "Közepes - hasznos lenne"
        - "Magas - nagyon kellene"
        - "Kritikus - nélkülözhetetlen"
    validations:
      required: true

  - type: checkboxes
    id: implementation
    attributes:
      label: Implementálás
      description: Hogyan tervezed megvalósítani?
      options:
        - label: Magam szeretném implementálni
        - label: Segítségre lenne szükségem
        - label: Mások implementálják

  - type: textarea
    id: additional
    attributes:
      label: További részletek
      description: Bármi más ami fontos (mockup, példa kód, stb.)
    validations:
      required: false
```

### 6.1.2 Issue Labelek Létrehozása

Hozd létre: `GITHUB-LABELS-SETUP.md`

```markdown
# GitHub Labels Beállítása

## Alapvető Label Kategóriák

### 🏷️ Típus Labels
- `bug` - 🐛 Hiba jelentés - Piros (#d73a49)
- `enhancement` - ✨ Új funkció - Kék (#a2eeef)  
- `documentation` - 📚 Dokumentáció - Világoskék (#0075ca)
- `question` - ❓ Kérdés - Lila (#d876e3)
- `duplicate` - 👥 Duplikáció - Szürke (#cfd3d7)

### 🎯 Prioritás Labels  
- `priority: low` - 🔽 Alacsony - Zöld (#28a745)
- `priority: medium` - ⏸️ Közepes - Sárga (#ffc107)
- `priority: high` - 🔼 Magas - Narancs (#fd7e14)
- `priority: critical` - 🚨 Kritikus - Piros (#dc3545)

### 🏗️ Status Labels
- `needs-investigation` - 🔍 Vizsgálat szükséges - Lila (#6f42c1)
- `in-progress` - 🚧 Folyamatban - Sárga (#fbca04)
- `ready-for-review` - 👀 Review-ra kész - Zöld (#0e8a16)
- `blocked` - 🚫 Blokkolva - Piros (#b60205)
- `won't-fix` - ❌ Nem javítjuk - Fehér (#ffffff)

### 📋 Komponens Labels
- `component: library` - 📚 Library osztály - Kék (#1d76db)
- `component: book` - 📖 Book osztály - Zöld (#0e8a16)
- `component: search` - 🔍 Keresőmotor - Narancs (#d93f0b)
- `component: ui` - 🖥️ Felhasználói felület - Lila (#8b5cf6)
- `component: tests` - 🧪 Tesztek - Rózsaszín (#f85a9f)

### 👥 Team Labels
- `good-first-issue` - 🌱 Kezdőknek - Zöld (#7057ff)
- `help-wanted` - 🙋 Segítség kell - Kék (#008672)
- `needs-discussion` - 💬 Megbeszélés - Sárga (#fbca04)
```

### 6.1.3 Első Issues Létrehozása

```powershell
# Branch létrehozása példa issue-khoz
git checkout -b feature/example-issues

# Commit az issue template-ekkel
git add .
git commit -m "Add GitHub issue templates

- Bug report template with detailed fields
- Feature request template with priority
- Labels setup documentation

Templates help standardize issue reporting and improve project management."

git push origin feature/example-issues
```

## 6.2 Project Boards - Feladatok Szervezése

### 6.2.1 GitHub Project Létrehozása

1. **Menj a repository-ba** GitHub-on
2. **Kattints a "Projects" fülre**
3. **"Link a project" > "Create new project"**
4. **Válaszd a "Team planning" template-et**
5. **Project név:** `Library Management Development`

### 6.2.2 Project Board Beállítása

**Oszlopok:**
- 📋 **Backlog** - Ötletek és tervezett funkciók
- 🔄 **Todo** - Következő sprintbe tervezett
- 🚧 **In Progress** - Aktívan fejlesztés alatt
- 👀 **In Review** - Code review vagy testing
- ✅ **Done** - Befejezett és deploy-olt

**Custom Fields:**
- **Priority:** Single select (Low, Medium, High, Critical)
- **Effort:** Number (1-13 story points)
- **Component:** Single select (Library, Book, Search, UI, Tests)
- **Sprint:** Text (pl. "Sprint 1", "Sprint 2")

### 6.2.3 Automated Workflows

**Project Settings > Workflows:**

1. **Auto-add issues and PRs:**
   - When: `Item added to project`
   - Then: `Set status to Backlog`

2. **Move to In Progress:**
   - When: `Pull request opened`
   - Then: `Set status to In Progress`

3. **Move to In Review:**
   - When: `Pull request marked ready for review`
   - Then: `Set status to In Review`

4. **Move to Done:**
   - When: `Pull request merged`
   - Then: `Set status to Done`

## 6.3 GitHub Actions Automation

### 6.3.1 Issue Auto-Assignment

Hozd létre: `.github\workflows\issue-automation.yml`

```yml
name: Issue Automation

on:
  issues:
    types: [opened, labeled]

jobs:
  auto-assign:
    runs-on: ubuntu-latest
    steps:
    - name: Auto-assign based on labels
      if: github.event.action == 'opened'
      uses: actions/github-script@v7
      with:
        script: |
          const issue = context.payload.issue;
          const labels = issue.labels.map(label => label.name);
          
          // Auto-assign based on component
          let assignees = [];
          
          if (labels.includes('component: search')) {
            assignees.push('${{ github.repository_owner }}');
          }
          
          if (labels.includes('component: tests')) {
            assignees.push('${{ github.repository_owner }}');
          }
          
          if (assignees.length > 0) {
            await github.rest.issues.addAssignees({
              owner: context.repo.owner,
              repo: context.repo.repo,
              issue_number: issue.number,
              assignees: assignees
            });
          }

  label-management:
    runs-on: ubuntu-latest
    if: github.event.action == 'labeled'
    steps:
    - name: Add to project based on priority
      uses: actions/github-script@v7
      with:
        script: |
          const issue = context.payload.issue;
          const label = context.payload.label;
          
          // Add high priority issues to current sprint
          if (label.name === 'priority: high' || label.name === 'priority: critical') {
            // Add comment with urgency
            await github.rest.issues.createComment({
              owner: context.repo.owner,
              repo: context.repo.repo,
              issue_number: issue.number,
              body: `⚠️ This issue has been marked as **${label.name}**. It will be prioritized in the next planning session.`
            });
          }
```

### 6.3.2 PR Size Warning

Hozd létre: `.github\workflows\pr-size-check.yml`

```yml
name: PR Size Check

on:
  pull_request:
    types: [opened, synchronize]

jobs:
  check-size:
    runs-on: ubuntu-latest
    steps:
    - name: Check PR size
      uses: actions/github-script@v7
      with:
        script: |
          const pr = context.payload.pull_request;
          const { data: files } = await github.rest.pulls.listFiles({
            owner: context.repo.owner,
            repo: context.repo.repo,
            pull_number: pr.number
          });
          
          const totalChanges = files.reduce((sum, file) => sum + file.additions + file.deletions, 0);
          const filesChanged = files.length;
          
          let message = '';
          let labels = [];
          
          if (totalChanges > 1000 || filesChanged > 20) {
            message = `🚨 **Large PR Warning**
            
            This PR modifies ${filesChanged} files with ${totalChanges} total changes.
            
            Large PRs are harder to review and more likely to have issues. Consider:
            - Breaking into smaller, focused PRs
            - Explaining the scope in the description
            - Adding extra context for reviewers
            
            **Files changed:** ${filesChanged}
            **Total changes:** ${totalChanges}`;
            
            labels.push('size: large');
          } else if (totalChanges > 500 || filesChanged > 10) {
            message = `⚠️ **Medium PR Notice**
            
            This PR has ${totalChanges} changes across ${filesChanged} files.
            Please ensure adequate testing and documentation.`;
            
            labels.push('size: medium');
          } else {
            labels.push('size: small');
          }
          
          // Add labels
          if (labels.length > 0) {
            await github.rest.issues.addLabels({
              owner: context.repo.owner,
              repo: context.repo.repo,
              issue_number: pr.number,
              labels: labels
            });
          }
          
          // Add comment for large PRs
          if (message) {
            await github.rest.issues.createComment({
              owner: context.repo.owner,
              repo: context.repo.repo,
              issue_number: pr.number,
              body: message
            });
          }
```

### 6.3.3 Release Automation

Hozd létre: `.github\workflows\release.yml`

```yml
name: Release

on:
  push:
    tags:
      - 'v*'

jobs:
  create-release:
    runs-on: ubuntu-latest
    steps:
    - name: Checkout code
      uses: actions/checkout@v4
      with:
        fetch-depth: 0

    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Download JUnit
      run: |
        wget -q https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/junit-platform-console-standalone-1.10.0.jar

    - name: Build and test
      run: |
        mkdir -p build
        javac -d build -sourcepath src/main/java src/main/java/com/example/library/*.java
        javac -d build -cp "junit-platform-console-standalone-1.10.0.jar:build" -sourcepath "src/test/java:src/main/java" src/test/java/com/example/library/*.java
        java -jar junit-platform-console-standalone-1.10.0.jar --class-path build --scan-class-path

    - name: Create JAR
      run: |
        cd build
        jar cvf ../library-management-${{ github.ref_name }}.jar com/example/library/*.class
        cd ..

    - name: Generate changelog
      id: changelog
      run: |
        # Get previous tag
        PREV_TAG=$(git describe --tags --abbrev=0 HEAD~1 2>/dev/null || echo "")
        
        if [ -z "$PREV_TAG" ]; then
          echo "changelog=First release of Library Management System" >> $GITHUB_OUTPUT
        else
          echo "changelog<<EOF" >> $GITHUB_OUTPUT
          echo "## Changes since $PREV_TAG" >> $GITHUB_OUTPUT
          echo "" >> $GITHUB_OUTPUT
          git log --pretty=format:"- %s (%an)" $PREV_TAG..HEAD >> $GITHUB_OUTPUT
          echo "" >> $GITHUB_OUTPUT
          echo "EOF" >> $GITHUB_OUTPUT
        fi

    - name: Create Release
      uses: actions/create-release@v1
      env:
        GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
      with:
        tag_name: ${{ github.ref }}
        release_name: Release ${{ github.ref_name }}
        body: |
          🎉 **Library Management System ${{ github.ref_name }}**
          
          ${{ steps.changelog.outputs.changelog }}
          
          ## 📦 Download
          
          - **JAR file:** library-management-${{ github.ref_name }}.jar
          - **Source code:** Available as ZIP/TAR.GZ below
          
          ## 🚀 Usage
          
          ```bash
          java -cp library-management-${{ github.ref_name }}.jar com.example.library.LibraryApp
          ```
          
          ## 📋 Requirements
          
          - Java 11 or higher
          
          ## 🐛 Known Issues
          
          Check our [Issues page](https://github.com/${{ github.repository }}/issues) for known problems.
        draft: false
        prerelease: false

    - name: Upload JAR to release
      uses: actions/upload-release-asset@v1
      env:
        GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
      with:
        upload_url: ${{ steps.create_release.outputs.upload_url }}
        asset_path: ./library-management-${{ github.ref_name }}.jar
        asset_name: library-management-${{ github.ref_name }}.jar
        asset_content_type: application/java-archive
```

## 6.4 Security és Code Quality

### 6.4.1 Dependabot Beállítása

Hozd létre: `.github\dependabot.yml`

```yml
version: 2
updates:
  # GitHub Actions
  - package-ecosystem: "github-actions"
    directory: "/"
    schedule:
      interval: "weekly"
    commit-message:
      prefix: "chore"
      include: "scope"

  # Java dependencies (ha később Maven/Gradle-t használnánk)
  - package-ecosystem: "maven"
    directory: "/"
    schedule:
      interval: "weekly"
    commit-message:
      prefix: "deps"
      include: "scope"
    reviewers:
      - "${{ github.repository_owner }}"
    assignees:
      - "${{ github.repository_owner }}"
```

### 6.4.2 Security Policy

Hozd létre: `SECURITY.md`

```markdown
# Security Policy

## Supported Versions

| Version | Supported          |
| ------- | ------------------ |
| 1.x.x   | :white_check_mark: |
| < 1.0   | :x:                |

## Reporting a Vulnerability

Ha biztonsági problémát találsz, kérjük:

### 🔒 Privát Jelentés

1. **NE hozz létre** publikus issue-t
2. **Küldj email-t:** [security@yourproject.com] vagy GitHub Security Advisories
3. **Adj meg:**
   - Részletes leírás
   - Reprodukálási lépések
   - Esetleges proof of concept
   - Javasolt javítás (ha van)

### ⏱️ Válaszidő

- **24 órán belül:** Visszaigazolás
- **72 órán belül:** Kezdeti értékelés
- **1 héten belül:** Részletes válasz és timeline

### 🏆 Elismerés

Komoly biztonsági problémák jelentőit elismerjük:
- Credits a CHANGELOG-ban
- Opcionális mention a dokumentációban

### 📋 Folyamat

1. **Fogadás és visszaigazolás**
2. **Probléma validálása**
3. **Javítás kifejlesztése**
4. **Testing és review**
5. **Koordinált közzététel**
6. **Credits és dokumentáció**

## Security Best Practices

### Kód Szinten
- Input validation minden felhasználói inputra
- Proper error handling
- Secure coding guidelines követése

### Deployment
- Regular updates
- Access control
- Monitoring és logging

Köszönjük hogy segítesz biztonságosabbá tenni a projektet! 🛡️
```

### 6.4.3 Code Quality Checks

Hozd létre: `.github\workflows\code-quality.yml`

```yml
name: Code Quality

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main, develop ]

jobs:
  checkstyle:
    runs-on: ubuntu-latest
    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Download Checkstyle
      run: |
        wget -q https://github.com/checkstyle/checkstyle/releases/download/checkstyle-10.12.4/checkstyle-10.12.4-all.jar

    - name: Create Checkstyle config
      run: |
        cat > checkstyle.xml << 'EOF'
        <?xml version="1.0"?>
        <!DOCTYPE module PUBLIC
            "-//Puppy Crawl//DTD Check Configuration 1.3//EN"
            "https://www.puppycrawl.com/dtds/configuration_1_3.dtd">
        
        <module name="Checker">
          <property name="charset" value="UTF-8"/>
          <property name="severity" value="warning"/>
          <property name="fileExtensions" value="java"/>
          
          <module name="TreeWalker">
            <!-- Naming Conventions -->
            <module name="ConstantName"/>
            <module name="LocalFinalVariableName"/>
            <module name="LocalVariableName"/>
            <module name="MemberName"/>
            <module name="MethodName"/>
            <module name="PackageName"/>
            <module name="ParameterName"/>
            <module name="StaticVariableName"/>
            <module name="TypeName"/>
            
            <!-- Imports -->
            <module name="AvoidStarImport"/>
            <module name="IllegalImport"/>
            <module name="RedundantImport"/>
            <module name="UnusedImports"/>
            
            <!-- Size Violations -->
            <module name="LineLength">
              <property name="max" value="120"/>
            </module>
            <module name="MethodLength"/>
            <module name="ParameterNumber"/>
            
            <!-- Whitespace -->
            <module name="EmptyForIteratorPad"/>
            <module name="GenericWhitespace"/>
            <module name="MethodParamPad"/>
            <module name="NoWhitespaceAfter"/>
            <module name="NoWhitespaceBefore"/>
            <module name="ParenPad"/>
            <module name="TypecastParenPad"/>
            <module name="WhitespaceAfter"/>
            <module name="WhitespaceAround"/>
            
            <!-- Modifier Checks -->
            <module name="ModifierOrder"/>
            <module name="RedundantModifier"/>
            
            <!-- Blocks -->
            <module name="AvoidNestedBlocks"/>
            <module name="EmptyBlock"/>
            <module name="LeftCurly"/>
            <module name="NeedBraces"/>
            <module name="RightCurly"/>
            
            <!-- Common Coding Problems -->
            <module name="EmptyStatement"/>
            <module name="EqualsHashCode"/>
            <module name="IllegalInstantiation"/>
            <module name="InnerAssignment"/>
            <module name="SimplifyBooleanExpression"/>
            <module name="SimplifyBooleanReturn"/>
            
            <!-- Class Design -->
            <module name="FinalClass"/>
            <module name="HideUtilityClassConstructor"/>
            <module name="InterfaceIsType"/>
            
            <!-- Miscellaneous -->
            <module name="ArrayTypeStyle"/>
            <module name="TodoComment"/>
          </module>
        </module>
        EOF

    - name: Run Checkstyle
      run: |
        java -jar checkstyle-10.12.4-all.jar -c checkstyle.xml src/main/java/com/example/library/*.java || true

  complexity-analysis:
    runs-on: ubuntu-latest
    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Analyze code complexity
      run: |
        echo "=== Code Complexity Analysis ==="
        echo ""
        
        # Count lines of code
        echo "📊 Lines of Code:"
        find src/main/java -name "*.java" -exec wc -l {} + | tail -n 1
        
        # Count methods
        echo ""
        echo "📈 Method Count:"
        grep -r "public\|private\|protected" src/main/java --include="*.java" | grep -E "(void|return)" | wc -l
        
        # Count classes
        echo ""
        echo "📋 Class Count:"
        find src/main/java -name "*.java" | wc -l
        
        # Find long methods (>20 lines)
        echo ""
        echo "⚠️  Long Methods (>20 lines):"
        find src/main/java -name "*.java" -exec awk '
          /^[[:space:]]*public|^[[:space:]]*private|^[[:space:]]*protected/ && /{/ {
            method_start = NR; method_name = $0
          }
          /^[[:space:]]*}/ && method_start {
            if (NR - method_start > 20) {
              print FILENAME ":" method_start ": " method_name " (" (NR - method_start) " lines)"
            }
            method_start = 0
          }
        ' {} +
        
        echo ""
        echo "✅ Code complexity analysis completed"
```

## 6.5 Documentation és Wiki

### 6.5.1 Wiki Létrehozása

1. **GitHub repository-ban** kattints a "Wiki" fülre
2. **Create the first page**
3. **Title:** `Home`
4. **Content:**

```markdown
# 📚 Library Management System Wiki

Üdvözlünk a Library Management System wiki-jében!

## 🚀 Gyors Start

- [Installation Guide](Installation-Guide) - Telepítési útmutató
- [User Manual](User-Manual) - Felhasználói kézikönyv  
- [Developer Guide](Developer-Guide) - Fejlesztői dokumentáció
- [API Reference](API-Reference) - API dokumentáció

## 📖 Dokumentáció Kategóriák

### 👤 Felhasználóknak
- [Getting Started](Getting-Started) - Első lépések
- [Features Overview](Features-Overview) - Funkciók áttekintése
- [Troubleshooting](Troubleshooting) - Hibaelhárítás
- [FAQ](FAQ) - Gyakori kérdések

### 💻 Fejlesztőknek
- [Architecture Overview](Architecture-Overview) - Architektúra
- [Code Guidelines](Code-Guidelines) - Kódolási konvenciók
- [Testing Strategy](Testing-Strategy) - Tesztelési stratégia
- [Deployment Guide](Deployment-Guide) - Deploy útmutató

### 🔧 Konfigúráció
- [Configuration Options](Configuration-Options) - Beállítási lehetőségek
- [Database Setup](Database-Setup) - Adatbázis konfiguráció
- [Security Settings](Security-Settings) - Biztonsági beállítások

## 🆘 Segítség

Ha nem találod amit keresel:
1. Használd a Wiki keresőt
2. Nézd meg a [GitHub Issues](../issues) oldalt
3. Kérdezz a [Discussions](../discussions) szekcióban

## 🤝 Közreműködés

A Wiki dokumentáció is nyílt forráskódú! Javítások és kiegészítések szívesen fogadottak.

---
*Utolsó frissítés: {today}*
```

### 6.5.2 API Documentation

Hozd létre új Wiki oldalt: **API Reference**

```markdown
# 📡 API Reference

## Book Class

### Constructor
```java
public Book(String title, String author, String isbn, int publicationYear)
```

Új könyv létrehozása.

**Parameters:**
- `title` - A könyv címe (nem lehet null)
- `author` - A szerző neve (nem lehet null) 
- `isbn` - ISBN szám (egyedi azonosító)
- `publicationYear` - Kiadás éve

**Example:**
```java
Book book = new Book("Java Guide", "John Doe", "978-1234567890", 2023);
```

### Methods

#### getTitle()
```java
public String getTitle()
```
Visszaadja a könyv címét.

#### addRating(int rating, String review)
```java
public boolean addRating(int rating, String review)
```

Értékelés hozzáadása a könyvhöz.

**Parameters:**
- `rating` - Értékelés 1-5 skálán
- `review` - Opcionális vélemény szövege

**Returns:** `true` ha sikeres, `false` ha hibás rating

**Example:**
```java
boolean success = book.addRating(4, "Nagyon jó könyv!");
```

## Library Class

### Constructor
```java
public Library(String name)
```

### Core Methods

#### addBook(Book book)
```java
public boolean addBook(Book book)
```

#### findBooksByTitle(String title)
```java
public List<Book> findBooksByTitle(String title)
```

#### borrowBook(String isbn)
```java
public boolean borrowBook(String isbn)
```

## BookSearchEngine Class

### Search Methods

#### search(List<Book> books, String query)
```java
public static List<Book> search(List<Book> books, String query)
```

Relevancia alapú keresés könyvekben.

**Scoring System:**
- Exact title match: 10 points
- Exact author match: 8 points
- Title contains query: 5 points
- Author contains query: 3 points
- Modern book (>2000): +2 points
- Available book: +1 point

**Example:**
```java
List<Book> results = BookSearchEngine.search(allBooks, "java programming");
```

## Error Handling

Minden public metódus megfelelően kezeli a null és invalid inputokat:

- `null` paraméterek esetén `false` vagy üres lista visszatérés
- Invalid értékek (pl. rating > 5) esetén `false` visszatérés
- Nem található elemek esetén `null` vagy üres lista

## Constants

### BookSearchEngine
```java
private static final int MINIMUM_RELEVANCE_SCORE = 3;
private static final int MODERN_BOOK_THRESHOLD = 2000;
```

### BookEra Enum
```java
NEW(2010, Integer.MAX_VALUE)     // Új könyvek
OLD(Integer.MIN_VALUE, 1980)     // Régi könyvek  
CLASSIC(Integer.MIN_VALUE, 1950) // Klasszikus könyvek
```
```

## 6.6 Commit és Összefoglalás

```powershell
# Add all advanced features
git add .

git commit -m "Add advanced GitHub features and automation

🎯 Issue Management:
  - Bug report and feature request templates
  - Label system with categories and priorities
  - Auto-assignment workflows

🏗️ Project Management:
  - Project board setup documentation
  - Automated status workflows
  - Custom fields and tracking

🤖 Advanced Automation:
  - Issue auto-assignment based on labels
  - PR size checking with warnings
  - Automated release creation with changelog
  - Code quality checks (Checkstyle, complexity)

🔒 Security & Quality:
  - Dependabot configuration
  - Security policy and reporting process
  - Code quality workflows

📚 Documentation:
  - Comprehensive Wiki structure
  - API reference documentation
  - User and developer guides

🎉 Production-ready GitHub setup with enterprise-level features!"

git push origin feature/example-issues
```

## 6.7 Final Pull Request

Hozz létre egy final PR-t ezekkel a haladó funkciókkal és merge-eld be a main branch-be.

## 🎊 Gratulálok!

Sikeresen létrehoztál egy teljes körű GitHub workflow-t amely tartalmazza:

✅ **Alapok:** Repository setup, branch-ek, commit-ok  
✅ **Collaboration:** Pull request-ek, code review  
✅ **Testing:** Unit tesztek, CI/CD pipeline  
✅ **Project Management:** Issues, project boards, automatizálás  
✅ **Quality Assurance:** Code quality checks, security  
✅ **Documentation:** Wiki, API docs, user guides  

Most már professzionális szinten tudsz dolgozni a GitHub-bal! 🚀

### Következő Lépések

1. **Gyakorolj** a tanult dolgokkal saját projekteken
2. **Fedezd fel** más GitHub funkciókkat (Actions marketplace, GitHub Pages, stb.)
3. **Csatlakozz** open source projektekhez és gyakorold a collaboration-t
4. **Tanulj** más DevOps eszközöket (Docker, Kubernetes, Cloud platforms)

**Happy coding!** 💻✨
