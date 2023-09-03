package expression;

import java.util.Objects;

public class Const implements MyExpression {
    Number value;

    public Const(final int value) {
        this.value = value;
    }

    public Const(final double value) {
        this.value = value;
    }

    public Const(final Number value) {
        this.value = value;
    }

    @Override
    public String toMiniString() {
        return toString();
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
        return this.value.intValue();
    }

    @Override
    public int evaluate(int value1, int value2, int value3) {
        return value.intValue();
    }

    @Override
    public String toString() {
        // return String.format("%.9f", value.intValue());
        return String.valueOf(value.longValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public double evaluate(double x) {
        return value.doubleValue();
    }

    public int priority() {
        return 1000;
    }

    public int priority_from_right() {
        return 1000;
    }
}
