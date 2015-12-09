package com.himes.games.floodit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.himes.games.floodit.displays.SimpleSurfaceView;
import com.himes.games.floodit.model.FloodItGameModel;
import com.himes.games.floodit.preferences.About;
import com.himes.games.floodit.preferences.FloodItSettings;
import com.himes.games.floodit.preferences.Statistics;

public class FloodItMainActivity extends Activity {
    private static final String DEFAULT_DIFFICULTY_VALUE = "3";
    public static final int  REDRAW = 245; // get it? (F5hex = 245dec)
    public static final int  WIN    = 100;
    public static final int  LOSE   = -100;
    
    private FloodItGameModel model;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Handler thisHandler = getHandler();

        model = (FloodItGameModel) getLastNonConfigurationInstance();

        if (model == null) {
            model = new FloodItGameModel();
            model.newGame(getPreferredBoardSize());
        }

        model.setHandler(thisHandler);

        Display display = (SimpleSurfaceView) findViewById(R.id.game_board);

        display.setHandler(thisHandler);

        attachListenersToButtons(model, display);

        display.update(model);
    }

    /* TODO: implement this later... - page 41 of hello-android_b7_0.pdf
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    */

    @Override
    public Object onRetainNonConfigurationInstance() {
        return model;
    }

    private Handler getHandler() {
        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case REDRAW:
                    SimpleSurfaceView gameSurface = (SimpleSurfaceView) findViewById(R.id.game_board);
                    gameSurface.invalidate();
                    TextView movesLeftText = (TextView) findViewById(R.id.moves_left);
                    movesLeftText.setText(String.valueOf(msg.arg1));
                    TextView level = (TextView)findViewById(R.id.level);
                    level.setText(String.valueOf(model.getLevel() + 1));
                    break;
                case LOSE:
                    showDialog(LOSE);
                    break;
                case WIN:
                    showDialog(WIN);
                    break;
                }

                super.handleMessage(msg);
            }
        };
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        
        switch (id) {
        case LOSE:
            dialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.lose_title)
                        .setMessage(getString(R.string.lose_message, model.getLevel()+1))
                        .setPositiveButton(R.string.lose_button, 
                                new OnClickListener() {
                                    @Override
                        			public void onClick(DialogInterface dialogInterface, int iThinkThisIsAMask) {
                                        int level = model.getLevel() + 1;
                                        possiblyUpdateHighestLevel(level);
                                        
                            			model.startOver();
                            			Display display = (SimpleSurfaceView) findViewById(R.id.game_board);
                            			display.update(model);
                        			}
                    			}
                            )
                    	.create();
            break;
        case WIN:
            dialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.win_title)
//                        .setMessage(getString(R.string.win_message))
                        .setMessage(String.format(getString(R.string.win_message), model.getLevel()+2, model.getMovesUsed(), model.getMovesLeft()))
                        .setPositiveButton(R.string.win_button, 
                                new OnClickListener() {
                                    @Override
                        			public void onClick(DialogInterface dialogInterface, int iThinkThisIsAMask) {
                                        incrementNumberOfBoardsFlooded();
                                        model.nextLevel();
                            			Display display = (SimpleSurfaceView) findViewById(R.id.game_board);
                            			display.update(model);
                        			}
                    			}
                            )
                    	.create();
            break;
        default:
            dialog = super.onCreateDialog(id);
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case LOSE:
                ((TextView)dialog.getWindow().findViewById(android.R.id.message)).setText(getString(R.string.lose_message, model.getLevel()+1));
            	break;
            case WIN:
                ((TextView)dialog.getWindow().findViewById(android.R.id.message)).setText(String.format(getString(R.string.win_message), model.getLevel()+2, model.getMovesUsed(), model.getMovesLeft()));
            default:
        		super.onPrepareDialog(id, dialog);
        		break;
        }
    }

    private void possiblyUpdateHighestLevel(int level) {
        SharedPreferences preferences = getSharedPreferences(Statistics.PREFERENCE_KEY, MODE_PRIVATE);
        if (level > preferences.getInt(Statistics.HIGHEST_LEVEL, 1)) {
            preferences.edit().putInt(Statistics.HIGHEST_LEVEL, (level)).commit();
        }
    }
    
    private void incrementNumberOfBoardsFlooded() {
        SharedPreferences preferences = getSharedPreferences(Statistics.PREFERENCE_KEY, MODE_PRIVATE);
        preferences.edit().putInt(Statistics.BOARDS_FLOODED, preferences.getInt(Statistics.BOARDS_FLOODED, 1) + 1).commit();
    }

    private void attachListenersToButtons(FloodItGameModel model, Display display) {
        FloodButtonClickListener clickListener = new FloodButtonClickListener(this, model, display);

        Button button;

        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(clickListener);

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(clickListener);

        button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(clickListener);

        button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(clickListener);

        button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(clickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.settings).setIcon(android.R.drawable.ic_menu_manage);
        menu.findItem(R.id.statistics).setIcon(android.R.drawable.ic_menu_agenda);
        menu.findItem(R.id.about).setIcon(android.R.drawable.ic_menu_help);
        menu.findItem(R.id.email_developer).setIcon(android.R.drawable.ic_menu_send);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.settings:
            startActivityForResult(new Intent(this, FloodItSettings.class), R.id.settings);
            return true;
        case R.id.statistics:
            startActivity(new Intent(this, Statistics.class));
            return true;
        case R.id.about:
            startActivity(new Intent(this, About.class));
            return true;
        case R.id.email_developer:
            /* Create the Intent */  
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
      
            /* Fill it with Data */  
            emailIntent.setType("plain/text");  
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"travis.himes+floodIt@gmail.com"});  
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Flood It User Comment");  
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, I had something I wanted to say about Flood It...");  
      
            /* Send it off to the Activity-Chooser */  
            startActivity(Intent.createChooser(emailIntent, "Send mail to developer..."));  
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case R.id.settings:
                int currentSize = model.getBoard().length;
                int newSize = getPreferredBoardSize();
                if (newSize != currentSize) {
                    model.newGame(newSize);
                }
                Display display = (SimpleSurfaceView) findViewById(R.id.game_board);
                display.update(model);
                return;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private int getPreferredBoardSize() {
        int newSize;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            newSize = Integer.parseInt(preferences.getString(getString(R.string.difficulty_key), DEFAULT_DIFFICULTY_VALUE));
        } catch (Exception e) {
            newSize = 3;
        }
        return newSize;
    }
    
}