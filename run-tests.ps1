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

# Compile test source code  
Write-Host "Compiling test source..." -ForegroundColor Yellow
javac -d build -cp "junit-platform-console-standalone-1.10.0.jar;build" -sourcepath "src\test\java;src\main\java" src\test\java\com\example\library\*.java

if ($LASTEXITCODE -ne 0) {
    Write-Host "Test compilation failed!" -ForegroundColor Red
    exit 1
}

# Run tests
Write-Host "Running tests..." -ForegroundColor Green
java -jar junit-platform-console-standalone-1.10.0.jar --class-path build --scan-class-path --details=verbose

Write-Host "`nTest execution completed!" -ForegroundColor Green