import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

class Player160983966 extends GomokuPlayer {
    private boolean firstMove = true;
    private boolean printOnce = true;
    public Move chooseMove(Color[][] board, Color me) {
        try {
            //First move
            if (me == Color.white && firstMove && board[4][4] == null) {
                firstMove = false;
                Move first = new Move(4,4);
                return first;
            } else {
                firstMove = false;
            }
            Move bestMove = new Move(0,0);
            int bestScore = 0;
            if (me == Color.white) {
                bestScore = Integer.MIN_VALUE;
            } else {
                bestScore = Integer.MAX_VALUE;
            }
            int score = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == null) {
                        board[i][j] = me;
                        printOnce = true;
                        score = minmax(board, 4, (me == Color.white) ? Color.black : Color.white, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        System.out.println(score);
                        board[i][j] = null;
                        if (me == Color.white) {
                            //Max score
                            if (score == Integer.MAX_VALUE) {
                                return new Move(i, j);
                            }
                            if (score > bestScore) {
                                Move newMove = new Move(i, j);
                                bestScore = score;
                                bestMove = newMove;
                            }
                        } else {
                            //Min score
                            if (score == Integer.MIN_VALUE) {
                                return new Move(i, j);
                            }
                            if (score < bestScore) {
                                Move newMove = new Move(i, j);
                                bestScore = score;
                                bestMove = newMove;
                            }
                        }
                        // if (score > bestScore) {
                        //     bestScore = score;
                        //     bestMove = newMove;
                        // }
                    }
                }
            }
            if (board[bestMove.row][bestMove.col] != null) {
                //Happens when no move possible i.e. it has lost
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        if (board[i][j] == null) {
                            return new Move(i, j);
                        }
                    }
                }
            }
            System.out.println("Best Score found: " + bestScore);
            return bestMove;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        // for (int row = 0; row < GomokuBoard.ROWS; row++)
		// 	for (int col = 0; col < GomokuBoard.COLS; col++)
		// 		if (board[row][col] == null) {
        //             System.out.println("Default move");
		// 			return new Move(row, col);
        //         }
        //
        // return null;
    }

    public int minmax(Color[][] board, int depth, Color me, int alpha, int beta) {
        if (depth == 4 && checkIfVictory(board, (me == Color.white) ? Color.black : Color.white)) {
            if (me == Color.white) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }
        if (depth == 0) {
            // System.out.println("Terminal state reached");
            return heuristicEvaluation(board, me);
        }
        if (me == Color.white) {
            // if (!firstMove) {
            //     firstMove = true;
            //     return new MoveScore(0, new Move(3,3));
            // }
            //Maximizing Player
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] == null) {
                        board[i][j] = Color.white;
                        int moveScore = minmax(board, depth - 1, Color.black, alpha, beta);
                        board[i][j] = null;
                        // moveScore = heuristicEvaluation(board, me);
                        if (moveScore > best) {
                            // System.out.println("-------");
                            // System.out.println("New Best Max Move");
                            // System.out.println("Max score: " + moveScore.score);
                            best = moveScore;
                        }
                        if (moveScore >= beta) {
                            return heuristicEvaluation(board, me);
                        }
                        if (moveScore > alpha) {
                            alpha = moveScore;
                        }
                    }
                }
            }
            return best;
        } else {
            //Minimizing Player
            firstMove = true;
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                for ( int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == null) {
                        board[i][j] = Color.black;
                        int moveScore = minmax(board, depth - 1, Color.white, alpha, beta);
                        board[i][j] = null;
                        // moveScore = heuristicEvaluation(board, me);
                        if (moveScore < best) {
                            // System.outopn("Min score: " + moveScore.score);
                            best = moveScore;
                        }
                        if (moveScore <= alpha) {
                            return heuristicEvaluation(board, me);
                        }
                        if (moveScore < beta) {
                            beta = moveScore;
                        }
                    }
                }
            }
            return best;
        }

    }

    public Boolean checkIfVictory(Color[][] board, Color me) {
        int score = heuristicEvaluation(board, me);
        if (score > 100000) {
            if (me == Color.white) {
                return true;
            }
        }
        if (score < -100000) {
            if (me == Color.black) {
                return true;
            }
        }
        return false;
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
        if (printOnce) {
            printOnce = false;
            printScoredBoard(board, scoredBoard);
        }
        return getScore(scoredBoard);
    }

    private int getScore(squareScore[][] scoredBoard) {
        int score = 0;
        int whiteScore = 0;
        int blackScore = 0;
        for (int row = 0; row < scoredBoard.length; row++) {
            for (int col = 0; col < scoredBoard[row].length; col++) {
                //White scoring
                if (scoredBoard[row][col].whitePoint > 2) {
                    //Chains of 3
                    whiteScore += 3;
                }
                if (scoredBoard[row][col].whitePoint == 2) {
                    //Chains of 3
                    whiteScore += 1;
                }

                //Black scoring
                if (scoredBoard[row][col].blackPoint > 2) {
                    //Chains of 3
                    blackScore += 3;
                }
                if (scoredBoard[row][col].blackPoint == 2) {
                    //Chains of 3
                    blackScore += 1;
                }
                if (scoredBoard[row][col].whitePoint == Integer.MAX_VALUE) {
                    whiteScore = Integer.MAX_VALUE;
                }
                if (scoredBoard[row][col].blackPoint == Integer.MAX_VALUE) {
                    blackScore = Integer.MAX_VALUE;
                }
            }
        }
        if (whiteScore > blackScore) {
            score = whiteScore;
        } else {
            score = -blackScore;
        }
        // System.out.println("Score: " + score);
        return score;
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
                //Add infinity for black
                scoredBoard[row][col].blackPoint = Integer.MAX_VALUE;
            } else if (whiteCount == 4) {
                //Add infinity for white
                scoredBoard[row][col].whitePoint = Integer.MAX_VALUE;
            } else {
                // if (right) {
                //     //right to left checking for xxx0x
                //     if (blackCount == 3) {
                //         if ((col+1) <= 7) {
                //             if (board[row][col+1] == Color.black) {
                //                 scoredBoard[row][col].blackPoint = Integer.MAX_VALUE;
                //             }
                //         }
                //     } else if (whiteCount == 3) {
                //         if ((col+1) <= 7) {
                //             if (board[row][col+1] == Color.white) {
                //                 scoredBoard[row][col].whitePoint = Integer.MAX_VALUE;
                //             }
                //         }
                //     }
                //     //Right to left checking for xx0xx
                //     if (blackCount == 2) {
                //
                //     } else if (whiteCount == 2) {
                //
                //     }
                // } else {
                //     //left to right checking for x0xxx
                //     if (blackCount == 3) {
                //         if ((col-1) >= 0) {
                //             if (board[row][col-1] == Color.black) {
                //                 scoredBoard[row][col].blackPoint = Integer.MAX_VALUE;
                //             }
                //         }
                //     } else if (whiteCount == 3) {
                //         if ((col-1) >= 0) {
                //             if (board[row][col-1] == Color.white) {
                //                 scoredBoard[row][col].whitePoint = Integer.MAX_VALUE;
                //             }
                //         }
                //     }
                // }
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
                    System.out.print("| B |");
                } else if (board[i][j] == Color.white) {
                    System.out.print("| W |");
                } else {
                    System.out.print("| " + ((scoredBoard[i][j].whitePoint > 1000) ? "X" : scoredBoard[i][j].whitePoint) + "," + ((scoredBoard[i][j].blackPoint > 1000) ? "X" : scoredBoard[i][j].blackPoint) + " |");
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

class MoveScore {
    public int score;
    public Move move;

    public MoveScore() {
        score = 0;
        move = new Move(0,0);
    }

    public MoveScore(int score) {
        score = 0;
        this.move = new Move(3, 3);
    }

    public MoveScore(int score, Move move) {
        this.score = score;
        this.move = move;
    }

}
