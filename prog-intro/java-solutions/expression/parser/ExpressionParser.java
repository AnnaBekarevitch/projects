package expression.parser;

import expression.*;

import java.util.*;

public final class ExpressionParser implements TripleParser {

    @Override
    public TripleExpression parse(final String expression) {
        //System.err.println("!" + expression);
        return parse(expression, skipWhitespace(expression, 0, expression.length()), expression.length());
    }

    private char tryParseSign(final String expression, final int start, final int end, final String sign) {
        int countOpenBrackets = 0;
        for (int i = end - 1; i >= start; i--) {
            final char now = expression.charAt(i);
            countOpenBrackets += switch (now) {
                case '(' -> 1;
                case ')' -> -1;
                default -> 0;
            };
            if (countOpenBrackets == 0 && sign.contains(String.valueOf(now)) && isSign(expression, start, i)) {
                return now;
            }
        }
        return 0;
    }

    private boolean isSign(final String expression, int start, int end) {
        for (int i = end - 1; i >= start; --i) {
            final char now = expression.charAt(i);
            if (!Character.isWhitespace(now) && !"()".contains(String.valueOf(now))) {
                return "0123456789xyz".contains(String.valueOf(now));
            }
        }
        return false;
    }

    private int parseThisSign(final String expression, final int start, final int end, final char sign) {
        int countOpenBrackets = 0;
        for (int i = end - 1; i >= start; i--) {
            final char now = expression.charAt(i);
            countOpenBrackets += switch (now) {
                case '(' -> 1;
                case ')' -> -1;
                default -> 0;
            };
            if (countOpenBrackets == 0 && now == sign && isSign(expression, start, i)) {
                return i;
            }
        }
        return expression.length();
    }

    private char parseSign(final String expression, final int start, final int end) {
        final String[] signs = { "lg","+-", "*/" };
        for (String pairSigns : signs) {
            final char sign = tryParseSign(expression, start, end, pairSigns);
            if (sign != 0) {
                return sign;
            }
        }
        return 0;
    }

    private int lengthSign(final char sign) {
        return sign == 'l' || sign == 'g' ? 3 : 1;
    }

    private MyExpression parse(final String expression, int start, final int end) {
        //System.err.println("?" + expression.substring(start, end));
        if (start == end) {
            return null;
        }
        start = skipWhitespace(expression, start, end);
        final char sign = parseSign(expression, start, end);
        if (sign == 0) {
            if (expression.charAt(start) == '-') {
                int pos = skipWhitespace(expression, start + 1, end);
                if (pos < end && pos > start + 1 || "-xyz(".contains(String.valueOf(expression.charAt(pos)))) { //  :NOTE: в отдельную константу
                    return new UnaryMinus(parse(expression, pos, end));
                }
                return parseValue(expression, start, end);
            } else if (expression.charAt(start) == 'r') {
                // :NOTE: не расширяео если еще фукнция начинается с r
                int pos = skipWhitespace(expression, start + 7, end);
                return new Reverse(parse(expression, pos, end));
            } 
            return parseValue(expression, skipWhitespace(expression, start, end), end);
        }

        final int posSign = parseThisSign(expression, start, end, sign);
        final MyExpression first = parse(expression, skipWhitespace(expression, start, posSign), posSign);
        final MyExpression second = parse(expression, skipWhitespace(expression, posSign + lengthSign(sign), end), end);

        return switch (sign) {
            case '*' -> new Multiply(first, second);
            case '/' -> new Divide(first, second);
            case '+' -> new Add(first, second);
            case '-' -> new Subtract(first, second);
            case 'l' -> new Lcm(first, second);
            case 'g' -> new Gcd(first, second);
            default -> null;
        };
    }

    private MyExpression parseValue(final String expression, final int start, final int end) {
        if (start >= end)
            return null;
        for (int i = start; i < end; i++) {
            if (expression.charAt(i) == '(') {
                int countOpenBrackets = 0;
                for (int j = i; j < end; j++) {
                    final char now = expression.charAt(j);
                    countOpenBrackets += switch (now) {
                        case '(' -> 1;
                        case ')' -> -1;
                        default -> 0;
                    };
                    if (countOpenBrackets == 0 && now == ')') {
                        return parse(expression, i + 1, j);
                    }
                }
            }
        }
        final char now = expression.charAt(start); // :NOTE: а если не односимвольные пеерменные?
        return switch (now) {
            case 'x' -> new Variable("x");
            case 'y' -> new Variable("y");
            case 'z' -> new Variable("z");
            default -> new Const(parseNumber(expression, start, end));
        };
    }

    private int skipWhitespace(final String expression, int start, int end) {
        while (start < end && Character.isWhitespace(expression.charAt(start))) {
            start++;
        }
        return start;
    }

    private Number parseNumber(final String expression, int start, final int end) {
        final StringBuilder result = new StringBuilder();
        if (expression.charAt(start) == '-') {
            result.append('-');
            start += 1;
        }
        start = takeInteger(expression, skipWhitespace(expression, start, end), end, result);

        if (start < end && expression.charAt(start) == '.') {
            result.append('.');
            start = takeDigits(expression, start + 1, end, result);
        }

        if (start < end && expression.toLowerCase().charAt(start) == 'e') {
            result.append('e');
            start++;
            if (start < end && expression.charAt(start) == '-') {
                result.append('e');
                start++;
            }
            start = takeDigits(expression, start, end, result);
        }
        try {
            return Integer.parseInt(result.toString());
        } catch (final NumberFormatException e) {
            return Double.parseDouble(result.toString());
        }
    }

    private boolean between(final char now, final char start, final char end) {
        return start <= now && now <= end;
    }

    private int takeDigits(final String expression, int start, final int end, final StringBuilder result) {
        while (start < end && between(expression.charAt(start), '0', '9')) {
            result.append(expression.charAt(start));
            start++;
        }
        return start;
    }

    private int takeInteger(final String expression, int start, final int end, final StringBuilder result) {
        if (expression.charAt(start) == '0') {
            result.append('0');
            start++;
        } else if (between(expression.charAt(start), '1', '9')) {
            start = takeDigits(expression, start, end, result);
        }
        return start;
    }
}
/**
 * __current-repo/java-solutions/expression/parser/ParserTest.java:13: error: duplicate class: expression.parser.ParserTest
 * public final class ParserTest {
 *              ^
 * 1 error
 * ERROR: Compilation failed
 */

/**
 Compiling 11 Java sources
 __current-repo/java-solutions/expression/parser/ParserTest.java:13: error: duplicate class: expression.parser.ParserTest
 public final class ParserTest {
 ^
 1 error
 */