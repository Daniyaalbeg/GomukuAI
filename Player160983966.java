import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

class Player160983966 extends GomokuPlayer {

    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_BLACK = "\033[0;30m";
    public static final String ANSI_RED = "\033[0;31m";

    Map<Integer, Color[][]> storedBoards = new HashMap<Integer, Color[][]>();

    public Move chooseMove(Color[][] board, Color me) {
        //printBoard(board);
        try {
            minmax(board, 0, me);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int row = 0; row < GomokuBoard.ROWS; row++)
        for (int col = 0; col < GomokuBoard.COLS; col++)
        if (board[row][col] == null)
        return new Move(row, col);
        return null;
    }

    public int minmax(Color[][] board, int depth, Color me) {
        int heuristicValue = Integer.MAX_VALUE;
        if (depth == 0 || checkIfVictory(board, me)) {
            return heuristicEvaluation(board, me);
        }
        return 0;
    }

    public Boolean checkIfVictory(Color[][] board, Color me) {
        return true;
    }

    public int heuristicEvaluation(Color[][] board, Color me) {
        squareScore[][] scoredBoard = new squareScore[8][8];
        for (int i = 0; i<scoredBoard.length; i++) {
            for (int j = 0; j<scoredBoard[i].length; j++) {
                scoredBoard[i][j] = new squareScore();
            }
        }
        checkHorizontal(board, scoredBoard, me);
        checkVertical(board, scoredBoard, me);
        checkDiagonalL(board, scoredBoard, me);
        checkDiagonalR(board, scoredBoard, me);
        printScoredBoard(board, scoredBoard);
        return 0;
    }

    private void checkHorizontal(Color[][] board, squareScore[][] scoredBoard, Color me) {
        for (int i =0; i < 8; i++) {
            checkH(board, scoredBoard, me, i, 0, 0, 0, true);
        }
        for (int i = 7; i >= 0; i--) {
            checkH(board, scoredBoard, me, i, 7, 0, 0, false);
        }
    }

    private void checkH(Color[][] board, squareScore[][] scoredBoard, Color me, int row, int col, int whiteCount, int blackCount, boolean right) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return;
        }
        int add = right ? 1 : -1;
        if (board[row][col] == null) {
            if (blackCount == 4) {
                scoredBoard[row][col].blackPoint = Integer.MAX_VALUE;
            } else if (whiteCount == 4) {
                scoredBoard[row][col].whitePoint = Integer.MAX_VALUE;
            } else {
                scoredBoard[row][col].blackPoint = (blackCount > scoredBoard[row][col].blackPoint) ? blackCount : scoredBoard[row][col].blackPoint;
                scoredBoard[row][col].whitePoint = (whiteCount > scoredBoard[row][col].whitePoint) ? whiteCount : scoredBoard[row][col].whitePoint;
            }
            checkH(board, scoredBoard, me, row, col+add, 0, 0, right);
        } else if (board[row][col] == Color.black) {
            checkH(board, scoredBoard, me, row, col+add, 0, blackCount+1, right);
        } else {
            checkH(board, scoredBoard, me, row, col+add, whiteCount+1, 0, right);
        }
    }

    private void checkVertical(Color[][] board, squareScore[][] scoredBoard, Color me) {
        for (int i = 0; i < 8; i++) {
            checkV(board, scoredBoard, me, 0, i, 0, 0, true);
        }
        for (int i = 7; i >= 0; i--) {
            checkV(board, scoredBoard, me, 7, i, 0, 0, false);
        }
    }

    private void checkV(Color[][] board, squareScore[][] scoredBoard, Color me, int row, int col, int whiteCount, int blackCount, boolean down) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return;
        }
        int add = down ? 1 : -1;
        if (board[row][col] == null) {
            if (board[row][col] == null) {
                if (blackCount == 4) {
                    scoredBoard[row][col].blackPoint = Integer.MAX_VALUE;
                } else if (whiteCount == 4) {
                    scoredBoard[row][col].whitePoint = Integer.MAX_VALUE;
                } else {
                    scoredBoard[row][col].blackPoint = (blackCount > scoredBoard[row][col].blackPoint) ? blackCount : scoredBoard[row][col].blackPoint;
                    scoredBoard[row][col].whitePoint = (whiteCount > scoredBoard[row][col].whitePoint) ? whiteCount : scoredBoard[row][col].whitePoint;
                }
            }
            checkV(board, scoredBoard, me, row+add, col, 0, 0, down);
        } else if (board[row][col] == Color.black) {
            checkV(board, scoredBoard, me, row+add, col, 0, blackCount+1, down);
        } else {
            checkV(board, scoredBoard, me, row+add, col, whiteCount+1, 0, down);
        }
    }

    private void checkDiagonalL(Color[][] board, squareScore[][] scoredBoard, Color me) {
        //Top to bottom
        for (int row = 3; row > 0; row--) {
            checkL(board, scoredBoard, me, row, 0, 0, 0, true);
        }
        checkL(board, scoredBoard, me, 0, 0, 0, 0, true);
        for (int col = 3; col > 0; col--) {
            checkL(board, scoredBoard, me, 0, col, 0, 0, true);
        }

        //Bottom to top
        for (int row = 4; row <= 7 ; row++) {
            checkL(board, scoredBoard, me, row, 7, 0, 0, false);
        }
        checkL(board, scoredBoard, me, 7, 7, 0, 0, false);
        for (int col = 4; col <= 7; col++) {
            checkL(board, scoredBoard, me, 7, col, 0, 0, false);
        }
    }

    private void checkL(Color[][] board, squareScore[][] scoredBoard, Color me, int row, int col, int whiteCount, int blackCount, boolean up) {
        if (row > 7 || col > 7 || row < 0 || col < 0) {
            return;
        }
        int add = up ? 1 : -1;
        if (board[row][col] == null) {
            if (board[row][col] == null) {
                if (blackCount == 4) {
                    scoredBoard[row][col].blackPoint = Integer.MAX_VALUE;
                } else if (whiteCount == 4) {
                    scoredBoard[row][col].whitePoint = Integer.MAX_VALUE;
                } else {
                    scoredBoard[row][col].blackPoint = (blackCount > scoredBoard[row][col].blackPoint) ? blackCount : scoredBoard[row][col].blackPoint;
                    scoredBoard[row][col].whitePoint = (whiteCount > scoredBoard[row][col].whitePoint) ? whiteCount : scoredBoard[row][col].whitePoint;
                }
            }
            checkL(board, scoredBoard, me, row+add, col+add, 0, 0, up);
        } else if (board[row][col] == Color.black) {
            checkL(board, scoredBoard, me, row+add, col+add, 0, blackCount+1, up);
        } else {
            checkL(board, scoredBoard, me, row+add, col+add, whiteCount+1, 0, up);
        }
    }

    private void checkDiagonalR(Color[][] board, squareScore[][] scoredBoard, Color me) {
        //Top to bottom
        for (int col = 4; col < 7; col++) {
            checkR(board, scoredBoard, me, 0, col, 0, 0, true);
        }
        checkR(board, scoredBoard, me, 0, 7, 0, 0, true);
        for (int row = 1; row < 4; row++) {
            checkR(board, scoredBoard, me, row, 7, 0, 0, true);
        }

        //Bottom to top
        for (int row = 4; row < 7; row++) {
            checkR(board, scoredBoard, me, row, 0, 0, 0, false);
        }
        checkR(board, scoredBoard, me, 7, 0, 0, 0, false);
        for (int col = 1; col < 4; col++) {
            checkR(board, scoredBoard, me, 7, col, 0, 0, false);
        }
    }

    private void checkR(Color[][] board, squareScore[][] scoredBoard, Color me, int row, int col, int whiteCount, int blackCount, boolean down) {
        if (row > 7 || col > 7 || row < 0 || col < 0) {
            return;
        }
        int add = down ? 1 : -1;
        if (board[row][col] == null) {
            if (blackCount == 4) {
                scoredBoard[row][col].blackPoint = Integer.MAX_VALUE;
            } else if (whiteCount == 4) {
                scoredBoard[row][col].whitePoint = Integer.MAX_VALUE;
            } else {
                scoredBoard[row][col].blackPoint = (blackCount > scoredBoard[row][col].blackPoint) ? blackCount : scoredBoard[row][col].blackPoint;
                scoredBoard[row][col].whitePoint = (whiteCount > scoredBoard[row][col].whitePoint) ? whiteCount : scoredBoard[row][col].whitePoint;
            }
            checkR(board, scoredBoard, me, row+add, col-add, 0, 0, down);
        } else if (board[row][col] == Color.black) {
            checkR(board, scoredBoard, me, row+add, col-add, 0, blackCount+1, down);
        } else {
            checkR(board, scoredBoard, me, row+add, col-add, whiteCount+1, 0, down);
        }
    }

    public void printBoardColor(Color[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                String pos = "O";
                if (board[i][j] == Color.black) {
                    pos = "B";
                } else if (board[i][j] == Color.white) {
                    pos = "W";
                }
                System.out.print("|" + pos);
            }
            System.out.print("|");
            System.out.println("\n");
        }
        System.out.println();
    }

    public void printScoredBoard(Color[][] board, squareScore[][] scoredBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == Color.black) {
                    System.out.print("|( B )|");
                } else if (board[i][j] == Color.white) {
                    System.out.print("|( W )|");
                } else {
                    System.out.print("|(" + scoredBoard[i][j].blackPoint + "," + scoredBoard[i][j].whitePoint + ")|");
                }
            }
            System.out.println("\n");
        }
        System.out.println();
    }

}

class squareScore {
    public int blackPoint = 0;
    public int whitePoint = 0;
}
