import java.io.*;
import java.util.Scanner;

public class MatrixAppMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sparse Matrix Calculator");
        System.out.println("=========================");
        System.out.println("1. Add");
        System.out.println("2. Subtract");
        System.out.println("3. Multiply");
        System.out.print("Choose operation (1-3): ");
        String choice = scanner.nextLine();

        if (!choice.matches("[1-3]")) {
            System.out.println("Invalid choice.");
            return;
        }

        SparseMatrix m1 = readMatrix(scanner, "first");
        SparseMatrix m2 = readMatrix(scanner, "second");

        SparseMatrix result = null;
        String operation = "";

        try {
            switch (choice) {
                case "1" -> {
                    result = m1.add(m2);
                    operation = "Addition";
                }
                case "2" -> {
                    result = m1.subtract(m2);
                    operation = "Subtraction";
                }
                case "3" -> {
                    result = m1.multiply(m2);
                    operation = "Multiplication";
                }
            }
        } catch (Exception e) {
            System.out.println("Operation failed: " + e.getMessage());
            return;
        }

        System.out.println("\n" + operation + " Result:\n" + result);

        System.out.print("Save result to file? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Enter file path: ");
            String outPath = scanner.nextLine().trim();
            try (PrintWriter out = new PrintWriter(outPath)) {
                out.print(result.toString());
                System.out.println("Result saved to " + outPath);
            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static SparseMatrix readMatrix(Scanner scanner, String label) {
        System.out.print("Enter path for the " + label + " matrix file: ");
        String path = scanner.nextLine().trim();
        try {
            return SparseMatrix.fromFile(path);
        } catch (Exception e) {
            System.out.println("Failed to load " + label + " matrix: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }
}
