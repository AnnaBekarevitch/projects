package expression;

public class Lcm extends MathExpression {

    public Lcm(final MyExpression first, final MyExpression second) {
        super(first, second);
        sign = "lcm";
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
        if (first.evaluate(value) == 0 && second.evaluate(value) == 0) return 0;
        MyExpression gcd = new Gcd(getFirst(), getSecond());
        MyExpression div = new Divide(getFirst(), gcd);
        MyExpression mult = new Multiply(div, getSecond());
        return mult.evaluate(value);
    }

    @Override
    public double evaluate(double value) {
        if (first.evaluate(value) == 0 && second.evaluate(value) == 0) return 0;
        MyExpression gcd = new Gcd(getFirst(), getSecond());
        MyExpression div = new Divide(getFirst(), gcd);
        MyExpression mult = new Multiply(div, getSecond());
        return mult.evaluate(value);
    }
    @Override
    public int evaluate(int value1, int value2, int value3) {

        if (first.evaluate(value1, value2, value3) == 0 && second.evaluate(value1, value2, value3) == 0) return 0;
        MyExpression gcd = new Gcd(getFirst(), getSecond());
        MyExpression div = new Divide(getFirst(), gcd);
        MyExpression mult = new Multiply(div, getSecond());
        return mult.evaluate(value1, value2, value3);
    }
}
