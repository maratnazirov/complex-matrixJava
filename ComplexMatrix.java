public class ComplexMatrix {

    private final int rows;
    private final int cols;
    private final ComplexNumber[][] data;

    public ComplexMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Размеры матрицы должны быть положительными");
        }
        this.rows = rows;
        this.cols = cols;
        this.data = new ComplexNumber[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = ComplexNumber.ZERO;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public ComplexNumber get(int i, int j) {
        return data[i][j];
    }

    public void set(int i, int j, ComplexNumber value) {
        data[i][j] = value;
    }

     /** Сложение матриц (размеры должны совпадать). */
    public ComplexMatrix add(ComplexMatrix other) {
        checkSameSize(other, "сложения");
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j].add(other.data[i][j]);
            }
        }
        return result;
    }

    /** Транспонирование матрицы. */
    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }
        return result;
    }

    /** Умножение матриц (число столбцов this должно равняться числу строк other). */
    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException(
                    "Несовместимые размеры для умножения: (" + rows + "x" + cols + ") и ("
                            + other.rows + "x" + other.cols + ")");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                ComplexNumber sum = ComplexNumber.ZERO;
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][k].multiply(other.data[k][j]));
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    /** Определитель матрицы (только для квадратных матриц), рекурсивное разложение по первой строке. */
    public ComplexNumber determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Определитель определён только для квадратных матриц");
        }
        return determinantRecursive(data, rows);
    }

    private static ComplexNumber determinantRecursive(ComplexNumber[][] m, int n) {
        if (n == 1) {
            return m[0][0];
        }
        if (n == 2) {
            return m[0][0].multiply(m[1][1]).subtract(m[0][1].multiply(m[1][0]));
        }
        ComplexNumber result = ComplexNumber.ZERO;
        for (int col = 0; col < n; col++) {
            ComplexNumber[][] minor = getMinor(m, 0, col, n);
            ComplexNumber term = m[0][col].multiply(determinantRecursive(minor, n - 1));
            if (col % 2 == 1) {
                term = ComplexNumber.ZERO.subtract(term);
            }
            result = result.add(term);
        }
        return result;
    }

    private static ComplexNumber[][] getMinor(ComplexNumber[][] m, int skipRow, int skipCol, int n) {
        ComplexNumber[][] minor = new ComplexNumber[n - 1][n - 1];
        int mi = 0;
        for (int i = 0; i < n; i++) {
            if (i == skipRow) continue;
            int mj = 0;
            for (int j = 0; j < n; j++) {
                if (j == skipCol) continue;
                minor[mi][mj] = m[i][j];
                mj++;
            }
            mi++;
        }
        return minor;
    }

    private void checkSameSize(ComplexMatrix other, String operation) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException(
                    "Матрицы должны быть одинакового размера для операции: " + operation);
        }
    }
    
    public void print() {
        for (int i = 0; i < rows; i++) {
            StringBuilder sb = new StringBuilder("| ");
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j].toString());
                if (j < cols - 1) sb.append("\t");
            }
            sb.append(" |");
            System.out.println(sb);
        }
    }
}