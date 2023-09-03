import java.util.ArrayList;
//import java.util.Scanner;
import java.io.*;
import java.util.Arrays;

public class E_5 {

    static ArrayList<Integer>[] cities;
    static int[] dists;
    static int[] tIn;
    static int[] tOut;
    static int time;

    static void dfs(int vertexV, int perent) {
        tIn[vertexV] = time;
        dists[vertexV] = dists[perent] + 1;
        for (Integer vertexU : cities[vertexV]) {
            if (vertexU != perent) {
                dfs(vertexU, vertexV);
            }
        }
        tOut[vertexV] = time++;
    }

    public static void main(final String[] args) {

        Scanner in = new Scanner(System.in);

        int sizeCities = in.nextInt();
        int sizeTeams = in.nextInt();
        cities = new ArrayList[sizeCities];
        for (int i = 0; i < sizeCities; i++) {
            cities[i] = new ArrayList<>();
        }

        dists = new int[sizeCities];
        tOut = new int[sizeCities];
        tIn = new int[sizeCities];
        for (int i = 0; i < sizeCities - 1; i++) {
            int cityA = in.nextInt();
            int cityB = in.nextInt();
            cityA--;
            cityB--;
            cities[cityA].add(cityB);
            cities[cityB].add(cityA);
        }
        int[] teams = new int[sizeTeams];
        for (int city = 0; city < sizeTeams; city++) {
            teams[city] = in.nextInt();
            teams[city]--;
        }
        dists[teams[0]] = -1;
        dfs(teams[0], teams[0]);
        int depthCity = teams[0];
        for (int city = 0; city < sizeTeams; city++) {
            if (dists[depthCity] < dists[teams[city]]) {
                depthCity = teams[city];
            }
        }
        int biggestDist = dists[depthCity];
        int middleCity = depthCity;
        for (int city = 0; city < sizeCities; city++) {
            if (tIn[city] <= tIn[depthCity] && tOut[depthCity] <= tOut[city]
                    && Math.abs(2 * dists[city] - dists[depthCity])
                    < Math.abs(2 * dists[middleCity] - dists[depthCity])) {
                middleCity = city;
            }
        }
        dists[middleCity] = -1;
        dfs(middleCity, middleCity);
        boolean hasSolve = true;
        for (int city = 1; city < sizeTeams; city++) {
            if (dists[teams[city]] != dists[teams[city - 1]]) {
                hasSolve = false;
            }
        }
        if (hasSolve) {
            System.out.println("YES");
            System.out.println(middleCity + 1);
        } else {
            System.out.println("NO");
        }
    }
}
class Scanner {

    public static final int BUFFER_SIZE = 1024 * 4;

    BufferedReader reader;
    int position = 0;
    int blockEnd = 0;
    char[] block = new char[BUFFER_SIZE];
    String nowWord = new String();
    int startNowToken = 0;
    int endNowToken = 0;
    private char previousChar = 0;

    private boolean fatality = false;
    private boolean hasNextToken = false;
    private boolean canRead = true;
    private int startNewLine = 0;

    public Scanner(FileInputStream file) throws IOException, FileNotFoundException, UnsupportedEncodingException { // new FileInputStream(args[0])
        reader = new BufferedReader(
                new InputStreamReader(file, "utf8"),
                BUFFER_SIZE
        );
    }

    public Scanner(String string) throws IOException, FileNotFoundException, UnsupportedEncodingException { // filename = args[0]
        reader = new BufferedReader(
                new StringReader(string),
                BUFFER_SIZE
        );
        //sSystem.err.println("string!");
    }

    public Scanner(InputStream in)  { // // System.in
        try {
            reader = new BufferedReader(
                    new InputStreamReader(in, "utf8"),
                    BUFFER_SIZE
            );
        }catch(Exception e){
            fatality=true;
        }
    }

    public void closeScanner() throws IOException {
        reader.close();
    }

    private boolean isNewLineStartedNow(char nowChar) {
        if (System.lineSeparator().length() == 2) {
            return previousChar == System.lineSeparator().charAt(0) && nowChar == System.lineSeparator().charAt(1);
        } else {
            return nowChar == System.lineSeparator().charAt(0);
        }
    }

    private boolean hasNext() {

        readNextToken();
        return nowWord.length() > 0;
    }

    public boolean HasException() {
        return fatality;
    }

    private void readNextBlock() {
        if (fatality) return;
        if (position == -1) {
            return;
        }
        try {
            blockEnd = reader.read(block, 0, BUFFER_SIZE);
            if (blockEnd == -1) {
                position = blockEnd;
                return;
            }
            position = 0;
        } catch (IOException e) {
            fatality = true;
        }
    }

    private void readNextToken() {
        if (fatality) return;
        if (hasNextToken) return;
        startNewLine = 0;
        StringBuilder word = new StringBuilder();
        boolean started = false;
        for (int j = position; j <= blockEnd; j++) {
            if (j == blockEnd) {
                readNextBlock();
                if (position == -1) break;
                j = position;
            }
            int p = j;
            while (p < blockEnd && !Character.isWhitespace(block[p])) {
                previousChar = block[p];
                word.append(block[p]);
                started = true;
                p++;
            }
            if (p == j && started) break;
            if (p == j && isNewLineStartedNow(block[p])) {
                startNewLine++;
            }

            if (!started) {
                previousChar = block[p];
                position = j + 1;
            } else {
                position = p;
                j = p - 1;
            }
        }
        nowWord = word.toString();
        hasNextToken = nowWord.length() > 0;
    }

    private String next() {
        readNextToken();
        hasNextToken = false;
        String result = nowWord;
        return result;
    }

    public int isLineHasStarted() {
        return startNewLine;
    }

    public int parseInt() {
        //System.err.println("| " + nowWord + " |");
        boolean isWordDigit = true;
        nowWord = nowWord.toLowerCase();
        for (int i = 0; i < nowWord.length() - 1; ++i) {
            if (nowWord.charAt(i) == '-') continue;
            isWordDigit &= Character.isDigit(nowWord.charAt(i));
        }
        if (isWordDigit && Character.isDigit(nowWord.charAt(nowWord.length() - 1))) {
            return (Integer.parseInt(nowWord));
        } else if (nowWord.charAt(nowWord.length() - 1) == 'o') {
            int parsedInt = 0;
            for (int i = 0; i < nowWord.length() - 1; ++i) {
                if (nowWord.charAt(i) == '-') {
                    continue;
                }
                parsedInt = parsedInt * 8;
                parsedInt += nowWord.charAt(i) - '0';

            }
            if (nowWord.charAt(0) == '-') {
                parsedInt *= -1;
            }
            return parsedInt;
        } else {
            int parsedInt = 0;
            for (int i = 0; i < nowWord.length(); ++i) {
                if (nowWord.charAt(i) == '-') {
                    continue;
                }
                parsedInt = parsedInt * 10;
                if (Character.isDigit(nowWord.charAt(i))) {
                    parsedInt += nowWord.charAt(i) - '0';
                } else {
                    parsedInt += (nowWord.charAt(i) - 'a');
                }
            }
            if (nowWord.charAt(0) == '-') {
                parsedInt *= -1;
            }
            return parsedInt;
        }
    }

    public int nextInt() {
        next();
        return parseInt();
    }

    public String nextWord() {
        return next();
    }

    public boolean hasNextWord() {
        return hasNext();
    }

    public boolean hasNextInt() {
        if (!hasNext()) return false;
        long res = parseInt();
        return true;

    }
}