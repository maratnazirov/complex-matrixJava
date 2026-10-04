public class TestMatrix {

    // Вспомогательный метод: собирает матрицу из обычных (действительных) чисел
    static ComplexMatrix real(double[][] values) {
        ComplexMatrix m = new ComplexMatrix(values.length, values[0].length);
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                m.set(i, j, new ComplexNumber(values[i][j], 0));
            }
        }
        return m;
    }

    public static void main(String[] args) {
        // Тесты конструктора, сложения и транспонирования

        // Новая матрица должна быть заполнена нулями
        ComplexMatrix z = new ComplexMatrix(2, 3);
        System.out.println("Нулевая 2x3:");
        z.print();

        // Матрица A: первая строка 1 и 2+i, вторая строка 3 и 4-i
        ComplexMatrix a = new ComplexMatrix(2, 2);
        a.set(0, 0, new ComplexNumber(1, 0));
        a.set(0, 1, new ComplexNumber(2, 1));
        a.set(1, 0, new ComplexNumber(3, 0));
        a.set(1, 1, new ComplexNumber(4, -1));

        // Матрица B: первая строка 1 и i, вторая строка 2 и 3
        ComplexMatrix b = new ComplexMatrix(2, 2);
        b.set(0, 0, new ComplexNumber(1, 0));
        b.set(0, 1, new ComplexNumber(0, 1));
        b.set(1, 0, new ComplexNumber(2, 0));
        b.set(1, 1, new ComplexNumber(3, 0));

        System.out.println("A:");
        a.print();
        System.out.println("A + B:");
        a.add(b).print();
        System.out.println("A транспонированная:");
        a.transpose().print();

        // Матрица C размером 2x3: первая строка 1, 2, 3, вторая строка 4, 5, 6
        ComplexMatrix c = real(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println("C (2x3):");
        c.print();
        System.out.println("C транспонированная (3x2):");
        c.transpose().print();

        // Проверки ошибок для сложения и конструктора
        try {
            a.add(c);
            System.out.println("ОШИБКА: сложение 2x2 и 2x3 должно быть запрещено!");
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано: " + e.getMessage());
        }
        try {
            new ComplexMatrix(0, 2);
            System.out.println("ОШИБКА: размер 0 должен быть запрещён!");
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано: " + e.getMessage());
        }

        // Тесты умножения
        System.out.println();
        System.out.println("Умножение:");

        // Матрица D размером 3x2: строки 1 и 2, 3 и 4, 5 и 6
        ComplexMatrix d = real(new double[][]{{1, 2}, {3, 4}, {5, 6}});

        // Неквадратные матрицы: 2x3 на 3x2 дают 2x2, а 3x2 на 2x3 дают 3x3
        System.out.println("C * D (2x2):");
        c.multiply(d).print();
        System.out.println("D * C (3x3):");
        d.multiply(c).print();

        // Комплексные матрицы
        System.out.println("A * B:");
        a.multiply(b).print();

        // Умножение на единичную матрицу не должно менять A
        ComplexMatrix e = new ComplexMatrix(2, 2);
        e.set(0, 0, ComplexNumber.ONE);
        e.set(1, 1, ComplexNumber.ONE);
        System.out.println("A * E (должно получиться A):");
        a.multiply(e).print();

        // Несовместимые размеры: 2x3 на 2x2
        try {
            c.multiply(a);
            System.out.println("ОШИБКА: умножение 2x3 на 2x2 должно быть запрещено!");
        } catch (IllegalArgumentException ex) {
            System.out.println("Поймано: " + ex.getMessage());
        }

        // Тесты определителя
        System.out.println();
        System.out.println("Определитель:");

        System.out.println("1x1 [[5]]: " + real(new double[][]{{5}}).determinant());
        System.out.println("2x2 [[1,2],[3,4]]: " + real(new double[][]{{1, 2}, {3, 4}}).determinant());
        System.out.println("3x3 [[1,2,3],[4,5,6],[7,8,10]]: "
                + real(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 10}}).determinant());
        System.out.println("3x3 вырожденная [[1,2,3],[4,5,6],[7,8,9]]: "
                + real(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}).determinant());
        System.out.println("3x3 единичная: "
                + real(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}).determinant());
        System.out.println("4x4 диагональная 2,3,4,5: "
                + real(new double[][]{{2, 0, 0, 0}, {0, 3, 0, 0}, {0, 0, 4, 0}, {0, 0, 0, 5}}).determinant());

        // Комплексные матрицы A и B из начала файла
        System.out.println("det A: " + a.determinant());
        System.out.println("det B: " + b.determinant());
        System.out.println("det(A*B): " + a.multiply(b).determinant());
        System.out.println("det A * det B: " + a.determinant().multiply(b.determinant()));

        // Неквадратная матрица C (2x3)
        try {
            c.determinant();
            System.out.println("ОШИБКА: для 2x3 должно быть исключение!");
        } catch (IllegalArgumentException ex) {
            System.out.println("Поймано: " + ex.getMessage());
        }
    }
}