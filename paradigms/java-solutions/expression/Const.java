package expression;

import java.math.BigInteger;
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
    public String toString() {
        // return String.format("%.9f", value.intValue());
        return String.valueOf(value.longValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public int priority() {
        return 1000;
    }

    public int priority_from_right() {
        return 1000;
    }

    @Override
    public <T extends Number> T evaluate(final int value1, final int value2, final int value3, Executor<T> exec) throws EvaluteException {
        return exec.constant(value);
    }

    @Override
    public int evaluate(final int value1, final int value2, final int value3) {
        return value.intValue();
    }


}
