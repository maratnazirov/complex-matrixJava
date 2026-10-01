public class Test {
    public static void main(String[] args) {
        ComplexNumber a = new ComplexNumber(1, 2);
        ComplexNumber b = new ComplexNumber(3, 4);

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a + b = " + a.add(b));
        System.out.println("a - b = " + a.subtract(b));
        System.out.println("a * b = " + a.multiply(b));
        System.out.println("a / b = " + a.divide(b));

        System.out.println("(3,-4) = " + new ComplexNumber(3, -4));
        System.out.println("(5,0)  = " + new ComplexNumber(5, 0));
        System.out.println("(0,2)  = " + new ComplexNumber(0, 2));
        System.out.println("(0,0)  = " + new ComplexNumber(0, 0));

        try {
            a.divide(new ComplexNumber(0, 0));
            System.out.println("ОШИБКА: исключения не было!");
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}