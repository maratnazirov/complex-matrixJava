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
        // ===== День 3: конструктор, add, transpose =====

        // Новая матрица должна быть заполнена нулями
        ComplexMatrix z = new ComplexMatrix(2, 3);
        System.out.println("Нулевая 2x3:");
        z.print();

        // A = [[1, 2+i], [3, 4-i]]
        ComplexMatrix a = new ComplexMatrix(2, 2);
        a.set(0, 0, new ComplexNumber(1, 0));
        a.set(0, 1, new ComplexNumber(2, 1));
        a.set(1, 0, new ComplexNumber(3, 0));
        a.set(1, 1, new ComplexNumber(4, -1));

        // B = [[1, i], [2, 3]]
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

        // C = [[1, 2, 3], [4, 5, 6]] (2x3)
        ComplexMatrix c = real(new double[][]{{1, 2, 3}, {4, 5, 6}});
        System.out.println("C (2x3):");
        c.print();
        System.out.println("C транспонированная (3x2):");
        c.transpose().print();

        // Проверки ошибок из дня 3
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

        // ===== День 4: multiply =====
        System.out.println();
        System.out.println("----- Умножение -----");

        // D = [[1, 2], [3, 4], [5, 6]] (3x2)
        ComplexMatrix d = real(new double[][]{{1, 2}, {3, 4}, {5, 6}});

        // Неквадратные: (2x3) * (3x2) = (2x2), и наоборот (3x3)
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
        System.out.println("A * E (должно быть равно A):");
        a.multiply(e).print();

        // Несовместимые размеры: (2x3) * (2x2)
        try {
            c.multiply(a);
            System.out.println("ОШИБКА: умножение 2x3 на 2x2 должно быть запрещено!");
        } catch (IllegalArgumentException ex) {
            System.out.println("Поймано: " + ex.getMessage());
        }
    }
}
