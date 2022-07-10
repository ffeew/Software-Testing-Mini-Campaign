# Software-Testing-Mini-Campaign

## Data Reconciliation Software
The data reconciliation software works by capturing mismatches in 2 different CSV files.

### How it works
For it to work, 2 different CSV files with the same columns and in the same order needs to be inputted to the software.

The sofware will then compare each row of the CSV file with all the rows of the other CSV file.

If there exists a row that is a close match to the other row, in other words, only 1 cell has a different value between the 2 rows, the 2 rows will be added to the list of exceptions.

At the end of comparing all the rows, the software will generate a new CSV file containing all the exceptions, named Exceptions.csv, in the software's working directory.

### How to use
Clone the git repository.

#### Method 1: Using IntelliJ
Open the Data Reconciliation Software project using IntelliJ, add a configuration, specifiying the main class and specify the working directory of the program, where all your CSV files are stored.

Click on run.

Enter your first file name into the console.

Enter your second file name into the console.

The program will execute and print out all the exceptions it found. The Exceptions.CSV file will also be generated and it can be found in the working directory specified earlier.

#### Method 2: Using the Terminal
Open up the terminal and CD into the Data Reconciliation Software folder.

From the Data Reconciliation Software folder, cd into src and make sure that main.java is present in the directory.

Next, compile the java code by typing:
```
javac main.java
```

After compiling the code, to run the program, type:
```
java main
```
The program will instruct you to enter your file name, please ensure that the CSV files are located in the same directory as your main.java file if you are running the program through the terminal.
