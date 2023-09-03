package expression;

import com.sun.jdi.Value;

import java.math.BigInteger;
import java.util.Objects;

public class UnaryMinus implements MyExpression {
    final MyExpression value;

    public UnaryMinus(final MyExpression value) {
        this.value = value;
    }

    @Override
    public String toMiniString() {
        if (value instanceof MathExpression) {
            return toString();
        }
        return "-" + value.toString();
    }

    @Override
    public boolean equals(final Object another) {
        if (another == null) {
            return false;
        }
        return this.getClass() == another.getClass() && this.toString().equals(another.toString());
    }
    protected boolean overflowTest(final Integer value1) {
        long a = Integer.valueOf(value1);
        return Integer.MIN_VALUE <= -a && -a <= Integer.MAX_VALUE;
    }

    @Override
    public String toString() {
        return "-(" + value.toString() + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash("-", value.hashCode());
    }

    public int priority() {
        return 1000;
    }

    public int priority_from_right() {
        return 1000;
    }

    @Override
    public <T extends Number> T evaluate(final int value1, final int value2, final int value3, Executor<T> exec) throws EvaluteException {
        return exec.unary(value.evaluate(value1, value2, value3, exec));
    }
    @Override
    public int evaluate(final int value1, final int value2, final int value3) {
        return -value.evaluate(value1, value2, value3);
    }
}
