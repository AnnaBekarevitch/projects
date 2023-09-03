package expression;


public interface Executor<T extends Number> {
    public T add(T first, T second) throws EvaluteException;
    public T sub(T first, T second) throws EvaluteException;
    public T mul(T first, T second) throws EvaluteException;
    public T div(T first, T second) throws EvaluteException;
    public T unary(T val) throws EvaluteException;
    public T constant(Number val) throws EvaluteException;
    public T variable(int val) throws EvaluteException;
}