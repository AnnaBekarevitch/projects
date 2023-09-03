package expression;

public interface MyExpression extends TripleExpression, Executable {
    int priority();
    int priority_from_right();


}
