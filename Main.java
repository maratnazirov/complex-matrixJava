import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Консольный интерфейс для работы с классом ComplexMatrix.
 * Позволяет создать две матрицы и выполнить над ними операции:
 * сложение, умножение, деление, транспонирование, вычисление определителя.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private static ComplexMatrix matrixA;
    private static ComplexMatrix matrixB;

    public static void main(String[] args) {
        System.out.println(" Работа с матрицами комплексных чисел ");
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Выберите пункт меню: ");
            switch (choice) {
                case 1:
                    matrixA = readMatrix("A");
                    break;
                case 2:
                    matrixB = readMatrix("B");
                    break;
                case 3:
                    printMatrix("A", matrixA);
                    break;
                case 4:
                    printMatrix("B", matrixB);
                    break;
                case 5:
                    performOperation("сложение (A + B)");
                    break;
                case 6:
                    performOperation("умножение (A * B)");
                    break;
                case 7:
                    performOperation("деление (A / B)");
                    break;
                case 8:
                    transposeMenu();
                    break;
                case 9:
                    determinantMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Завершение работы.");
                    break;
                default:
                    System.out.println("Неизвестный пункт меню, попробуйте снова.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Создать/задать матрицу A");
        System.out.println("2. Создать/задать матрицу B");
        System.out.println("3. Показать матрицу A");
        System.out.println("4. Показать матрицу B");
        System.out.println("5. Сложить A + B");
        System.out.println("6. Умножить A * B");
        System.out.println("7. Разделить A / B (A * B^-1)");
        System.out.println("8. Транспонировать матрицу");
        System.out.println("9. Вычислить определитель матрицы");
        System.out.println("0. Выход");
    }

    private static ComplexMatrix readMatrix(String name) {
        int rows = readPositiveInt("Введите количество строк матрицы " + name + ": ");
        int cols = readPositiveInt("Введите количество столбцов матрицы " + name + ": ");
        ComplexMatrix matrix = new ComplexMatrix(rows, cols);
        System.out.println("Ввод элементов матрицы " + name
                + " (для каждого элемента указывается действительная и мнимая часть):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double re = readDouble("  [" + i + "][" + j + "] действительная часть: ");
                double im = readDouble("  [" + i + "][" + j + "] мнимая часть: ");
                matrix.set(i, j, new ComplexNumber(re, im));
            }
        }
        System.out.println("Матрица " + name + " сохранена.");
        return matrix;
    }

    private static void printMatrix(String name, ComplexMatrix matrix) {
        if (matrix == null) {
            System.out.println("Матрица " + name + " ещё не задана.");
            return;
        }
        System.out.println("Матрица " + name + ":");
        matrix.print();
    }

    private static void performOperation(String description) {
        if (matrixA == null || matrixB == null) {
            System.out.println("Сначала задайте обе матрицы (A и B).");
            return;
        }
        try {
            ComplexMatrix result;
            if (description.startsWith("сложение")) {
                result = matrixA.add(matrixB);
            } else if (description.startsWith("умножение")) {
                result = matrixA.multiply(matrixB);
            } else {
                result = matrixA.divide(matrixB);
            }
            System.out.println("Результат операции \"" + description + "\":");
            result.print();
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void transposeMenu() {
        ComplexMatrix chosen = chooseMatrix();
        if (chosen == null) return;
        ComplexMatrix result = chosen.transpose();
        System.out.println("Транспонированная матрица:");
        result.print();
    }

    private static void determinantMenu() {
        ComplexMatrix chosen = chooseMatrix();
        if (chosen == null) return;
        try {
            ComplexNumber det = chosen.determinant();
            System.out.println("Определитель: " + det);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static ComplexMatrix chooseMatrix() {
        System.out.print("Какую матрицу использовать? (A/B): ");
        String answer = scanner.next().trim().toUpperCase();
        if (answer.equals("A")) {
            if (matrixA == null) {
                System.out.println("Матрица A ещё не задана.");
                return null;
            }
            return matrixA;
        } else if (answer.equals("B")) {
            if (matrixB == null) {
                System.out.println("Матрица B ещё не задана.");
                return null;
            }
            return matrixB;
        } else {
            System.out.println("Некорректный выбор.");
            return null;
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Ошибка: число должно быть положительным.");
        }
    }
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода: введите целое число.");
                scanner.next();
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода: введите число.");
                scanner.next();
            }
        }
    }
}
