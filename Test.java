public class Test {
    public static void main(String[] args) {
        ComplexNumber a = new ComplexNumber(1, 2);
        ComplexNumber b = new ComplexNumber(3, 4);

        ComplexNumber sum = a.add(b);
        System.out.println("a + b = " + sum.getRe() + " + " + sum.getIm() + "i");

        ComplexNumber minus = a.subtract(b);
        System.out.println("a - b = " + minus.getRe() + " + " + minus.getIm() + "i");

        ComplexNumber mult = a.multiply(b);
        System.out.println("a * b = " + mult.getRe() + " + " + mult.getIm() + "i");

        ComplexNumber i = new ComplexNumber(0, 1);
        ComplexNumber ii = i.multiply(i);
        System.out.println("i * i = " + ii.getRe() + " + " + ii.getIm() + "i");

        ComplexNumber p = new ComplexNumber(1, 1);
        ComplexNumber q = new ComplexNumber(1, -1);
        ComplexNumber pq = p.multiply(q);
        System.out.println("(1+i)(1-i) = " + pq.getRe() + " + " + pq.getIm() + "i");
    }
}