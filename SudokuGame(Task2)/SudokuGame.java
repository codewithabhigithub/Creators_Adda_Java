import java.util.Scanner;

public class SudokuGame {
    private int[][] board;
    private final int size = 9;
    private final int emptyCell = 0;
    private final int blockSize = 3;

    public SudokuGame() {
        board = new int[size][size];
    }

    // Function to print the Sudoku board to the console
    private void printBoard() {
        System.out.println("-------------");
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(board[row][col] + " ");
                if ((col + 1) % blockSize == 0) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((row + 1) % blockSize == 0) {
                System.out.println("-------------");
            }
        }
    }

    // Function to check if placing a number in a cell is valid
    private boolean isValidPlacement(int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < size; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // Check the 3x3 block
        int blockRowStart = row - row % blockSize;
        int blockColStart = col - col % blockSize;
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                if (board[blockRowStart + i][blockColStart + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // Function to solve the Sudoku puzzle using backtracking
    private boolean solveSudoku() {
        int[] emptyCellPos = findEmptyCell();
        if (emptyCellPos == null) {
            return true; // Puzzle solved
        }

        int row = emptyCellPos[0];
        int col = emptyCellPos[1];

        for (int num = 1; num <= size; num++) {
            if (isValidPlacement(row, col, num)) {
                board[row][col] = num;

                if (solveSudoku()) {
                    return true;
                }

                board[row][col] = emptyCell; // Backtrack
            }
        }

        return false;
    }

    // Function to find an empty cell in the board
    private int[] findEmptyCell() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == emptyCell) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    // Function to play the game
    public void play() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Sudoku!");
        solveSudoku(); // Generate the solution

        // Clear random cells to create a puzzle
        int numEmptyCells = size * size / 2;
        while (numEmptyCells > 0) {
            int row = (int) (Math.random() * size);
            int col = (int) (Math.random() * size);
            if (board[row][col] != emptyCell) {
                board[row][col] = emptyCell;
                numEmptyCells--;
            }
        }

        printBoard();

        // Play the game
        while (true) {
            System.out.print("Enter row, column, and number (space-separated, e.g., '3 4 7'): ");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            int num = scanner.nextInt();

            if (row < 0 || row >= size || col < 0 || col >= size || num < 1 || num > size) {
                System.out.println("Invalid input! Row, column, and number must be between 1 and 9.");
                continue;
            }

            if (board[row][col] == emptyCell) {
                if (isValidPlacement(row, col, num)) {
                    board[row][col] = num;
                } else {
                    System.out.println("Invalid placement! Try again.");
                }
            } else {
                System.out.println("Cell already filled. Try again.");
            }

            printBoard();

            if (isBoardFilled()) {
                if (solveSudoku()) {
                    System.out.println("Congratulations! You solved the puzzle.");
                } else {
                    System.out.println("Sorry, your solution is incorrect.");
                }
                break;
            }
        }
    }

    private boolean isBoardFilled() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == emptyCell) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
        game.play();
    }
}

// all zero is not a empty cell some cell zero is value so if you enter the row column or enter 4 ya another value or value not render so in this place zero is value not a blank space so select another row or column