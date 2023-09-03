package expression;

public class Divide extends MathExpression {

    public Divide(final MyExpression first, final MyExpression second) {
        super(first, second);
        sign = "/";
    }

    public int priority(){
        return 2;
    }

    public int priority_from_right(){
        return 1;
    }

    public boolean associative(){
        return false;
    }

    @Override
    public int evaluate(int value) {
        return getFirst().evaluate(value) / getSecond().evaluate(value);
    }

    @Override
    public int evaluate(int value1, int value2, int value3) {
        return getFirst().evaluate(value1, value2, value3) / getSecond().evaluate(value1, value2, value3);
    }
    
    @Override
    public double evaluate(double value) {
        return getFirst().evaluate(value) / getSecond().evaluate(value);
    }
}
