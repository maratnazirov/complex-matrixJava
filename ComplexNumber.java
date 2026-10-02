import java.util.Locale;

public class ComplexNumber {

    private final double re;
    private final double im;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static final ComplexNumber ZERO = new ComplexNumber(0, 0);
    public static final ComplexNumber ONE = new ComplexNumber(1, 0);


    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.re + other.re, this.im + other.im);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.re - other.re, this.im - other.im);
    }
    
    public ComplexNumber multiply(ComplexNumber other) {
        double newRe = this.re * other.re - this.im * other.im;
        double newIm = this.re * other.im + this.im * other.re;
        return new ComplexNumber(newRe, newIm);
    }

    public ComplexNumber divide(ComplexNumber other) {
        double denominator = other.re * other.re + other.im * other.im;
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль: делитель равен комплексному нулю");
        }
        double newRe = (this.re * other.re + this.im * other.im) / denominator;
        double newIm = (this.im * other.re - this.re * other.im) / denominator;
        return new ComplexNumber(newRe, newIm);
    }

    @Override
    public String toString() {
        if (im == 0) {
            return formatNumber(re);
        }
        if (re == 0) {
            return formatNumber(im) + "i";
        }
        String sign; 

        if (im < 0) {
            sign = "-"; 
        } else {
            sign = "+"; 
        }
        return formatNumber(re) + sign + formatNumber(Math.abs(im)) + "i";
    }

    private String formatNumber(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        }
        return String.format(Locale.US, "%.3f", value);
    }
}