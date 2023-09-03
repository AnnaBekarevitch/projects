package expression;

public interface MyExpression extends Expression, DoubleExpression, TripleExpression {
    public int priority();
    public int priority_from_right();
}
