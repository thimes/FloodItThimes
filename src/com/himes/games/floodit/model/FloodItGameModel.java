package com.himes.games.floodit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Handler;
import android.os.Message;

import com.himes.games.floodit.FloodItMainActivity;

public class FloodItGameModel {

    private int              originalMovesLeft;
    private int              movesLeft, movesUsed;
    private int              level;
    private int[][]          board;
    private int              floodColor = -1;
    private Handler          handler;

    public static final int  COLOR1     = 0;
    public static final int  COLOR2     = 1;
    public static final int  COLOR3     = 2;
    public static final int  COLOR4     = 3;
    public static final int  COLOR5     = 4;
    private static final int TEMPCOLOR  = -1;

    /**
     * Creates a new game.
     * 
     * @param size the number of blocks of 1 side of the game board
     * @param numberOfMoves the number of moves the player has to complete the puzzle
     */
    public void newGame(int size, int numberOfMoves) {
        level = 0;
        originalMovesLeft = numberOfMoves;
        initializeBoard(size, numberOfMoves);
    }
    
    public void newGame(int boardSize) {
        newGame(boardSize, (2*boardSize) + 3);
    }
    
    private void initializeBoard(int size, int numberOfMoves) {
        Random r = new Random(System.currentTimeMillis());
        board = new int[size][size];
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[x][y] = r.nextInt(5);
            }
        }
        floodColor = board[0][0];
        movesLeft = numberOfMoves;
        movesUsed = 0;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void setFloodColor(int floodColor) {
        this.floodColor = floodColor;
    }

    public void move(int color) {
        if (color != floodColor) {
            if (!boardHasBeenFlooded() && !thereAreNoMovesLeft()) {
                flood(color);
                floodColor = color;
                movesLeft--;
                movesUsed++;
            }
            // um, eww, this has to happen after we flood and decrement the counter, oh well
            if (boardHasBeenFlooded()) {
                notifyOfWin();
            } else if (thereAreNoMovesLeft()) {
                notifyOfLoss();
            }
        }
    }

    private void notifyOfLoss() {
        Message msg = new Message();
        msg.what = FloodItMainActivity.LOSE;
        handler.sendMessage(msg);
    }
    
    private void notifyOfWin() {
        Message msg = new Message();
        msg.what = FloodItMainActivity.WIN;
        handler.sendMessage(msg);
    }

    public final boolean thereAreNoMovesLeft() {
        return movesLeft == 0;
    }

    public final boolean boardHasBeenFlooded() {
        boolean allColorsTheSame = true;
        for (int i = 0; i < board.length && allColorsTheSame; i++) {
            for (int j = 0; j < board[i].length && allColorsTheSame; j++) {
                allColorsTheSame = board[i][j] == floodColor;
            }
        }
        return allColorsTheSame;
    }

    private void flood(int color) {
        List<int[]> addressesToWork = new ArrayList<int[]>();
        addressesToWork.add(new int[] { 0, 0 });
        board[0][0] = TEMPCOLOR;

        while (!addressesToWork.isEmpty()) {
            int[] i = addressesToWork.get(0);
            addressesToWork.remove(0);
            int y = i[1];
            int x = i[0];

            if (x > 0 && board[x - 1][y] == floodColor) {
                board[x - 1][y] = TEMPCOLOR;
                addressesToWork.add(new int[] { x - 1, y });
            }
            if (y > 0 && board[x][y - 1] == floodColor) {
                board[x][y - 1] = TEMPCOLOR;
                addressesToWork.add(new int[] { x, y - 1 });
            }
            if (x + 1 < board.length && board[x + 1][y] == floodColor) {
                board[x + 1][y] = TEMPCOLOR;
                addressesToWork.add(new int[] { x + 1, y });
            }
            if (y + 1 < board[x].length && board[x][y + 1] == floodColor) {
                board[x][y + 1] = TEMPCOLOR;
                addressesToWork.add(new int[] { x, y + 1 });
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == -1) {
                    board[i][j] = color;
                }
            }
        }

    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public int getMovesUsed() {
        return movesUsed;
    }
    public void setHandler(Handler thisHandler) {
        this.handler = thisHandler;
    }
    
    public void startOver() {
        level = 0;
        initializeBoard(board.length, originalMovesLeft);
    }

    public void nextLevel() {
        level++;
        initializeBoard(board.length, originalMovesLeft - level);
    }
    
    public int getLevel() {
        return level;
    }


}
