import java.util.Arrays;

public class Game {
    //gameState stores all user actions (visible to user)
    private final int[][] gameState;
    //gameBoard stores positions of all mines and other data (invisible to user)
    private final int[][] gameBoard;

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

        gameState = new int[p][q];
        gameBoard = new int[p][q];

        //fills gameBoard with NULLs
        for (int[] ints : gameBoard) {
            Arrays.fill(ints, NULL);
        }

        //fills gameState with NULLs
        for (int[] ints : gameState) {
            Arrays.fill(ints, NULL);
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
                    gameBoard[i][j] = findMines(i, j);
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
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            try {
                if (gameBoard[p - 1][q] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            try {
                if (gameBoard[p - 1][q + 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            try {
                if (gameBoard[p][q + 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            try {
                if (gameBoard[p + 1][q + 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            try {
                if (gameBoard[p + 1][q] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            try {
                if (gameBoard[p + 1][q - 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            try {
                if (gameBoard[p][q - 1] == MINE) {
                    count++;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            return count;
        }
    }

    public static final int ALL_FINE = 0;
    public static final int ALREADY_CLICKED = 1;
    public static final int INVALID_POSITION = -1;
    public static final int PLAYER_LOST = -2;
    public static final int PLAYER_WON = 2;
    public static final int ALREADY_FLAGGED = -3;

    public int clickTile(int p, int q) {
        try {
            if (gameState[p][q] == NULL) {
                if (gameBoard[p][q] != MINE) {
                    //if not clicked on mine
                    gameState[p][q] = CLICKED;
                    if (gameBoard[p][q] == 0) {
                        //zero mines surround clicked area
                        clickTile(p - 1, q - 1);
                        clickTile(p - 1, q);
                        clickTile(p - 1, q + 1);
                        clickTile(p, q + 1);
                        clickTile(p + 1, q + 1);
                        clickTile(p + 1, q);
                        clickTile(p + 1, q - 1);
                        clickTile(p, q - 1);
                    }
                    if (checkWon()) {
                        return PLAYER_WON;
                    } else {
                        return ALL_FINE;
                    }
                } else {
                    //clicked on a mine
                    return PLAYER_LOST;
                }
            } else if (gameState[p][q] == FLAGGED) {
                return ALREADY_FLAGGED;
            } else {
                return ALREADY_CLICKED;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return INVALID_POSITION;
        }
    }

    public boolean checkWon() {
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                if ((gameState[i][j] == NULL || gameState[i][j] == FLAGGED) && gameBoard[i][j] != MINE) {
                    //if the positions i and j are not clicked and they also do not have a mine
                    //i.e a position i and j exists that is not clicked and not mined
                    return false;
                }
            }
        }
        //this means all the positions that are not clicked are mined
        return true;
    }

    public void display() {
        System.out.print("  q  ");
        for (int i = 0; i < gameState[0].length; i++) {
            System.out.print(i + "   ");
        }
        System.out.println();
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[i].length; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("p 0 ");
                } else if (j == 0) {
                    System.out.print("  " + i + " ");
                }
                if (gameState[i][j] == NULL) {
                    System.out.print("[_] ");
                } else if (gameState[i][j] == CLICKED) {
                    System.out.print("{" + gameBoard[i][j] + "} ");
                } else if (gameState[i][j] == FLAGGED) {
                    System.out.print("(F) ");
                }
            }
            System.out.println();
        }
    }

    public int flag(int p, int q) {
        try {
            if (gameState[p][q] == NULL) {
                gameState[p][q] = FLAGGED;
                return ALL_FINE;
            } else if (gameState[p][q] == FLAGGED) {
                gameState[p][q] = NULL;
                return ALL_FINE;
            } else {
                return ALREADY_CLICKED;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return INVALID_POSITION;
        }
    }

    public void displayLost() {
        System.out.print("  q  ");
        for (int i = 0; i < gameBoard[0].length; i++) {
            System.out.print(i + "   ");
        }
        System.out.println();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (j == 0 && i == 0) {
                    System.out.print("p 0 ");
                } else if (j == 0) {
                    System.out.print("  " + i + " ");
                }
                if (gameBoard[i][j] == MINE) {
                    System.out.print("<M> ");
                } else {
                    System.out.print("{" + gameBoard[i][j] + "} ");
                }
            }
            System.out.println();
        }
    }
}
