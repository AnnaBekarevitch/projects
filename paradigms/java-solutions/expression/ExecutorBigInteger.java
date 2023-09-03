package expression;

import java.math.BigInteger;

public class ExecutorBigInteger implements Executor<BigInteger> {
    public BigInteger add(BigInteger first, BigInteger second){
        return first.add(second);
    }
    public BigInteger sub(BigInteger first, BigInteger second){
        return first.subtract(second);
    }
    public BigInteger mul(BigInteger first, BigInteger second){
        return first.multiply(second);
    }
    public BigInteger div(BigInteger first, BigInteger second) throws EvaluteException {
        if(second.signum() == 0){
            throw new EvaluteException("Divide by zero");
        }
        return first.divide(second);
    }
    public BigInteger unary(BigInteger val){
        return val.negate();
    }

    public BigInteger constant(Number val){
        return BigInteger.valueOf(val.longValue());
    }

    public BigInteger variable(int value){
        return BigInteger.valueOf(value);
    }
}
