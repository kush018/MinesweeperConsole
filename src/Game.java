public class Game {
    //gameState stores all user actions (visible to user)
    private byte[][] gameState;
    //gameBoard stores positions of all mines and other data (invisible to user)
    private byte[][] gameBoard;

    //if the space has a mine (for gameBoard only)
    private static final int MINE = -1;
    //if the space has nothing
    private static final int NULL = 0;
    //if the user clicked on a tile
    private static final int CLICKED = 1;
    //if the user flagged a tile
    private static final int FLAGGED = 2;

    public Game(int p, int q, int mines) {
        //p - height of the game board
        //q - length of the game board
        //mines - no. of mines

        gameState = new byte[p][q];
        gameBoard = new byte[p][q];

        /*
        q ------------->
       p{{0, 0, 0, 0, 0},
       | {0, 0, 0, 0, 0},
       | {0, 0, 0, 0, 0}}
      \/
      */

        //fills gameBoard with NULLs
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = NULL;
            }
        }

        //fills gameState with NULLs
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                gameState[i][j] = NULL;
            }
        }

        //generates mines
        for (int i = 0; i < mines; i++) {
            int randomQ = (int) (Math.random() * q);
            int randomP = (int) (Math.random() * p);
            //checks if the chosen coordinates has a mine already
            if (gameBoard[randomP][randomQ] == NULL) {
                //if the part is free
                gameBoard[randomP][randomQ] = MINE;
            } else {
                //if the coordinates already has a mine
                //then redo
                i--;
            }
        }

        //generates mine indicators on all non mines
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (!(gameBoard[i][j] == MINE)) {
                    //if the thing is not a mine
                    gameBoard[i][j] = (byte) findMines(i, j);
                }
            }
        }
    }

    //finds number of mines around a given set of co-ordinates
    private int findMines(int p, int q) {
        if (gameBoard[p][q] == MINE) {
            //it is a mine so no point
            return -1;
        } else {
            //stores number of mines
            int count = 0;
            //not a mine; need to check
            try {
                if (gameBoard[p - 1][q - 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (gameBoard[p - 1][q] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (gameBoard[p - 1][q + 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (gameBoard[p][q + 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (gameBoard[p + 1][q + 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (gameBoard[p + 1][q] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (gameBoard[p + 1][q - 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            try {
                if (gameBoard[p][q - 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {}
            return count;
        }
    }

    public static int clickTile(int p, int q, int operation) {
        return 0;
    }
}
