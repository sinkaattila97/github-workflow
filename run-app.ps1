Write-Host "Building project..." -ForegroundColor Green

# Create build directory
if (!(Test-Path "build")) {
    New-Item -ItemType Directory -Path "build"
}

# Compile main source code
Write-Host "Compiling main source..." -ForegroundColor Yellow
javac -d build -sourcepath src\main\java src\main\java\com\example\library\*.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "Main compilation failed!" -ForegroundColor Red
    exit 1
}

# Run the application
Write-Host "Running application..." -ForegroundColor Green
java -cp build com.example.library.LibraryApp

Write-Host "`nApplication finished!" -ForegroundColor Green
