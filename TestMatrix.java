
public class TestMatrix {
    public static void main(String[] args) {
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

        // Неквадратная 2x3: заполняем числами 1..6
        ComplexMatrix c = new ComplexMatrix(2, 3);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                c.set(i, j, new ComplexNumber(i * 3 + j + 1, 0));
            }
        }
        System.out.println("C (2x3):");
        c.print();
        System.out.println("C транспонированная (3x2):");
        c.transpose().print();

        // Проверки ошибок
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
    }
}