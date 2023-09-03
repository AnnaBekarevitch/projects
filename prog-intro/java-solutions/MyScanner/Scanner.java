package MyScanner;

import java.util.Arrays;
import java.util.*;
import java.io.*;

public class Scanner {

    public static final int BUFFER_SIZE = 1024*4;
    
    BufferedReader reader;
    int position = 0, blockEnd = 0;
    char[] block = new char[BUFFER_SIZE];
    String nowWord = new String();
    int startNowToken = 0, endNowToken = 0;
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

    public Scanner(InputStream in) throws IOException, FileNotFoundException, UnsupportedEncodingException { // // System.in
        reader = new BufferedReader(
                new InputStreamReader(in, "utf8"),
                BUFFER_SIZE
        );
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
        if(position == -1){
            return;
        }
        try {
            blockEnd = reader.read(block, 0, BUFFER_SIZE);
            //System.err.println(blockEnd);

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
        //System.err.println(":(((");
        if (fatality) return;
        if(hasNextToken) return;
        startNewLine = 0;
        StringBuilder word = new StringBuilder();
        boolean started = false;
        for (int j = position; j <= blockEnd; j++) {
            if (j == blockEnd) {
                readNextBlock();
                if(position == -1) break;
                //for (int i = 0; i < blockEnd; ++i) System.err.println((int)block[i]);
                j = position;
            }
            int p = j;
            while (p < blockEnd && !Character.isWhitespace(block[p])) {
                previousChar = block[p];
                word.append(block[p]);
                started = true;
                //System.err.println((int)block[p]);
                p++;
            }
            if(p == j && started) break;
            if (p == j && isNewLineStartedNow(block[p])) {
                startNewLine++;
            }
            
            if(!started){
                previousChar = block[p];
                position = j + 1;
            }else{
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
                }
                else {
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
        //System.err.println(";((((");
        if (!hasNext()) return false;
            long res = parseInt();
            return true;

    }
}
