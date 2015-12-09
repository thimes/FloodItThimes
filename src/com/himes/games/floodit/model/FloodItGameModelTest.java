package com.himes.games.floodit.model;

import junit.framework.TestCase;

public class FloodItGameModelTest extends TestCase {
    
    public void testInitializationWorks() {
        FloodItGameModel model = new FloodItGameModel();
        int[][] board = {{1,2,3,4},
                         {2,2,3,4},
                         {3,3,3,4},
                         {4,4,4,4}};
        model.setBoard(board);
        assertEquals(board, model.getBoard());
    }
    
    public void testSpreadingRightWorks() {
        FloodItGameModel model = new FloodItGameModel();
        model.setFloodColor(1);
        int[][] boardBefore = {{1,1,1,4},
                               {4,3,1,4},
                               {2,2,1,4},
                               {4,4,4,4}};
        model.setBoard(boardBefore);
        model.move(2);
        int[][] boardAfter  = {{2,2,2,4},
                               {4,3,2,4},
                               {2,2,2,4},
                               {4,4,4,4}};
        assertTrue(compareBoards(boardAfter, model.getBoard()));
    }
    
    public void testSpreadingDownWorks() {
        FloodItGameModel model = new FloodItGameModel();
        model.setFloodColor(1);
        int[][] boardBefore = {{1,1,1,4},
                               {4,3,1,4},
                               {2,2,1,4},
                               {4,4,4,4}};
        model.setBoard(boardBefore);
        model.move(2);
        int[][] boardAfter  = {{2,2,2,4},
                               {4,3,2,4},
                               {2,2,2,4},
                               {4,4,4,4}};
        assertTrue(compareBoards(boardAfter, model.getBoard()));
    }
    
    public void testSpreadingLeftWorks() {
        FloodItGameModel model = new FloodItGameModel();
        model.setFloodColor(1);
        int[][] boardBefore = {{1,1,1,4},
                               {4,3,1,4},
                               {2,1,1,4},
                               {4,4,4,4}};
        model.setBoard(boardBefore);
        model.move(3);
        int[][] boardAfter  = {{3,3,3,4},
                               {4,3,3,4},
                               {2,3,3,4},
                               {4,4,4,4}};
        assertTrue(compareBoards(boardAfter, model.getBoard()));
    }
    
    public void testSpreadingUpWorks() {
        FloodItGameModel model = new FloodItGameModel();
        model.setFloodColor(2);
        int[][] boardBefore = {{2,2,2,2},
                               {4,4,4,2},
                               {4,2,4,2},
                               {2,2,2,2}};
        model.setBoard(boardBefore);
        model.move(3);
        int[][] boardAfter  = {{3,3,3,3},
                               {4,4,4,3},
                               {4,3,4,3},
                               {3,3,3,3}};
        assertTrue(compareBoards(boardAfter, model.getBoard()));
    }
    
    private boolean compareBoards(int[][] boardAfter, int[][] board) {
        boolean equal = true;
        for (int i = 0; i < board.length && equal; i++) {
            for (int j = 0; j < board[i].length && equal; j++) {
                equal = boardAfter[i][j] == board [i][j];
            }
        }
        return equal;
    }
    
}
