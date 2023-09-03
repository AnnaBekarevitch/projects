package expression;

import java.math.BigInteger;

public class Add extends MathExpression  {

    public Add(final MyExpression first, final MyExpression second) {
        super(first, second);
        sign = "+";
    }

    @Override
    public int evaluate(final int value1, final int value2, final int value3) {
        return getFirst().evaluate(value1, value2, value3) + getSecond().evaluate(value1, value2, value3);
    }

    public int priority(){
        return 0;
    }

    public int priority_from_right(){
        return 0;
    }

    public boolean associative(){
        return true;
    }

    @Override
    public <T extends Number> T evaluate(final int value1, final int value2, final int value3, Executor<T> exec) throws EvaluteException {
        return exec.add(getFirst().evaluate(value1, value2, value3, exec), getSecond().evaluate(value1, value2, value3, exec));
    }
}
