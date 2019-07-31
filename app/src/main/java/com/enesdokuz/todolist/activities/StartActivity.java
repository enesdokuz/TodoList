package com.enesdokuz.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.enesdokuz.todolist.utils.PreferenceSingleton;
import com.enesdokuz.todolist.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (PreferenceSingleton.getInstance().getUserId() == -1) {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }
}
