package com.himes.games.floodit.preferences;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

import com.himes.games.floodit.R;

public class FloodItSettings extends PreferenceActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setPreferenceScreen(createPreferenceHierarchy());
        registerForContextMenu(getListView());
    }

    private PreferenceScreen createPreferenceHierarchy() {

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        ListPreference preference = new ListPreference(this);
        preference.setTitle(getString(R.string.difficulty_preference_title));
        preference.setSummary(getString(R.string.difficulty_summary));
        preference.setKey(getString(R.string.difficulty_key));
        preference.setEntries(R.array.difficulty_titles);
        preference.setEntryValues(R.array.difficulty_values);

        root.addPreference(preference);

        return root;
    }
}
