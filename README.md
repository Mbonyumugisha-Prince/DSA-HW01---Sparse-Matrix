Sparse Matrix Calculator
Overview
This Java application provides a console-based interface to perform arithmetic operations—addition, subtraction, and multiplication—on sparse matrices. It reads matrix data from files, performs the selected operation, and optionally saves the result to a file.

How It Works
The main class to run the application is MatrixAppMain.

It interacts with the user via command-line prompts.

Users provide the paths to two matrix files.

The application loads the matrices, performs the selected operation, and displays the result.

Users can choose to save the result to a file.

Matrix File Format
Each matrix must be stored in a file with the following format:

makefile
Copy
Edit
rows=3
cols=3
(0, 1, 5)
(1, 2, 8)
(2, 0, 4)
The first two lines define the number of rows and columns.

Each subsequent line represents a non-zero element in the format (row, column, value).

How to Run
Compile the application:

bash
Copy
Edit
javac MatrixAppMain.java SparseMatrix.java
Run the application:

bash
Copy
Edit
java MatrixAppMain
Follow the on-screen instructions to:

Select an operation (Add, Subtract, Multiply).

Enter paths for the two matrix files.

View and optionally save the result.

Example Session
sql
Copy
Edit
Sparse Matrix Calculator
=========================
1. Add
2. Subtract
3. Multiply
Choose operation (1-3): 1
Enter path for the first matrix file: matrix1.txt
Enter path for the second matrix file: matrix2.txt

Addition Result:
rows=3
cols=3
(0, 1, 7)
(1, 2, 10)

Save result to file? (y/n): y
Enter file path: result.txt
Result saved to result.txt
Notes
All matrix operations are performed using the SparseMatrix class.

The application handles errors such as invalid input formats, dimension mismatches, and file I/O issues.

