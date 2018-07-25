package com.secureidltd.belemaogan.understandingpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView mDisplayTextView;
    private ConstraintLayout mBackgroundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisplayTextView = findViewById(R.id.textView);
        mBackgroundView = findViewById(R.id.background);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        Boolean isDark = sharedPreferences.getBoolean(getString(R.string.pref_background_color_key),
                getResources().getBoolean(R.bool.pref_background_color_default));
        modifyBackgroundColor(isDark);
        String language = sharedPreferences.getString(getString(R.string.pref_language_key),
                getString(R.string.pref_language_english_value));
        displayText(language);
    }


    private void modifyBackgroundColor(boolean isDark){
        if (isDark){
            mBackgroundView.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            mDisplayTextView.setTextColor(ContextCompat.getColor(this, R.color.white));
            //mDisplayTextView.setText(getString(R.string.hello_world_french));
        } else {
            mBackgroundView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            mDisplayTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
            //mDisplayTextView.setText(getString(R.string.hello_world_english));
        }
    }

    private void displayText(String language){
        if (language.equals(getString(R.string.pref_language_english_value))){
            mDisplayTextView.setText(getString(R.string.hello_world_english));
        } else if (language.equals(getString(R.string.pref_language_french_value))){
            mDisplayTextView.setText(getString(R.string.hello_world_french));
        } else if (language.equals(getString(R.string.pref_language_italian_value))){
            mDisplayTextView.setText(getString(R.string.hello_world_italian));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_background_color_key))) {
            Boolean isDark = sharedPreferences.getBoolean(key,
                    getResources().getBoolean(R.bool.pref_background_color_default));
            modifyBackgroundColor(isDark);
        } else if (key.equals(getString(R.string.pref_language_key))){
            String language = sharedPreferences.getString(key,
                    getString(R.string.pref_language_english_value));
            displayText(language);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
