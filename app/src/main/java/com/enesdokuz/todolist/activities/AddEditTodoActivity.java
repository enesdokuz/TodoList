package com.enesdokuz.todolist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.enesdokuz.todolist.R;
import com.enesdokuz.todolist.utils.Constants;

public class AddEditTodoActivity extends AppCompatActivity {

    private EditText editTitle, editDescription;
    private NumberPicker pickerDay, pickerMonth, pickerYear;
    private CheckBox checkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_todo);

        editTitle = findViewById(R.id.editTitle_addEditTodo);
        editDescription = findViewById(R.id.editDescription_addEditTodo);
        pickerDay = findViewById(R.id.pickerDay_addEditTodo);
        pickerMonth = findViewById(R.id.pickerMonth_addEditTodo);
        pickerYear = findViewById(R.id.pickerYear_addEditTodo);
        checkStatus = findViewById(R.id.checkStatus_addEditTodo);

        pickerDay.setMinValue(1);
        pickerDay.setMaxValue(31);
        pickerMonth.setMinValue(1);
        pickerMonth.setMaxValue(12);
        pickerYear.setMinValue(2018);
        pickerYear.setMaxValue(2030);
        pickerDay.setWrapSelectorWheel(true);
        pickerMonth.setWrapSelectorWheel(true);
        pickerYear.setWrapSelectorWheel(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.EXTRA_ID_ITEM)) {

            setTitle(getResources().getString(R.string.edit_todo));
            editTitle.setText(intent.getStringExtra(Constants.EXTRA_NAME_ITEM));
            editDescription.setText(intent.getStringExtra(Constants.EXTRA_DESCRIPTION_ITEM));
            pickerDay.setValue(Integer.valueOf(intent.getStringExtra(Constants.EXTRA_DEADLINE_DAY_ITEM)));
            pickerMonth.setValue(Integer.valueOf(intent.getStringExtra(Constants.EXTRA_DEADLINE_MONTH_ITEM)));
            pickerYear.setValue(Integer.valueOf(intent.getStringExtra(Constants.EXTRA_DEADLINE_YEAR_ITEM)));
            checkStatus.setChecked(intent.getBooleanExtra(Constants.EXTRA_STATUS_ITEM, false));

        } else {
            setTitle(getResources().getString(R.string.add_todo));
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
                saveTodo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveTodo() {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();
        String deadline_day = pickerDay.getValue() + "";
        String deadline_month = pickerMonth.getValue() + "";
        String deadline_year = pickerYear.getValue() + "";
        Boolean status = checkStatus.isChecked();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {

            Toast.makeText(this, getResources().getString(R.string.fill_in_the_blanks) + "", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(Constants.EXTRA_NAME_ITEM, title);
        data.putExtra(Constants.EXTRA_DESCRIPTION_ITEM, description);
        data.putExtra(Constants.EXTRA_CREATE_DATE_ITEM, getIntent().getStringExtra(Constants.EXTRA_CREATE_DATE_ITEM));
        data.putExtra(Constants.EXTRA_DEADLINE_DAY_ITEM, deadline_day);
        data.putExtra(Constants.EXTRA_DEADLINE_MONTH_ITEM, deadline_month);
        data.putExtra(Constants.EXTRA_DEADLINE_YEAR_ITEM, deadline_year);
        data.putExtra(Constants.EXTRA_STATUS_ITEM, status);

        int id = getIntent().getIntExtra(Constants.EXTRA_ID_ITEM, -1);
        if (id != -1) {
            data.putExtra(Constants.EXTRA_ID_ITEM, id);

        }
        setResult(RESULT_OK, data);
        finish();
    }
}
