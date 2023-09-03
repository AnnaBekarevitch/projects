package expression.generic;

import expression.exceptions.ExpressionParser;
import expression.*;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        MyExpression parsedExpression = (MyExpression)((new ExpressionParser()).parse(expression));
        Executor<? extends Number> executor =  switch (mode) {
            case "i" -> new ExecutorInteger(true);
            case "d" -> new ExecutorDouble();
            case "bi" -> new ExecutorBigInteger();
            case "u" -> new ExecutorInteger(false);
            case "f" -> new ExecutorFloat();
            case "s" -> new ExecutorShort();
            default -> null;
        };
        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {
                    try {
                        result[i][j][k] = parsedExpression.evaluate(x1 + i, y1 + j, z1 + k, executor);
                    }catch (EvaluteException e){
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }
}
