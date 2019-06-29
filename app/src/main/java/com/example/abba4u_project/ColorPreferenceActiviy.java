package com.example.abba4u_project;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ColorPreferenceActiviy extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.colorpreference);
    }
}
