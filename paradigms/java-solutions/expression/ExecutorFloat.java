package expression;

public class ExecutorFloat implements Executor<Float>  {
    @Override
    public Float add(Float first, Float second) {
        return first + second;
    }
    public Float sub(Float first, Float second) {
        return first - second;
    }
    public Float mul(Float first, Float second) {
        return first * second;
    }
    public Float div(Float first, Float second) {
        //if(second == 0){
          //  throw new EvaluteException("Divide by zero");
        //}
        return first / second;
    }
    public Float unary(Float val) {
        return -val;
    }

    public Float constant(Number val) {
        return val.floatValue();
    }

    public Float variable(int value){
        return Float.valueOf(value);
    }
}