package expression;

import java.math.BigInteger;

public class Subtract extends MathExpression {
    public Subtract(final MyExpression first, final MyExpression second) {
        super(first, second);
        sign = "-";
    }
    public int priority(){
        return 0;
    }
    public int priority_from_right(){
        return 0;
    }
    public boolean associative(){
        return false;
    }

    @Override
    public int evaluate(final int value1, final int value2, final int value3) {
        return getFirst().evaluate(value1, value2, value3) - getSecond().evaluate(value1, value2, value3);
    }
    @Override
    public <T extends Number> T evaluate(final int value1, final int value2, final int value3, Executor<T> exec) throws EvaluteException {
        return exec.sub(getFirst().evaluate(value1, value2, value3, exec), getSecond().evaluate(value1, value2, value3, exec));
    }
}
