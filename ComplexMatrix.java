import java.util.Random;

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

    public ComplexMatrix subtract(ComplexMatrix other) {
        checkSameSize(other, "вычитания");
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j].subtract(other.data[i][j]);
            }
        }
        return result;
    }

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

    public ComplexMatrix divide(ComplexMatrix other) {
        if (other.rows != other.cols) {
            throw new IllegalArgumentException("Делитель должен быть квадратной матрицей");
        }
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Несовместимые размеры для деления матриц");
        }
        return this.multiply(other.inverse());
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }
        return result;
    }

    // Определитель рекурсивным разложением по первой строке
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

    // Обращение матрицы методом Гаусса-Жордана с выбором ведущего элемента
    public ComplexMatrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException("Обратная матрица определена только для квадратных матриц");
        }
        int n = rows;

        // Строим расширенную матрицу [A | E]
        ComplexNumber[][] aug = new ComplexNumber[n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                aug[i][j] = data[i][j];
            }
            for (int j = 0; j < n; j++) {
                aug[i][n + j] = (i == j) ? ComplexNumber.ONE : ComplexNumber.ZERO;
            }
        }

        for (int col = 0; col < n; col++) {
            // Ищем строку с максимальным по модулю элементом в текущем столбце
            int pivotRow = col;
            double maxMag = aug[col][col].magnitude();
            for (int r = col + 1; r < n; r++) {
                double mag = aug[r][col].magnitude();
                if (mag > maxMag) {
                    maxMag = mag;
                    pivotRow = r;
                }
            }
            if (maxMag == 0) {
                throw new ArithmeticException("Матрица вырождена, обратной не существует");
            }

            // Меняем строки местами
            ComplexNumber[] tmp = aug[col];
            aug[col] = aug[pivotRow];
            aug[pivotRow] = tmp;

            // Нормализуем ведущую строку
            ComplexNumber pivot = aug[col][col];
            for (int j = 0; j < 2 * n; j++) {
                aug[col][j] = aug[col][j].divide(pivot);
            }

            // Зануляем остальные строки в этом столбце
            for (int r = 0; r < n; r++) {
                if (r == col) continue;
                ComplexNumber factor = aug[r][col];
                if (factor.isZero()) continue;
                for (int j = 0; j < 2 * n; j++) {
                    aug[r][j] = aug[r][j].subtract(factor.multiply(aug[col][j]));
                }
            }
        }

        // Извлекаем правую половину [E | A^-1]
        ComplexMatrix result = new ComplexMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.data[i][j] = aug[i][n + j];
            }
        }
        return result;
    }

    private void checkSameSize(ComplexMatrix other, String operation) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException(
                    "Матрицы должны быть одинакового размера для " + operation);
        }
    }

    // Генерация случайной матрицы с целыми числами в диапазоне [minVal, maxVal]
    public static ComplexMatrix random(int rows, int cols, int minVal, int maxVal) {
        Random rnd = new Random();
        ComplexMatrix matrix = new ComplexMatrix(rows, cols);
        int range = Math.max(1, maxVal - minVal + 1);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double re = rnd.nextInt(range) + minVal;
                double im = rnd.nextInt(range) + minVal;
                matrix.set(i, j, new ComplexNumber(re, im));
            }
        }
        return matrix;
    }

    @Override
    public String toString() {
        // Подготавливаем строковое представление всех элементов и считаем ширину каждого столбца
        String[][] str = new String[rows][cols];
        int[] colWidths = new int[cols];

        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                str[i][j] = data[i][j].toString();
                if (str[i][j].length() > colWidths[j]) {
                    colWidths[j] = str[i][j].length();
                }
            }
        }

        // Формируем красивую ровную таблицу
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append("| ");
            for (int j = 0; j < cols; j++) {
                sb.append(String.format("%" + colWidths[j] + "s", str[i][j]));
                if (j < cols - 1) {
                    sb.append("   ");
                }
            }
            sb.append(" |\n");
        }
        return sb.toString();
    }

    public void print() {
        System.out.print(this);
    }
}