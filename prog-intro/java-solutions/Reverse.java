import MyScanner.Scanner;
import java.util.Arrays;
import java.util.*;
import java.io.*;

public class Reverse {
    public static void main(final String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            int[] result = new int[1];
            int[] limites = new int[1];
            int index = 0, lines = 0;
            while (in.hasNextInt()) {
                if (index >= result.length) {
                    result = Arrays.copyOf(result, 2 * result.length);
                }
                result[index] = in.nextInt();
                // System.err.println(result[index]);
                int countNewLines = in.isLineHasStarted();
                for (int i = 0; i < countNewLines; ++i) {
                    lines++;
                    if (lines >= limites.length) {
                        limites = Arrays.copyOf(limites, 2 * limites.length);
                    }
                    limites[lines] = index;
                }
                index++;
            }
            int countNewLines = in.isLineHasStarted();
            for (int i = 0; i < countNewLines; ++i) {
                lines++;
                if (lines >= limites.length) {
                    limites = Arrays.copyOf(limites, 2 * limites.length);
                }
                limites[lines] = index;
            }
            for (int i = lines - 1; i >= 0; --i) {
                for (int j = index - 1; j >= limites[i]; --j) {
                    System.out.print(result[j] + " ");
                }
                index = limites[i];
                System.out.println();
            }
            in.closeScanner();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Input file reading error: " + e.getMessage());
        }
    }
}
