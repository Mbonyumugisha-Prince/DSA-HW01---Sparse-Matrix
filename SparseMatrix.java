import java.io.*;
import java.util.*;

public class SparseMatrix {
    private int numRows;
    private int numCols;
    private Map<String, Integer> elements;

    public SparseMatrix(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.elements = new HashMap<>();
    }

    public static SparseMatrix fromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        }

        if (lines.size() < 2) {
            throw new IllegalArgumentException("Invalid file format: Not enough lines");
        }

        int numRows = parseHeader(lines.get(0), "rows");
        int numCols = parseHeader(lines.get(1), "cols");

        SparseMatrix matrix = new SparseMatrix(numRows, numCols);

        for (int i = 2; i < lines.size(); i++) {
            String[] parts = lines.get(i).replaceAll("[()]", "").split(",");
            if (parts.length != 3) throw new IllegalArgumentException("Invalid entry: " + lines.get(i));

            int row = Integer.parseInt(parts[0].trim());
            int col = Integer.parseInt(parts[1].trim());
            int val = Integer.parseInt(parts[2].trim());

            matrix.setElement(row, col, val);
        }

        return matrix;
    }

    private static int parseHeader(String line, String key) {
        if (!line.startsWith(key + "=")) {
            throw new IllegalArgumentException("Expected " + key + " definition, got: " + line);
        }
        return Integer.parseInt(line.split("=")[1].trim());
    }

    public SparseMatrix add(SparseMatrix other) {
        checkSameDimensions(other);
        SparseMatrix result = new SparseMatrix(numRows, numCols);

        for (Map.Entry<String, Integer> entry : elements.entrySet()) {
            result.elements.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry : other.elements.entrySet()) {
            result.elements.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }

        return result;
    }

    public SparseMatrix subtract(SparseMatrix other) {
        checkSameDimensions(other);
        SparseMatrix result = new SparseMatrix(numRows, numCols);

        for (Map.Entry<String, Integer> entry : elements.entrySet()) {
            result.elements.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Integer> entry : other.elements.entrySet()) {
            result.elements.merge(entry.getKey(), -entry.getValue(), Integer::sum);
        }

        return result;
    }

    public SparseMatrix multiply(SparseMatrix other) {
        if (this.numCols != other.numRows) {
            throw new IllegalArgumentException("Matrix dimension mismatch for multiplication");
        }

        SparseMatrix result = new SparseMatrix(this.numRows, other.numCols);

        for (Map.Entry<String, Integer> aEntry : this.elements.entrySet()) {
            String[] aIndices = aEntry.getKey().split(",");
            int i = Integer.parseInt(aIndices[0]);
            int k = Integer.parseInt(aIndices[1]);
            int aVal = aEntry.getValue();

            for (int j = 0; j < other.numCols; j++) {
                int bVal = other.getElement(k, j);
                if (bVal != 0) {
                    int currentVal = result.getElement(i, j);
                    result.setElement(i, j, currentVal + aVal * bVal);
                }
            }
        }

        return result;
    }

    public int getElement(int row, int col) {
        return elements.getOrDefault(row + "," + col, 0);
    }

    public void setElement(int row, int col, int value) {
        checkBounds(row, col);
        String key = row + "," + col;
        if (value == 0) {
            elements.remove(key);
        } else {
            elements.put(key, value);
        }
    }

    private void checkBounds(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            throw new IndexOutOfBoundsException("Invalid index (" + row + ", " + col + ")");
        }
    }

    private void checkSameDimensions(SparseMatrix other) {
        if (this.numRows != other.numRows || this.numCols != other.numCols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("rows=").append(numRows).append("\n");
        sb.append("cols=").append(numCols).append("\n");

        List<String> sortedKeys = new ArrayList<>(elements.keySet());
        sortedKeys.sort(Comparator.comparingInt((String k) -> {
            String[] parts = k.split(",");
            return Integer.parseInt(parts[0]) * numCols + Integer.parseInt(parts[1]);
        }));

        for (String key : sortedKeys) {
            String[] parts = key.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            sb.append("(").append(row).append(", ").append(col).append(", ").append(elements.get(key)).append(")\n");
        }

        return sb.toString();
    }
}

 
