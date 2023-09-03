public class Sum {
    public static void main(final String[] args) {
        int res = 0, last = -1;
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j <= args[i].length(); j++) {
                if (j == args[i].length() || Character.isWhitespace(args[i].charAt(j))) {
                    if (last != -1) {
                        res += Integer.parseInt(args[i].substring(last, j));
                        last = -1;
                    }
                } else if (last == -1) {
                    last = j;
                }
            }
        }
        System.out.println(res);
    }
}
