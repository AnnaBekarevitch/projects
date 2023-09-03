import java.util.Arrays;
import java.util.*;
import java.io.*;
import MyScanner.Scanner;

public class Wspp {

    public static final int BUFFER_SIZE = 1024 * 4;

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
        Map<String, IntList> words
                = new HashMap<String, IntList>();
        try {
            Scanner reader = new Scanner(new FileInputStream(args[0]));
            int index = 1;
            while (reader.hasNextWord()) {
                String line = reader.nextWord();
                for (int i = 0; i < line.length(); i++) {
                    int start = i;
                    i = getLimits(line, i);
                    if (i != start) {
                        String word = line.substring(start, i).toLowerCase();
                        if (words.get(word) == null) {
                            words.put(word, new IntList());
                        }
                        words.get(word).add(index);
                        index++;
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

        List<Map.Entry<String, IntList>> result = new ArrayList(words.entrySet());
        Collections.sort(result, new Comparator<Map.Entry<String, IntList>>() {
            @Override
            public int compare(Map.Entry<String, IntList> a, Map.Entry<String, IntList> b) {
                return a.getValue().elemAt(0) - b.getValue().elemAt(0);
            }
        });

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"),
                BUFFER_SIZE
        )) {
            for (int i = 0; i < result.size(); i++) {
                writer.write(result.get(i).getKey() + " " + result.get(i).getValue().size());
                for (int j = 0; j < result.get(i).getValue().size(); j++) {
                    writer.write(" " + result.get(i).getValue().elemAt(j));
                }
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