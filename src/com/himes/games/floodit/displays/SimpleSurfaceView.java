package com.himes.games.floodit.displays;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.himes.games.floodit.Display;
import com.himes.games.floodit.FloodItMainActivity;
import com.himes.games.floodit.R;
import com.himes.games.floodit.model.FloodItGameModel;

public class SimpleSurfaceView extends SurfaceView implements Display, SurfaceHolder.Callback {

    public SurfaceHolder mHolder;
    public Handler surfaceHandler;

    public SimpleSurfaceView(Context c) {
        super(c);
        init();
    }

    public void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    }

    public SimpleSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    private Paint paint1, paint4, paint2, paint3, paint5;

    List<Paint>   paints;

    public SimpleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        setFocusable(true); // make sure we get key events
        paints = new ArrayList<Paint>();
        
        Resources res = getResources();

        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(res.getColor(R.color.red));
        paints.add(paint1);

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(res.getColor(R.color.yellow));
        paints.add(paint2);

        paint3 = new Paint();
        paint3.setAntiAlias(true);
        paint3.setColor(res.getColor(R.color.green));
        paints.add(paint3);
        
        paint4 = new Paint();
        paint4.setAntiAlias(true);
        paint4.setColor(res.getColor(R.color.cyan));
        paints.add(paint4);

        paint5 = new Paint();
        paint5.setAntiAlias(true);
        paint5.setColor(res.getColor(R.color.blue));
        paints.add(paint5);

    }

    int[][]board;
    private int movesLeft;
    
    public void update(FloodItGameModel model) {
        this.board = model.getBoard();
        this.movesLeft = model.getMovesLeft();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (board == null || canvas == null) {
            return;
        }

        canvas.drawColor(Color.DKGRAY);

        int blockWidth = getWidth() / (board.length + 1);
        int blockHeight = getHeight() / (board.length + 1);
        int blockSize = ( blockWidth < blockHeight ? blockWidth : blockHeight );
        int boardSpace = board.length * blockSize;
        
        int leftGutter = (getWidth() - boardSpace) / 2;
        int topGutter = (getHeight() - boardSpace) / 2;
        
        RectF scratchRect = new RectF(0, 0, 0, 0);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int x1 = leftGutter + ((i) * blockSize);
                int x2 = leftGutter + ((i+1) * blockSize);
                int y1 = topGutter + ((j) * blockSize);
                int y2 = topGutter + ((j+1) * blockSize);
                scratchRect.set(x1, y1, x2, y2);
                canvas.drawRect(scratchRect, paints.get(board[i][j]));
            }
        }
        Message msg = new Message();
        msg.what = FloodItMainActivity.REDRAW;
        msg.arg1 = movesLeft;
        surfaceHandler.sendMessage(msg);
    }

    @Override
    public void setHandler(Handler handler) {
        surfaceHandler = handler;
    }

}
