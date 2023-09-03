package expression;

import java.util.Objects;

public class Variable implements MyExpression {

    String variable;
    int value;

    public int priority(){
        return 1000;
    }

    public int priority_from_right(){
        return 1000;
    }

    public Variable(final String variable) {
        this.variable = variable;
    }
    @Override
    public int evaluate(int value) {
        return value;
    }

    @Override
    public String toString() {
        return variable;
    }
    @Override
    public boolean equals(final Object another) {
        if(another == null){
            return false;
        }
        return this.getClass() == another.getClass() && variable.equals(another.toString());
    }
    @Override
    public String toMiniString() {
        return variable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable);
    }
    
    @Override
    public double evaluate(double value) {
        return value;
    }

    @Override
    public int evaluate(int value1, int value2, int value3) {
        return switch (variable) {
            case "x" -> value1;
            case "y" -> value2;
            case "z" -> value3;
            default -> 1;
          };
    }
}
