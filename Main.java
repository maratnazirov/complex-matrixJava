import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    private static ComplexMatrix matrixA;
    private static ComplexMatrix matrixB;

    private static ComplexMatrix lastMatrixResult;
    private static ComplexNumber lastScalarResult;
    private static String lastOperation = "";
    private static String lastChosenName = "";

    public static void main(String[] args) {
        System.out.println(" Калькулятор матриц комплексных чисел ");
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
                    performOperation("вычитание (A - B)");
                    break;
                case 7:
                    performOperation("умножение (A * B)");
                    break;
                case 8:
                    performOperation("деление (A / B)");
                    break;
                case 9:
                    transposeMenu();
                    break;
                case 10:
                    determinantMenu();
                    break;
                case 11:
                    showLastResult();
                    break;
                case 12:
                    copyLastResult();
                    break;
                case 0:
                    running = false;
                    System.out.println("Работа завершена.");
                    break;
                default:
                    System.out.println("Неизвестный пункт меню, попробуйте снова.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Задать матрицу A");
        System.out.println("2. Задать матрицу B");
        System.out.println("3. Показать матрицу A");
        System.out.println("4. Показать матрицу B");
        System.out.println("5. Сложить (A + B)");
        System.out.println("6. Вычесть (A - B)");
        System.out.println("7. Умножить (A * B)");
        System.out.println("8. Разделить (A / B)");
        System.out.println("9. Транспонировать матрицу");
        System.out.println("10. Вычислить определитель");
        System.out.println("11. Показать последний результат");
        System.out.println("12. Скопировать последний результат в A или B");
        System.out.println("0. Выход");
    }

    private static ComplexMatrix readMatrix(String name) {
        int rows = readPositiveInt("Введите количество строк матрицы " + name + ": ");
        int cols = readPositiveInt("Введите количество столбцов матрицы " + name + ": ");

        System.out.println("Способ задания матрицы " + name + ":");
        System.out.println("  1. Ввести вручную");
        System.out.println("  2. Сгенерировать случайными числами");
        int mode = readInt("Выберите вариант (1 или 2): ");
        if (mode == 2) {
            int maxVal = readPositiveInt("Максимальное число по модулю (например, 5 или 10): ");
            ComplexMatrix matrix = ComplexMatrix.random(rows, cols, -maxVal, maxVal);
            System.out.println("Сгенерирована матрица " + name + ":");
            matrix.print();
            return matrix;
        }

        ComplexMatrix matrix = new ComplexMatrix(rows, cols);
        System.out.println("Ввод элементов матрицы " + name + " (Re и Im):");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double re = readDouble("  [" + i + "][" + j + "] Re: ");
                double im = readDouble("  [" + i + "][" + j + "] Im: ");
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
            System.out.println("Ошибка: сначала нужно задать обе матрицы (A и B).");
            return;
        }
        try {
            ComplexMatrix result;
            if (description.startsWith("сложение")) {
                result = matrixA.add(matrixB);
            } else if (description.startsWith("вычитание")) {
                result = matrixA.subtract(matrixB);
            } else if (description.startsWith("умножение")) {
                result = matrixA.multiply(matrixB);
            } else {
                result = matrixA.divide(matrixB);
            }

            // Сохраняем в буфер
            lastMatrixResult = result;
            lastOperation = description;

            System.out.println("Результат (" + description + "):");
            result.print();
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void transposeMenu() {
        ComplexMatrix chosen = chooseMatrix();
        if (chosen == null) return;

        ComplexMatrix result = chosen.transpose();
        lastMatrixResult = result;
        lastOperation = "транспонирование матрицы " + lastChosenName;

        System.out.println("Транспонированная матрица " + lastChosenName + ":");
        result.print();
    }

    private static void determinantMenu() {
        ComplexMatrix chosen = chooseMatrix();
        if (chosen == null) return;

        try {
            ComplexNumber det = chosen.determinant();
            lastScalarResult = det;
            lastOperation = "определитель матрицы " + lastChosenName;
            System.out.println("det(" + lastChosenName + ") = " + det);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void showLastResult() {
        if (lastMatrixResult == null && lastScalarResult == null) {
            System.out.println("История пуста: пока не выполнено ни одной операции.");
            return;
        }

        System.out.println("Последняя операция: " + lastOperation);
        if (lastOperation.startsWith("определитель")) {
            System.out.println("Результат: " + lastScalarResult);
        } else if (lastMatrixResult != null) {
            System.out.println("Результат:");
            lastMatrixResult.print();
        }
    }

    private static void copyLastResult() {
        if (lastOperation.isEmpty()) {
            System.out.println("История пуста: пока не выполнено ни одной операции.");
            return;
        }

        if (lastOperation.startsWith("определитель")) {
            System.out.println("Последний результат — это скаляр (" + lastOperation + " = " + lastScalarResult + ").");
            System.out.println("В матрицу A или B можно копировать только матричные результаты (сложение, умножение и т.д.).");
            return;
        }

        if (lastMatrixResult == null) {
            System.out.println("Нет сохраненного матричного результата для копирования.");
            return;
        }

        System.out.print("В какую матрицу скопировать результат? (A/B): ");
        String target = scanner.next().trim().toUpperCase();

        if (target.equals("A")) {
            matrixA = lastMatrixResult;
            System.out.println("Результат успешно скопирован в матрицу A.");
        } else if (target.equals("B")) {
            matrixB = lastMatrixResult;
            System.out.println("Результат успешно скопирован в матрицу B.");
        } else {
            System.out.println("Некорректный ввод: выберите A или B.");
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
            lastChosenName = "A";
            return matrixA;
        } else if (answer.equals("B")) {
            if (matrixB == null) {
                System.out.println("Матрица B ещё не задана.");
                return null;
            }
            lastChosenName = "B";
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
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число.");
                scanner.next();
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите число.");
                scanner.next();
            }
        }
    }
}
