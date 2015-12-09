package com.himes.games.floodit;

import android.os.Handler;

import com.himes.games.floodit.model.FloodItGameModel;

public interface Display {
    
    void update(FloodItGameModel model);
    
    void setHandler(Handler handler);

}
