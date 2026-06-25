@echo off
REM Database Migration Script for Windows
REM This script copies data from ALL tables with "2025" in the name to their base tables

echo ========================================
echo Database Migration Script - 2025 Tables
echo ========================================
echo.
echo This script will:
echo 1. Find ALL tables with "2025" in the name
echo 2. Copy data to corresponding base tables (removing "2025")
echo 3. Handle conflicts by updating existing records
echo.

REM Database connection parameters - UPDATE THESE!
set DB_HOST=localhost
set DB_PORT=5432
set DB_NAME=itiap
set DB_USER=postgres
set DB_PASSWORD=mKnic123

echo Database: %DB_NAME% on %DB_HOST%:%DB_PORT%
echo.

REM Prompt for password if not set
if "%DB_PASSWORD%"=="your_password" (
    set /p DB_PASSWORD="Enter database password for user %DB_USER%: "
)

echo.
echo Step 1: Finding PostgreSQL psql command...
echo.

REM Try to find psql in common locations
set PSQL_CMD=

REM Check if psql is in PATH
where psql >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    set PSQL_CMD=psql
    echo Found psql in PATH
    goto :run_migration
)

REM Check common PostgreSQL installation paths
if exist "C:\Program Files\PostgreSQL\16\bin\psql.exe" (
    set PSQL_CMD="C:\Program Files\PostgreSQL\16\bin\psql.exe"
    echo Found psql at: C:\Program Files\PostgreSQL\16\bin\psql.exe
    goto :run_migration
)

if exist "C:\Program Files\PostgreSQL\15\bin\psql.exe" (
    set PSQL_CMD="C:\Program Files\PostgreSQL\15\bin\psql.exe"
    echo Found psql at: C:\Program Files\PostgreSQL\15\bin\psql.exe
    goto :run_migration
)

if exist "C:\Program Files\PostgreSQL\14\bin\psql.exe" (
    set PSQL_CMD="C:\Program Files\PostgreSQL\14\bin\psql.exe"
    echo Found psql at: C:\Program Files\PostgreSQL\14\bin\psql.exe
    goto :run_migration
)

if exist "C:\Program Files\PostgreSQL\13\bin\psql.exe" (
    set PSQL_CMD="C:\Program Files\PostgreSQL\13\bin\psql.exe"
    echo Found psql at: C:\Program Files\PostgreSQL\13\bin\psql.exe
    goto :run_migration
)

if exist "C:\Program Files (x86)\PostgreSQL\16\bin\psql.exe" (
    set PSQL_CMD="C:\Program Files (x86)\PostgreSQL\16\bin\psql.exe"
    echo Found psql at: C:\Program Files (x86)\PostgreSQL\16\bin\psql.exe
    goto :run_migration
)

if exist "C:\Program Files (x86)\PostgreSQL\15\bin\psql.exe" (
    set PSQL_CMD="C:\Program Files (x86)\PostgreSQL\15\bin\psql.exe"
    echo Found psql at: C:\Program Files (x86)\PostgreSQL\15\bin\psql.exe
    goto :run_migration
)

REM Check if PostgreSQL is installed via Chocolatey
if exist "C:\ProgramData\chocolatey\lib\postgresql\tools\bin\psql.exe" (
    set PSQL_CMD="C:\ProgramData\chocolatey\lib\postgresql\tools\bin\psql.exe"
    echo Found psql at: C:\ProgramData\chocolatey\lib\postgresql\tools\bin\psql.exe
    goto :run_migration
)

echo ERROR: Could not find psql.exe
echo.
echo Please either:
echo 1. Add PostgreSQL bin directory to your PATH
echo 2. Install PostgreSQL from https://www.postgresql.org/download/windows/
echo 3. Or use pgAdmin to run the SQL script manually
echo.
echo To use pgAdmin:
echo   1. Open pgAdmin
echo   2. Connect to your database
echo   3. Open Query Tool
echo   4. Copy and paste the contents of copy_from_2025_tables.sql
echo   5. Execute the query
echo.
pause
exit /b 1

:run_migration
echo.
echo Step 2: Running migration script...
echo.
echo This will find ALL tables with "2025" in the name and copy them.
echo For example: cand_marks2025 -^> cand_marks
echo.

REM Get the full path of the SQL file
set SQL_FILE=%~dp0copy_from_2025_tables_final.sql
echo Using SQL file: %SQL_FILE%
echo.

pause

REM Run the migration script
%PSQL_CMD% -h %DB_HOST% -p %DB_PORT% -U %DB_USER% -d %DB_NAME% -f "%SQL_FILE%"

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Migration failed!
    echo Please check the error messages above.
    pause
    exit /b 1
)

echo.
echo ========================================
echo Migration completed successfully!
echo ========================================
echo.
echo Next steps:
echo 1. Review the output above to see what was copied
echo 2. Verify the data in your main tables
echo 3. Run this query to check:
echo    SELECT COUNT(*) FROM public.cand_marks;
echo.
pause