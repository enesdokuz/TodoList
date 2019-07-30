package com.enesdokuz.todolist.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 28.07.2019
 ***/
public class PreferenceSingleton {
    private static PreferenceSingleton mInstance;
    private Context mContext;
    private SharedPreferences preferences;
    private static String TAG = "PreferenceSingleton";

    private PreferenceSingleton() {
    }

    public static PreferenceSingleton getInstance() {
        if (mInstance == null) mInstance = new PreferenceSingleton();
        return mInstance;
    }

    public void Initialize(Context context) {
        mContext = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void writePref(String key, String value) {
        SharedPreferences.Editor e = preferences.edit();
        e.putString(key, value);
        e.apply();
    }

    public void setUserId(int _user_id) {
        SharedPreferences.Editor e = preferences.edit();
        e.putInt("user_id", _user_id);
        e.apply();
        Log.e(TAG, _user_id + "");
    }

    public int getUserId() {
        Log.e(TAG, preferences.getInt("user_id", -1) + "");
        return preferences.getInt("user_id", -1);
    }

    public void setListNameSortType(String _type) {
        SharedPreferences.Editor e = preferences.edit();
        e.putString("list_sort_type", _type);
        e.apply();
        Log.e(TAG, _type + "");
    }

    public String getListNameSortType() {
        Log.e(TAG, preferences.getString("list_sort_type", "") + "");
        return preferences.getString("list_sort_type", "");
    }

    public void setListId(int _list_id) {
        SharedPreferences.Editor e = preferences.edit();
        e.putInt("list_id", _list_id);
        e.apply();
        Log.e(TAG, _list_id + "");
    }

    public int getListId() {
        Log.e(TAG, preferences.getInt("list_id", -1) + "");
        return preferences.getInt("list_id", -1);
    }

    public void setTodoOrderType(String _todo_order_type) {
        SharedPreferences.Editor e = preferences.edit();
        e.putString("todo_order_type", _todo_order_type);
        e.apply();
        Log.e(TAG, _todo_order_type + "");
    }

    public String getTodoOrderType() {
        Log.e(TAG, preferences.getInt("todo_order_type", -1) + "");
        return preferences.getString("todo_order_type", "status");
    }
}
