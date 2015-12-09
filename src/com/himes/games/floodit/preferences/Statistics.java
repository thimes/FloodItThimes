package com.himes.games.floodit.preferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.himes.games.floodit.R;

public class Statistics extends Activity {

    public static final String HIGHEST_LEVEL = "HIGHESTLEVEL";
    public static final String BOARDS_FLOODED = "BOARDSFLOODED";
    public static final String PREFERENCE_KEY = "STATISTICSPREFERENCES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE);
        int highestLevel = sharedPreferences.getInt(HIGHEST_LEVEL, 1);
        int boardsFlooded = sharedPreferences.getInt(BOARDS_FLOODED, 0);
        
        TextView boardsFloodedTV = (TextView)findViewById(R.id.number_boards_flooded);
        boardsFloodedTV.setText(""+boardsFlooded);
        
        TextView highestLevelTV = (TextView)findViewById(R.id.highest_level);
        highestLevelTV.setText(""+highestLevel);
        
    }

}
