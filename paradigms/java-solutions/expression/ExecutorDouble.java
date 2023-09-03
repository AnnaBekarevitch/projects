package expression;

public class ExecutorDouble implements Executor<Double> {
    public Double add(Double first, Double second){
        return first + second;
    }
    public Double sub(Double first, Double second){
        return first - second;
    }
    public Double mul(Double first, Double second){
        return first * second;
    }
    public Double div(Double first, Double second){
        return first / second;
    }
    public Double unary(Double val){
        return -val;
    }

    public Double constant(Number val){
        return val.doubleValue();
    }

    public Double variable(int value){
        return Double.valueOf(value);
    }
}