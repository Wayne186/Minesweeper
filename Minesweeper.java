import java.util.Random;
import java.util.Scanner;



public class Minesweeper{
    private final boolean[][] minefield;
    private final int[][] clueGrid;
    private boolean[][] checked;
    private int rows;
    private int columns;
    private int mines;
    
    public Minesweeper(int rows, int columns, int mines) {
        minefield = new boolean[rows][columns];
        clueGrid = new int[rows][columns];
        checked = new boolean[rows][columns];
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        generateMinefield(mines);
        generateClueGrid();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++) {
                checked[i][j] = false;
                //minefield[i][j] = false;
            }
        }
    }
    
    private void generateMinefield(int mines) {
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++) {
                minefield[i][j] = false;
            }
        }
        Random r = new Random(System.currentTimeMillis());
        while(mines > 0) { 
            int rr = r.nextInt(rows);
            int rc = r.nextInt(columns);
            if(minefield[rr][rc] == false) {
                minefield[rr][rc] = true;
                mines--;
            }
        }
    }
    
    private void generateClueGrid() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(minefield[i][j] == true)
                    clueGrid[i][j] = -1;
                else
                    clueGrid[i][j] = countMines(i, j);
            }
        }
    }
    
    private int countMines(int row, int column) {
        int count = 0;
        for(int i = Math.max(row - 1, 0); i <= Math.min(row + 1, rows - 1); i++) {
            for(int j = Math.max(column - 1, 0); j <= Math.min(column + 1, columns - 1); j++) {
                if(minefield[i][j] == true)
                    count++;
            }
        }
        return count;
    }
    
    private void printBoard() {
        System.out.print("   ");
        for(int j = 0; j < columns; j++)
            System.out.print(" " + (j + 1));
        System.out.println();
        System.out.print("  +");
        for(int j = 0; j < columns; j++)
            System.out.print("--");
        System.out.print("--");
        System.out.println();
        char a = 'A';
        for(int i = 0; i < rows; i++) {
            System.out.print(a + " |");
            for(int j = 0; j < columns; j++) {
                char symbol;
                if(checked[i][j] == false)
                    symbol = '?';
                else if(minefield[i][j])
                    symbol = '*';
                else if(clueGrid[i][j] > 0)
                    symbol = (char)('0' + clueGrid[i][j]);
                else
                    symbol = ' ';
                System.out.print(" " + symbol);
            }
            System.out.println();
            a++;
        }
    }
    
    public void check(int row, int column) {
        for(int i = Math.max(row - 1, 0); i <= Math.min(row + 1, rows - 1); i++) {
            for(int j = Math.max(column - 1, 0); j <= Math.min(column + 1, columns - 1); j++) {
                //checked[i][j] = true;
                if(!(i == row && j == column) && !checked[i][j]) {
                    checked[i][j] = true;
                    if(clueGrid[i][j] == 0)
                        check(i,j);
                }
            }
        }
    }
    
    public void start() {
        Scanner in = new Scanner(System.in);
        boolean win = false;
        while(!win) {
            printBoard();
            System.out.println("Check cell?");
            String line = in.nextLine().toUpperCase();
            int r = line.charAt(0) - 'A';
            int c = line.charAt(1) - '1';
            if(minefield[r][c])
                break;
            else {
                checked[r][c] = true;
                //if(cluegrid[r][c] == 0)
                check(r,c);    
                //if(clueGrid[r][c] == 0)
                    
            }
            win = true;
            for(int i = 0; i < rows && win; i++) {
                for(int j = 0; j < columns && win; j++) {
                    if(!checked[i][j] && !minefield[i][j])
                        win = false;
                }
            }
        }
        in.close();
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++)
                checked[i][j] = true;
        }
        printBoard();
        if(win)
            System.out.println("Awesome, you win");
        else
            System.out.println("You sucks loser.");
    }
    
    public static void main(String[] args) {
        Minesweeper m = new Minesweeper(5,5,2);
        m.start();
    }
    
}