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

:: Compile test source code
javac -d build -cp "junit-platform-console-standalone-1.10.0.jar;build" -sourcepath "src\test\java;src\main\java" src\test\java\com\example\library\*.java
if %errorlevel% neq 0 (
    echo Test compilation failed!
    exit /b 1
)

echo Running tests...
java -jar junit-platform-console-standalone-1.10.0.jar --class-path build --scan-class-path --details=verbose

echo.
echo Test execution completed!