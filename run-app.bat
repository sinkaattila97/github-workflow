@echo off
echo Building project...

:: Create build directory
if not exist build mkdir build

:: Compile main source code
javac -d build -sourcepath src\main\java src\main\java\com\example\library\*.java
if %errorlevel% neq 0 (
    echo Main compilation failed!
    exit /b 1
)

echo Running application...
java -cp build com.example.library.LibraryApp

echo.
echo Application finished!
