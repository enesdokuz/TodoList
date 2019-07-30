package com.enesdokuz.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.enesdokuz.todolist.R;
import com.enesdokuz.todolist.utils.Constants;

public class AddEditListActivity extends AppCompatActivity {

    private EditText editTitle, editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_list);

        editTitle = findViewById(R.id.editTitle_addList);
        editDescription = findViewById(R.id.editDescription_addList);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_ID_LIST)) {

            setTitle(getString(R.string.edit_list));
            editTitle.setText(intent.getStringExtra(Constants.EXTRA_TITLE_LIST));
            editDescription.setText(intent.getStringExtra(Constants.EXTRA_DESCRIPTION_LIST));
        } else {

            setTitle(getString(R.string.add_list));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_list_menu:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {

            Toast.makeText(this, getResources().getString(R.string.fill_in_the_blanks)+"", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(Constants.EXTRA_TITLE_LIST, title);
        data.putExtra(Constants.EXTRA_DESCRIPTION_LIST, description);

        int id = getIntent().getIntExtra(Constants.EXTRA_ID_LIST, -1);
        if (id != -1) {
            data.putExtra(Constants.EXTRA_ID_LIST, id);

        }
        setResult(RESULT_OK, data);
        finish();
    }
}
