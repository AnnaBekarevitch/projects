package expression;

import java.util.Objects;
public class Reverse implements MyExpression {
    final MyExpression value;

    public Reverse(final MyExpression value) {
        this.value = value;
        //System.err.println(this.value);
    }

    private int reverse(int number) {
        int result = 0;
        while (number != 0) {
            result = result * 10 + number % 10;
            number /= 10;
        }
        return result;
    }

    @Override
    public String toMiniString() {
        if (value instanceof MathExpression) {
            return "reverse(" + value.toMiniString() + ")";
        }
        return "reverse " + value.toMiniString();
    }

    @Override
    public boolean equals(final Object another) {
        if (another == null) {
            return false;
        }
        return this.getClass() == another.getClass() && this.toString().equals(another.toString());
    }

    @Override
    public int evaluate(int value) {
        return Math.abs(reverse(this.value.evaluate(value)));
    }

    @Override
    public int evaluate(int value1, int value2, int value3) {
        return reverse(value.evaluate(value1, value2, value3));
    }

    @Override
    public String toString() {
        return "reverse(" + value.toString() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash("reverse", value.hashCode());
    }

    @Override
    public double evaluate(double x) {
        return reverse((int) this.value.evaluate(x));
    }

    public int priority() {
        return 1000;
    }

    public int priority_from_right() {
        return 1000;
    }
}
