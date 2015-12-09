package com.himes.games.floodit.displays;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.himes.games.floodit.Display;
import com.himes.games.floodit.model.FloodItGameModel;

public class LogCatDisplay implements Display {

    Context context;
    
    public LogCatDisplay(Context context) {
        this.context = context;
    }

    @Override
    public void update(FloodItGameModel model) {
        int[][] board = model.getBoard();
          String TAG = "MODEL : ";
          Log.v(TAG, "Moves Left : " + model.getMovesLeft());
          int numRows = board.length;
          int numCols = board[0].length;
          for (int y = 0; y < numRows; y++) {
              StringBuilder sb = new StringBuilder();
              for (int x = 0; x < numCols; x++) {
                  sb.append(board[y][x]).append(" ");
              }
              Log.v(TAG, sb.toString());
          }
    }

    @Override
    public void setHandler(Handler handler) {
        // TODO Auto-generated method stub
        
    }

}
