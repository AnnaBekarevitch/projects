package expression;

import java.util.Objects;

public class UnaryMinus implements MyExpression {
    MyExpression value;

    public UnaryMinus(final MyExpression value) {
        this.value = value;
    }

    @Override
    public String toMiniString() {
        if (value instanceof MathExpression) {
            return "-(" + value.toMiniString() + ")";
        }
        return "- " + value.toMiniString();
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
        return -this.value.evaluate(value);
    }

    @Override
    public int evaluate(int value1, int value2, int value3) {
        return -value.evaluate(value1, value2, value3);
    }

    @Override
    public String toString() {
        return "-(" + value.toString() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash("-", value.hashCode());
    }

    @Override
    public double evaluate(double x) {
        return -this.value.evaluate(x);
    }

    public int priority() {
        return 1000;
    }

    public int priority_from_right() {
        return 1000;
    }
}
