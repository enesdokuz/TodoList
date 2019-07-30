package com.enesdokuz.todolist.utils;

import android.app.Application;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 28.07.2019
 ***/
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceSingleton.getInstance().Initialize(this);
    }
}
