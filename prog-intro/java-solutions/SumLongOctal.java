public class SumLongOctal {

    public static long calculate(int index, int last, String string) {
        return (((string.charAt(index - 1) == 'o' || string.charAt(index - 1) == 'O'))
                ? Long.parseLong(string.substring(last, index - 1), 8)
                : Long.parseLong(string.substring(last, index)));
    }

    public static void main(final String[] args) {
        long res = 0;
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args[i].length(); j++) {
                int p = j;
                while (p < args[i].length() && !Character.isWhitespace(args[i].charAt(p))) {
                    p++;
                }
                if (p != j) {
                    res += calculate(p, j, args[i]);
                }
                j = p;
            }
        }
        System.out.println(res);
    }
}