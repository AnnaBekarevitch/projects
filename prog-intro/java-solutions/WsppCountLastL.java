import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MyScanner.Scanner;

class IntList {

    public static final int RESIZE_SIZE = 2;

    int lastIndex = 0;
    int size = 0;
    int first = -1;
    int lastLine = -1;
    int[] intList = new int[1];

    IntList() {
    }

    private void resize() {
        if (lastIndex >= intList.length) {
            intList = Arrays.copyOf(intList, RESIZE_SIZE * intList.length);
        }
    }

    public void add() {
        size++;
    }

    public void set(int elem) {
        if (first == -1) {
            first = elem;
        }
    }

    public int size() {
        return lastIndex;
    }

    public int getFirst() {
        return first;
    }

    public int sizeAll() {
        return size;
    }

    public void add(int elem) {
        resize();
        intList[lastIndex] = elem;
        lastIndex++;
    }

    public void add(int elem, int line) {
        resize();
        if (lastLine == line) {
            intList[lastIndex - 1] = elem;
        } else {
            add(elem);
        }
        lastLine = line;
    }

    public int elemAt(int index) {
        return intList[index];
    }
}

public class WsppCountLastL {

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
            int prevIndex = 0;
            int countLines = 0;
            while (reader.hasNextWord()) {
                String line = reader.nextWord();
                if (reader.isLineHasStarted() > 0) {
                    countLines++;
                    prevIndex = index - 1;
                }
                for (int i = 0; i < line.length(); i++) {
                    int start = i;
                    i = getLimits(line, i);
                    if (i != start) {
                        String word = line.substring(start, i).toLowerCase();
                        IntList il = words.get(word);
                        if (il == null) {
                            il = new IntList();
                            il.set(index);
                        }
                        il.add();
                        il.add(index - prevIndex, countLines);
                        words.put(word, il);
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
                if (a.getValue().sizeAll() == b.getValue().sizeAll()) {
                    return a.getValue().getFirst() - b.getValue().getFirst();
                }
                return a.getValue().sizeAll() - b.getValue().sizeAll();
            }
        });
        try (BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(args[1]), "utf8"),
            BUFFER_SIZE
        )) {
            for (int i = 0; i < result.size(); i++) {
                writer.write(result.get(i).getKey() + " " + result.get(i).getValue().sizeAll());
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

/**
 * Exception in thread "main" java.lang.AssertionError: Line 6:
 * expected `question 1 10`,
 * actual `question 1 1`
 * at base.Asserts.error(Asserts.java:75)
 * at base.Asserts.assertTrue(Asserts.java:41)
 * at base.Asserts.assertEquals(Asserts.java:20)
 * at base.Runner.lambda$testEquals$0(Runner.java:36)
 * at base.TestCounter.lambda$test$0(TestCounter.java:58)
 * at base.TestCounter.lambda$testV$2(TestCounter.java:71)
 * at base.Log.silentScope(Log.java:40)
 * at base.TestCounter.testV(TestCounter.java:70)
 * at base.TestCounter.test(TestCounter.java:57)
 * at base.Runner.testEquals(Runner.java:30)
 * at base.MainChecker.testEquals(MainChecker.java:25)
 * at wordStat.WordStatChecker.test(WordStatChecker.java:66)
 * at wordStat.WordStatChecker.test(WordStatChecker.java:49)
 * at wspp.WsppTester.lambda$variant$9(WsppTester.java:76)
 * at wordStat.WordStatChecker.test(WordStatChecker.java:44)
 * at wspp.WsppTester.lambda$variant$10(WsppTester.java:23)
 * at base.Selector.lambda$test$2(Selector.java:79)
 * at base.Log.lambda$action$0(Log.java:18)
 * at base.Log.silentScope(Log.java:40)
 * at base.Log.scope(Log.java:31)
 * at base.Log.scope(Log.java:24)
 * at base.Selector.lambda$test$3(Selector.java:79)
 * at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
 * at base.Selector.test(Selector.java:79)
 * at base.Selector.main(Selector.java:51)
 * at wspp.FullWsppTest.main(FullWsppTest.java:57)
 */