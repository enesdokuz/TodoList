package com.enesdokuz.todolist.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Date;

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

    public String getDate() {
        String result;
        Date d = new Date();
        CharSequence s = DateFormat.format("d-M-yyyy", d.getTime());
        result = s + "";
        return result;
    }

}
