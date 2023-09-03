package expression;

import java.util.Objects;

public abstract class MathExpression implements MyExpression {
    MyExpression first;
    MyExpression second;
    protected String sign;

    MathExpression(final MyExpression first, final MyExpression second) {
        this.first = first;
        this.second = second;
    }

    public abstract boolean associative();
    
    protected MyExpression getFirst() {
        return first;
    }
    protected MyExpression getSecond() {
        return second;
    }
    public boolean distributive(final MyExpression now, final MyExpression second) {
        return !((second instanceof Lcm && now instanceof Gcd) || (second instanceof Gcd && now instanceof Lcm));
    } 

    @Override
    public String toString() {
        StringBuilder expression = new StringBuilder();
        expression.append('(');
        expression.append(first.toString()).append(" ");
        expression.append(sign).append(" ");
        expression.append(second.toString());
        expression.append(')');
        return expression.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder expression = new StringBuilder();
        if(first.priority() < priority()){
            expression.append("(").append(first.toMiniString()).append(")");
        }else{
            expression.append(first.toMiniString());
        }
        expression.append(" ").append(sign).append(" ");
        if(priority() < second.priority_from_right() || associative() && priority() == second.priority_from_right() && distributive(this, this.getSecond())){
            expression.append(second.toMiniString());
        }else{
            expression.append("(").append(second.toMiniString()).append(")");
        }
        return expression.toString();
    }
    @Override
    public boolean equals(Object another) {
        if(another == null){
            return false;
        }
        return this.getClass() == another.getClass() && this.toString().equals(another.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, sign);
    }

}
