import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

class Player160983966 extends GomokuPlayer {

  public static final String ANSI_RESET = "\033[0m";
  public static final String ANSI_BLACK = "\033[0;30m";
  public static final String ANSI_RED = "\033[0;31m";

  Map<Integer, Color[][]> storedBoards = new HashMap<Integer, Color[][]>();

  public Move chooseMove(Color[][] board, Color me) {
    printBoard(board);
    for (int row = 0; row < GomokuBoard.ROWS; row++)
			for (int col = 0; col < GomokuBoard.COLS; col++)
				if (board[row][col] == null)
					return new Move(row, col);
		return null;
	}

  public int minmax(Color[][] board, int depth, Color me) {
    int heuristicValue = Integer.MAX_VALUE;
    if (depth == 0 || checkIfVictory(board, me)) {
      return heuristicValue;
    }
    return 0;
  }

  public Boolean checkIfVictory(Color[][] board, Color me) {
    return true;
  }

  public int heuristicEvaluation(Color[][] board, Color me) {
    int[][] scoredBoard = new int[7][7];
    for (int x = 0; x < board.length; x++) {
      //COPY ARRAY
      // scoredBoard =
    }
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] != null) {
          checkVertical(board, me, i, j);
          checkHorizontal(board, me, i, j);
          checkDiagonalL(board, me, i, j);
          checkDiagonalR(board, me, i, j);
        }
      }
    }

    return 0;
  }

  private void checkVertical(Color[][] board, Color me, int row, int col) {
    int topEmptyPos = 0;
    int bottomEmptyPos = 0;
    int count = 0;
    for (int i = 0; i < 4; i++) {
      if (board[i][col] == null) {

      }
    }
  }

  private void checkHorizontal(Color[][] board, Color me, int row, int col) {

  }

  private void checkDiagonalL(Color[][] board, Color me, int row, int col) {

  }

  private void checkDiagonalR(Color[][] board, Color me, int row, int col) {

  }

  public void printBoard(Color[][] board) {
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

}
