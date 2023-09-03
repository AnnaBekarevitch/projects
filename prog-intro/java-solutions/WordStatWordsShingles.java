import java.util.Arrays;
import java.util.*;
import java.io.*;
import MyScanner.Scanner;

public class WordStatWordsShingles {

    public static final int SIZE_SUB = 3;
    public static final int RESIZE_CONST = 2;
    public static final int BUFFER_SISE = 1024;

    public static int getLimits(String line, int start) {
        int i = start;
        while (i < line.length() && (Character.isAlphabetic(line.charAt(i))
                || line.charAt(i) == '\''
                || Character.DASH_PUNCTUATION == Character.getType(line.charAt(i)))) {
            i++;
        }
        return i;
    }

    public static void main(final String[] args) {
        String[] words = new String[1];
        int indexWords = 0;
        try {
            Scanner reader = new Scanner(new FileInputStream(args[0]));
            while (reader.hasNextWord()) {
                String line = reader.nextWord();
                for (int i = 0; i < line.length(); i++) {
                    int start = i;
                    i = getLimits(line, i);
                    if (i != start) {
                        for (int j = start; j + Math.min(i - start, SIZE_SUB) <= i; j++) {
                            if (indexWords >= words.length) {
                                words = Arrays.copyOf(words, words.length * RESIZE_CONST);
                            }
                            words[indexWords] = line.substring(j, j + Math.min(i - start, SIZE_SUB)).toLowerCase();
                            indexWords++;
                        }
                    }
                }
            }
            reader.closeScanner();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Input file reading error: " + e.getMessage());
        }

        Arrays.sort(words, 0, indexWords);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"),
                BUFFER_SISE
        )) {
            for (int i = 0; i < indexWords; ) {
                int start = i;
                while (i < indexWords && words[start].equals(words[i])) {
                    i++;
                }
                writer.write(words[start] + " " + (i - start));
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Output file reading error: " + e.getMessage());
        }
    }
}