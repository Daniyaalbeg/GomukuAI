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
        checkVertical(board, scoredBoard, me);
        checkHorizontal(board, scoredBoard, me);
        checkDiagonalL(board, scoredBoard, me);
        checkDiagonalR(board, scoredBoard, me);
        return 0;
    }

    private void checkHorizontal(Color[][] board, squareScore[][] scoredBoard, Color me) {
        int whiteCount = 0;
        int blackCount = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == Color.black) {
                    //Black Piece
                    blackCount++;
                    whiteCount = 0;
                } else if (board[i][j] == Color.white) {
                    //White Piece
                    whiteCount++;
                    blackCount = 0;
                } else {
                    //No Piece
                    if (blackCount != 0) {
                        if (blackCount == 4) {
                            scoredBoard[i][j].blackPoint = (me==Color.black) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                        } else {
                            scoredBoard[i][j].blackPoint += blackCount;
                        }
                        blackCount = 0;
                    } else {
                        if (whiteCount == 4) {
                            scoredBoard[i][j].whitePoint = (me==Color.white) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                        } else {
                            scoredBoard[i][j].whitePoint += whiteCount;
                        }
                        whiteCount = 0;
                    }
                }
            }
            whiteCount = 0;
            blackCount = 0;
        }

        for (int i = 7; i >= 0; i--) {
            for (int j = 7; j >= 0; j--) {
                if (board[i][j] == Color.black) {
                    //Black Piece
                    blackCount++;
                    whiteCount = 0;
                } else if (board[i][j] == Color.white) {
                    //White Piece
                    whiteCount++;
                    blackCount = 0;
                } else {
                    //No Piece
                    if (blackCount != 0) {
                        if (blackCount == 4) {
                            scoredBoard[i][j].blackPoint = (me==Color.black) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                        } else {
                            scoredBoard[i][j].blackPoint += blackCount;
                        }
                        blackCount = 0;
                    } else {
                        if (whiteCount == 4) {
                            scoredBoard[i][j].whitePoint = (me==Color.white) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                        } else {
                            scoredBoard[i][j].whitePoint += whiteCount;
                        }
                        whiteCount = 0;
                    }
                }
            }
            whiteCount = 0;
            blackCount = 0;
        }
        printScoredBoard(scoredBoard);
    }

    private void checkVertical(Color[][] board, squareScore[][] scoredBoard, Color me) {
        int whiteCount = 0;
        int blackCount = 0;
        //Run down
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[j][i] == Color.black) {
                    blackCount++;
                    whiteCount = 0;
                } else if (board[j][i] == Color.white) {
                    whiteCount++;
                    blackCount = 0;
                } else {
                    if (blackCount != 0) {
                        if (blackCount == 4) {
                            scoredBoard[j][i].blackPoint = (me==Color.black) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                        } else {
                            scoredBoard[j][i].blackPoint += blackCount;
                        }
                        blackCount = 0;
                    } else {
                        if (whiteCount == 4) {
                            scoredBoard[j][i].whitePoint = (me==Color.white) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                        } else {
                            scoredBoard[j][i].whitePoint += whiteCount;
                        }
                        whiteCount = 0;
                    }
                }
            }
            whiteCount = 0;
            blackCount = 0;
        }
        //Run up
        for (int i = 7; i >= 0; i--) {
            for (int j = 7; j >= 0; j--) {
                if (board[j][i] == Color.black) {
                    blackCount++;
                    whiteCount = 0;
                } else if (board[j][i] == Color.white) {
                    whiteCount++;
                    blackCount = 0;
                } else {
                    if (blackCount != 0) {
                        if (blackCount == 4) {
                            scoredBoard[j][i].blackPoint = (me==Color.black) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                        } else {
                            scoredBoard[j][i].blackPoint += blackCount;
                        }
                        blackCount = 0;
                    } else {
                        if (whiteCount == 4) {
                            scoredBoard[j][i].whitePoint = (me==Color.white) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                        } else {
                            scoredBoard[j][i].whitePoint += whiteCount;
                        }
                        whiteCount = 0;
                    }
                }
            }
            whiteCount = 0;
            blackCount = 0;
        }
    }

    private void checkDiagonalL(Color[][] board, squareScore[][] scoredBoard, Color me) {
        for () {
            for () {

            }
        }
    }

    private void checkDiagonalR(Color[][] board, squareScore[][] scoredBoard, Color me) {

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

    public void printScoredBoard(squareScore[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("|(" + board[i][j].blackPoint + "," + board[i][j].whitePoint + ")|");
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
