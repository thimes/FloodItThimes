package com.himes.games.floodit;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.himes.games.floodit.model.FloodItGameModel;

public class FloodButtonClickListener implements OnClickListener {

    private static final String TAG = "FloodButtonClickListener";
    private FloodItMainActivity mainActivity;
    private FloodItGameModel model;
    private Display display;
    
    public FloodButtonClickListener(FloodItMainActivity floodIt, FloodItGameModel model, Display display) {
        this.mainActivity = floodIt;
        this.model = model;
        this.display = display;
    }
    
    @Override
    public void onClick(View clickedView) {
        switch(clickedView.getId()) {
            case(R.id.button1):
                model.move(FloodItGameModel.COLOR1);
            	break;
            case(R.id.button2):
                model.move(FloodItGameModel.COLOR2);
            	break;
            case(R.id.button3):
                model.move(FloodItGameModel.COLOR3);
            	break;
            case(R.id.button4):
                model.move(FloodItGameModel.COLOR4);
            	break;
            case(R.id.button5):
                model.move(FloodItGameModel.COLOR5);
            	break;
            default:
            	Toast.makeText(mainActivity, "NO_IDEA", Toast.LENGTH_LONG).show();
            	Log.d(TAG, "unknown button pressed, button id = " + clickedView.getId());
        }
        display.update(model);
    }
}
