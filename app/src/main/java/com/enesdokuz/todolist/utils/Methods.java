package com.enesdokuz.todolist.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 28.07.2019
 ***/
public class Methods {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String device_id;

    public Methods(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public int getUserId() {
        return preferences.getInt("user_id", -1);
    }

}
