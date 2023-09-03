package expression;
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
    public int evaluate(int value) {
        return getFirst().evaluate(value) - getSecond().evaluate(value);
    }
    
    @Override
    public double evaluate(double value) {
        return getFirst().evaluate(value) - getSecond().evaluate(value);
    }
    
    @Override
    public int evaluate(int value1, int value2, int value3) {
        return getFirst().evaluate(value1, value2, value3) - getSecond().evaluate(value1, value2, value3);
    }
}
