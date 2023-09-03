package expression;

public class Gcd extends MathExpression {
    int gcd(int a, int b){
        return b != 0 ? gcd(b, a % b) : a;
    }
    public Gcd(final MyExpression first, final MyExpression second) {
        super(first, second);
        sign = "gcd";
    }

    public int priority(){
        return -2;
    }

    public int priority_from_right(){
        return -2;
    }

    public boolean associative(){
        return true;
    }

    @Override
    public int evaluate(int value) {
        return Math.abs(gcd(getFirst().evaluate(value), getSecond().evaluate(value)));
    }

    @Override
    public double evaluate(double value) {
        return Math.abs(gcd((int)getFirst().evaluate(value), (int)getSecond().evaluate(value)));
    }
    @Override
    public int evaluate(int value1, int value2, int value3) {
        return Math.abs(gcd(getFirst().evaluate(value1, value2, value3),
                getSecond().evaluate(value1, value2, value3)));
    }
}
