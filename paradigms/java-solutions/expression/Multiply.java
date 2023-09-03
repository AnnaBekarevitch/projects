package expression;

import java.math.BigInteger;

public class Multiply extends MathExpression {
    //final char sign = '*';

    public Multiply(final MyExpression first, final MyExpression second) {
        super(first, second);
        sign = "*";
    }

    public int priority(){
        return 2;
    }

    public int priority_from_right(){
        return 2;
    }

    public boolean associative(){
        return true;
    }

    @Override
    public int evaluate(final int value1, final int value2, final int value3) {
        return getFirst().evaluate(value1, value2, value3) * getSecond().evaluate(value1, value2, value3);
    }

    @Override
    public <T extends Number> T evaluate(final int value1, final int value2, final int value3, Executor<T> exec) throws EvaluteException {
        return exec.mul(getFirst().evaluate(value1, value2, value3, exec), getSecond().evaluate(value1, value2, value3, exec));
    }
}
