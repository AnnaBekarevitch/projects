package expression;

public interface Executable {
    public <T extends Number> T evaluate(int value1, int value2, int value3, Executor<T> exec)  throws EvaluteException;
}