import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;
import java.util.Set;

class Pair{
    public int first;
    public int second;
    Pair(){}
    Pair(int a, int b){
        first = a;
        second = b;
    }
}

public class K_43 {
    public static void main(final String[] args) {
        Scanner in = new Scanner(System.in);
        int rows = in.nextInt();
        int cols = in.nextInt();
        Stack<Pair> centers = new Stack<>();
        char[][] country = new char[rows][cols];
        for (int row = 0; row < rows; row++) {
            String inputLine = in.next();
            for (int col = 0; col < cols; col++) {
                country[row][col] = inputLine.charAt(col);
            }
        }

        int rowA = -1;
        int colA = -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (country[i][j] == 'A') {
                    rowA = i;
                    colA = j;
                }
                if (country[i][j] != '.') {
                    centers.add(new Pair(i, j));
                    country[i][j] = Character.toLowerCase(country[i][j]);
                }
            }
        }
        country[rowA][colA] = '.';

        int[][] heights = new int[rows + 1][cols + 1];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (country[row][col] == '.') {
                    heights[row + 1][col + 1] = heights[row][col + 1] + 1;
                }
            }
        }
        int[][] precalc = new int[rows + 1][cols + 1];
        for (int row = 1; row <= rows; row++) {
            Stack<Integer> stack = new Stack<>();
            for (int col = cols; col > 0; col--) {
                while (!stack.isEmpty() && heights[row][stack.peek()] >= heights[row][col]) {
                    stack.pop();
                }
                precalc[row][col] = (!stack.isEmpty() ? stack.peek() : cols + 1) - col;
                stack.push(col);
            }
        }
        int sRect = 0;
        int lRectRow = rowA;
        int lRectCol = colA;
        int rRectRow = rowA + 1;
        int rRectCol = colA + 1;
        for (int row = 1; row <= rows; row++) {
            Stack<Integer> stack = new Stack<>();
            for (int col = 1; col <= cols; col++) {
                while (!stack.isEmpty() && heights[row][stack.peek()] >= heights[row][col]) {
                    stack.pop();
                }
                int a = precalc[row][col] + col - 1 - (!stack.isEmpty() ? stack.peek() : 0);
                int b = heights[row][col] ; // прямоугольник (a х b)
                if (a * b > sRect && row - heights[row][col] <= rowA && rowA < row
                        && (!stack.isEmpty() ? stack.peek() : -1) < colA + 1
                        && colA + 1 < col + precalc[row][col]) {
                    sRect = a * b;
                    lRectRow = row - heights[row][col];
                    lRectCol = (!stack.isEmpty() ? stack.peek() : 0);
                    rRectRow = row;
                    rRectCol = col + precalc[row][col] - 1;
                }
                stack.push(col);
            }

        }
        for (int row = lRectRow; row < rRectRow; row++) {
            for (int col = lRectCol; col < rRectCol; col++) {
                country[row][col] = 'a';
            }
        }
        solve(rRectRow, rows, country, cols);
        solve(lRectRow, rRectRow, country, cols);
        solve(0, lRectRow, country, cols);
        for(Pair v : centers){
            country[v.first][v.second] = Character.toUpperCase(country[v.first][v.second]);
        }
        for (int row = 0; row < rows; row++) {
            System.out.println(country[row]);
        }
    }

    public static void solve(int lowrow, int uprow, char[][] country, int cols) {
        Set<Character> was = new HashSet<>();
        for (int col = 0; col < cols; col++) {
            for (int row = lowrow; row < uprow; row++) {
                if (country[row][col] != '.' && country[row][col] != 'a' && !was.contains(country[row][col])) {
                    was.add(country[row][col]);
                    int newRow = row - 1;
                    while (newRow >= lowrow && country[newRow][col] == '.') {
                        country[newRow][col] = country[row][col];
                        newRow--;
                    }
                    newRow = row + 1;
                    while (newRow < uprow && country[newRow][col] == '.') {
                        country[newRow][col] = country[row][col];
                        newRow++;
                    }
                }
            }
        }
        was.clear();
        for (int col = 0; col < cols; col++) {
            for (int row = lowrow; row < uprow; row++) {
                if (country[row][col] != '.' && country[row][col] != 'a' && !was.contains(country[row][col])) {
                    was.add(country[row][col]);
                    int upRow = row;
                    while (upRow >= lowrow && country[upRow][col] == country[row][col]) {
                        upRow--;
                    }
                    upRow++;
                    int downRow = row;
                    while (downRow < uprow && country[downRow][col] == country[row][col]) {
                        downRow++;
                    }
                    downRow--;
                    int newCol = col - 1;
                    while (newCol >= 0) {
                        boolean can = true;
                        for(int c = upRow; c <= downRow;c++){
                            if(country[c][newCol] != '.'){
                                can = false;
                                break;
                            }
                        }
                        if(!can){
                            break;
                        }
                        for(int c = upRow; c <= downRow;c++) {
                            country[c][newCol] = country[row][col];
                        }
                        newCol--;
                    }
                    newCol = col + 1;
                    while (newCol < cols) {
                        boolean can = true;
                        for(int c = upRow; c <= downRow;c++){
                            if(country[c][newCol] != '.'){
                                can = false;
                                break;
                            }
                        }
                        if(!can) {
                            break;
                        }
                        for(int c = upRow; c <= downRow;c++) {
                            country[c][newCol] = country[row][col];
                        }
                        newCol++;
                    }
                }
            }
        }
    }
}