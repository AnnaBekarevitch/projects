import java.util.Arrays;
import java.util.*;
import java.io.*;
import MyScanner.Scanner;

class WordData {
    public String word;
    public int position;
    public int count;

    public WordData(String _word, int _position, int _count) {
        word = _word;
        position = _position;
        count = _count;
    }
};

public class WordStatInput {
    public static void main(final String[] args) {
        WordData[] words = new WordData[1];
        int indexWords = 0;
        try {
            Scanner reader = new Scanner(new FileInputStream(args[0]));
            while (reader.hasNextWord()) {
                String line = reader.nextWord();
                for (int i = 0; i < line.length(); i++) {
                    int start = i;
                    while (i < line.length() && (Character.isAlphabetic(line.charAt(i))
                            || line.charAt(i) == '\''
                            || Character.DASH_PUNCTUATION == Character.getType(line.charAt(i)))) {
                        i++;
                    }
                    if (i != start) {
                        if (indexWords >= words.length) {
                            words = Arrays.copyOf(words, words.length * 2);
                        }
                        words[indexWords] = new WordData(line.substring(start, i).toLowerCase(), indexWords, i - start);
                        indexWords++;
                    }
                }
            }

            reader.closeScanner();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedEncodingException e) {;
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(args[1]), "utf8"))) {
            Arrays.sort(words, 0, indexWords, Comparator.comparing(a -> a.word));
            for (int i = 0; i < indexWords; ) {
                int start = i;
                while (i < indexWords && words[start].word.equals(words[i].word)) {
                    if (words[start].position > words[i].position) {
                        words[start].position = words[i].position;
                    }
                    ++i;
                }
                for (int j = start + 1; j < i; ++j) {
                    words[j].position = indexWords + 1;
                }
                words[start].count = i - start;
            }
            Arrays.sort(words, 0, indexWords, Comparator.comparing(a -> a.position));
            for (int i = 0; i < indexWords && words[i].position < indexWords + 1; i++) {
                writer.write(words[i].word + " " + words[i].count + "\n");
            }
        } catch (UnsupportedEncodingException e1) {
            System.out.println(e1.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e2) {
            System.out.println(e2.getMessage());
        }

    }
}