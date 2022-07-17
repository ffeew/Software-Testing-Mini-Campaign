# Data Reconciliation Software
The data reconciliation software works by capturing mismatches in 2 different CSV files and listing them as exceptions. 
The program will take in 3 inputs:
1. CSV file 1.
2. CSV file 2.
3. Column numbers of CSV file 1, separated by commas, used as the reference when comparing both files.

## Requirements
1. For it to work, the 2 different CSV files need to have the same column headers, but they can be in different order. E.g. file1: [a, b, c], file2: [a, c, b]
2. The column numbering starts from 0, i.e. the first column is 0
3. The column numbers need to be seperated by commas if >1 column number is provided.

## How it works
The software will use the column numbers provided as the identifier of the row for the comparison. 
Example: file1: [a, b, c, d], file2: [c, b, a, d], column_number: 0,1
Any row from file1 will be considered the same as any row from file2 if and only if the data in under headers a and b are the same in both rows.

2 rows that are considered the same will be expected to have the same data throughout the row.

Threfore, if there exists a row whose data in the reference column(s) matches that of any of the other file's rows, the 2 rows will be further compared to ensure that all the corresponding values in the rows matches If any of the cell data does not match that of the other row, the 2 rows will be added to the list of exceptions.

At the end of comparing all the rows, the software will generate a new CSV file containing all the exceptions, named Exceptions.csv, in the software's working directory.

## How to use
Clone the git repository.

### Method 1: Using IntelliJ
1. Open the Data Reconciliation Software project using IntelliJ, add a configuration, specifiying the main class and specify the working directory of the program, where all your CSV files are stored.

2. Click on run or `shift + F10`.

3. Enter your first file name into the console.

4. Enter your second file name into the console, the second file may have its headers in a different order as the first file, but the headers must be the same.

5. Enter the column numbers, separated by commas if >1 column number, into the console that you wish to have compared between the 2 files.

6. The program will execute and print out all the exceptions it found. The Exceptions.CSV file will also be generated and it can be found in the working directory specified earlier.

### Method 2: Using the Terminal
1. Open up the terminal and CD into the Data Reconciliation Software folder.

2. From the Data Reconciliation Software folder, cd into src and make sure that main.java is present in the directory.

3. Next, compile the java code by typing:
```
javac main.java
```

4. After compiling the code, to run the program, type:
```
java main
```
5. The program will instruct you to enter the file names, please ensure that the CSV files are located in the same directory as your main.java file if you are running the program through the terminal.

6. Enter the column numbers, seperated by commas if >1 column number, of the first file that you wish to compare to the second file. They will be used as the identifier of the row for the comparison. 2 rows are considered the same if the data in the columns specified are the same, thus they will be expected to have the same data throughout the row.
