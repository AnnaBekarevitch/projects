package expression;
public class ExecutorShort implements Executor<Short>  {
    @Override
    public Short add(Short first, Short second) {
        int a = first.shortValue() + second.shortValue();
        return Short.valueOf((short) a);
    }
    public Short sub(Short first, Short second) {
        int a = first.shortValue() - second.shortValue();
        return Short.valueOf((short) a);
    }
    public Short mul(Short first, Short second) {
        int a = first.shortValue() * second.shortValue();
        return Short.valueOf((short) a);
    }
    public Short div(Short first, Short second) throws EvaluteException {
        int a = first.shortValue(), b = second.shortValue();
        if(b == 0){
            throw new EvaluteException("Divide by zero");
        }
        return Short.valueOf((short) (a/b));
    }
    public Short unary(Short val) {
        int a = val.shortValue();
        return Short.valueOf((short)-val);
    }

    public Short constant(Number val) {
        return val.shortValue();
    }

    public Short variable(int value){
        return Short.valueOf((short)value);
    }
}