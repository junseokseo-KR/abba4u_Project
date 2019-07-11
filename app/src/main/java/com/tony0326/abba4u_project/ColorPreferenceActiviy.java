package com.tony0326.abba4u_project;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.tony0326.abba4u_project.R;

public class ColorPreferenceActiviy extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.colorpreference);
    }
}
