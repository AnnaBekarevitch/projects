package expression;

public class ExecutorInteger implements Executor<Integer>  {
    boolean check = false;
    public ExecutorInteger() {}

    public ExecutorInteger(final boolean value) {
        check = value;
    }
    private void overflowTest(final String sign, final Integer value1, final Integer value2) throws EvaluteException {
        if (value1 != null && value2 != null) {
            long a = value1;
            long b = value2;
            long result = switch (sign) {
                case "*" -> a * b;
                case "+" -> a + b;
                case "-" -> a - b;
                case "/" -> a / b;
                case "u-" -> -a;
                default -> Long.MIN_VALUE;
            };
            if (!(Integer.MIN_VALUE <= result && result <= Integer.MAX_VALUE)) {
                throw new EvaluteException("overflow when performing a mathematical operation");
            }
        }else {
            throw new EvaluteException("value is null");
        }
    }
    @Override
    public Integer add(Integer first, Integer second) throws EvaluteException {
        if (check) overflowTest("+", first, second);
        return first + second;
    }
    @Override
    public Integer sub(Integer first, Integer second) throws EvaluteException {
        if (check) overflowTest("-", first, second);
        return first - second;
    }
    @Override
    public Integer mul(Integer first, Integer second) throws EvaluteException {
        if (check) overflowTest("*", first, second);
        return first * second;
    }
    @Override
    public Integer div(Integer first, Integer second) throws EvaluteException {
        if(second == 0){
            throw new EvaluteException("Divide by zero");
        }
        if (check) overflowTest("/", first, second);
        return first / second;
    }
    @Override
    public Integer unary(Integer val) throws EvaluteException {
        if (check) overflowTest("u-", val, val);
        return -val;
    }
    @Override
    public Integer constant(Number val) throws EvaluteException {
        if (check && !(Integer.MIN_VALUE <= val.longValue() && val.longValue() <= Integer.MAX_VALUE)) {
            throw new EvaluteException("overflow when performing a mathematical operation");
        }
        return val.intValue();
    }
	@Override
    public Integer variable(int value){
        return value;
    }
}