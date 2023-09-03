import MyScanner.Scanner;
import java.util.Arrays;
import java.util.*;
import java.io.*;

public class ReverseAvg {
    public static void main(final String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            int[] result = new int[1];
            int[] limites = new int[1];
            int index = 0, lines = 0, cntCol = 0;
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
                    if (cntCol < index - limites[lines - 1]) {
                        cntCol = index - limites[lines - 1];
                    }
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
                if (cntCol < index - limites[lines - 1]) {
                    cntCol = index - limites[lines - 1];
                }
            }
            long[] sumCol = new long[cntCol];
            int[] cntRow = new int[cntCol];
            for (int i = 0; i < lines; ++i) {
                for (int j = limites[i]; j < limites[i + 1]; ++j) {
                    sumCol[j - limites[i]] += result[j];
                    cntRow[j - limites[i]]++;
                }
            }
            long sumRow = 0;
            for (int i = 0; i < lines; ++i) {
                sumRow = 0;
                for (int j = limites[i]; j < limites[i + 1]; ++j) {
                    sumRow += result[j];
                }
                for (int j = limites[i]; j < limites[i + 1]; ++j) {
                    System.out.print((sumRow + sumCol[j - limites[i]] - result[j])
                            / (cntRow[j - limites[i]] + limites[i + 1] - limites[i] - 1) + " ");

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
