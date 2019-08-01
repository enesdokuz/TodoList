package com.enesdokuz.todolist.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.enesdokuz.todolist.R;
import com.enesdokuz.todolist.TodoAdapter;
import com.enesdokuz.todolist.model.Todo;
import com.enesdokuz.todolist.utils.Constants;
import com.enesdokuz.todolist.utils.Methods;
import com.enesdokuz.todolist.utils.PreferenceSingleton;
import com.enesdokuz.todolist.viewmodel.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    public static final int ADD_TODO_REQUEST = 10;
    public static final int EDIT_TODO_REQUEST = 11;
    private TodoViewModel todoViewModel;
    private Methods methods;
    private TodoAdapter todoAdapter;
    private RecyclerView recyclerView;
    private List<Todo> todosForShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        methods = new Methods(TodoActivity.this);
        FloatingActionButton btnAdd = findViewById(R.id.btn_add_todo);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodoActivity.this, AddEditTodoActivity.class);
                startActivityForResult(intent, ADD_TODO_REQUEST);
            }
        });

        recyclerView = findViewById(R.id.recycler_todo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        todoAdapter = new TodoAdapter();
        recyclerView.setAdapter(todoAdapter);

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllTodo().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {

                todoAdapter.setTodos(todos);
                todosForShare = todos;
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                todoViewModel.delete(todoAdapter.getTodoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TodoActivity.this, "" + getResources().getString(R.string.todo_deleted), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        todoAdapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                //Nothing in now
            }
        });

        todoAdapter.setOnLongItemClickListener(new TodoAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(Todo todo) {
                String[] deadline = todo.getDeadline().split("-");

                Intent intent = new Intent(TodoActivity.this, AddEditTodoActivity.class);
                intent.putExtra(Constants.EXTRA_ID_ITEM, todo.getId());
                intent.putExtra(Constants.EXTRA_NAME_ITEM, todo.getName());
                intent.putExtra(Constants.EXTRA_DESCRIPTION_ITEM, todo.getDescription());
                intent.putExtra(Constants.EXTRA_STATUS_ITEM, todo.getStatus());
                intent.putExtra(Constants.EXTRA_DEADLINE_DAY_ITEM, deadline[0] + "");
                intent.putExtra(Constants.EXTRA_DEADLINE_MONTH_ITEM, deadline[1] + "");
                intent.putExtra(Constants.EXTRA_DEADLINE_YEAR_ITEM, deadline[2] + "");
                intent.putExtra(Constants.EXTRA_CREATE_DATE_ITEM, todo.getCreate_date());
                startActivityForResult(intent, EDIT_TODO_REQUEST);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(Constants.EXTRA_NAME_ITEM);
            String description = data.getStringExtra(Constants.EXTRA_DESCRIPTION_ITEM);
            String createDate = methods.getDate();
            String deadline = data.getStringExtra(Constants.EXTRA_DEADLINE_DAY_ITEM) + "-" + data.getStringExtra(Constants.EXTRA_DEADLINE_MONTH_ITEM) + "-" + data.getStringExtra(Constants.EXTRA_DEADLINE_YEAR_ITEM);
            Boolean status = data.getBooleanExtra(Constants.EXTRA_STATUS_ITEM, false);

            Todo todo = new Todo(PreferenceSingleton.getInstance().getListId(), PreferenceSingleton.getInstance().getUserId(), title, description, deadline, createDate, status);
            todoViewModel.insert(todo);

        } else if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(Constants.EXTRA_ID_ITEM, -1);

            if (id == -1) {
                Toast.makeText(this, "" + getResources().getString(R.string.todo_no_edited), Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(Constants.EXTRA_NAME_ITEM);
            String description = data.getStringExtra(Constants.EXTRA_DESCRIPTION_ITEM);
            String createDate = data.getStringExtra(Constants.EXTRA_CREATE_DATE_ITEM);
            String deadline = data.getStringExtra(Constants.EXTRA_DEADLINE_DAY_ITEM) + "-" + data.getStringExtra(Constants.EXTRA_DEADLINE_MONTH_ITEM) + "-" + data.getStringExtra(Constants.EXTRA_DEADLINE_YEAR_ITEM);
            Boolean status = data.getBooleanExtra(Constants.EXTRA_STATUS_ITEM, false);

            Todo todo = new Todo(PreferenceSingleton.getInstance().getListId(), PreferenceSingleton.getInstance().getUserId(), title, description, deadline, createDate, status);
            todo.setId(id);
            todoViewModel.update(todo);

            Toast.makeText(this, "" + getResources().getString(R.string.todo_updated), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.todo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_todo_menu:
                todoViewModel.deleteAllTodo();
                Toast.makeText(this, "" + getResources().getString(R.string.todo_all_deleted), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.share_todo_menu:
                ShareTodo();
                return true;

            case R.id.sort_deadline_asc_todo_menu:
                PreferenceSingleton.getInstance().setTodoOrderType("deadline");
                todoViewModel.getAllTodoAsc().observe(this, new Observer<List<Todo>>() {
                    @Override
                    public void onChanged(List<Todo> todos) {
                        todoAdapter.setTodos(todos);
                    }
                });
                Toast.makeText(this, "Todo is " + getResources().getString(R.string.deadline_asc_todo), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sort_deadline_desc_todo_menu:
                PreferenceSingleton.getInstance().setTodoOrderType("deadline");
                todoViewModel.getAllTodoDesc().observe(this, new Observer<List<Todo>>() {
                    @Override
                    public void onChanged(List<Todo> todos) {
                        todoAdapter.setTodos(todos);
                    }
                });
                return true;

            case R.id.sort_create_asc_todo_menu:
                PreferenceSingleton.getInstance().setTodoOrderType("create_date");
                todoViewModel.getAllTodoAsc().observe(this, new Observer<List<Todo>>() {
                    @Override
                    public void onChanged(List<Todo> todos) {
                        todoAdapter.setTodos(todos);
                    }
                });
                return true;
            case R.id.sort_create_desc_todo_menu:
                PreferenceSingleton.getInstance().setTodoOrderType("create_date");
                todoViewModel.getAllTodoDesc().observe(this, new Observer<List<Todo>>() {
                    @Override
                    public void onChanged(List<Todo> todos) {
                        todoAdapter.setTodos(todos);
                    }
                });
                return true;

            case R.id.sort_name_asc_todo_menu:
                PreferenceSingleton.getInstance().setTodoOrderType("name");
                todoViewModel.getAllTodoAsc().observe(this, new Observer<List<Todo>>() {
                    @Override
                    public void onChanged(List<Todo> todos) {
                        todoAdapter.setTodos(todos);
                    }
                });
                return true;
            case R.id.sort_name_desc_todo_menu:
                PreferenceSingleton.getInstance().setTodoOrderType("name");
                todoViewModel.getAllTodoDesc().observe(this, new Observer<List<Todo>>() {
                    @Override
                    public void onChanged(List<Todo> todos) {
                        todoAdapter.setTodos(todos);
                    }
                });
                return true;

            case R.id.sort_status_asc_todo_menu:
                PreferenceSingleton.getInstance().setTodoOrderType("status");
                todoViewModel.getAllTodoAsc().observe(this, new Observer<List<Todo>>() {
                    @Override
                    public void onChanged(List<Todo> todos) {
                        todoAdapter.setTodos(todos);
                    }
                });
                return true;
            case R.id.sort_status_desc_todo_menu:
                PreferenceSingleton.getInstance().setTodoOrderType("status");
                todoViewModel.getAllTodoDesc().observe(this, new Observer<List<Todo>>() {
                    @Override
                    public void onChanged(List<Todo> todos) {
                        todoAdapter.setTodos(todos);
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void ShareTodo() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, ShareStringGenerator() + "");
        intent.setType("text/plain");
        startActivity(intent);

    }

    public String ShareStringGenerator() {
        String result = "";
        if (todosForShare != null) {
            for (int i = 0; i < todosForShare.size(); i++) {
                result += "Todo: " + i
                        + " \nName: " + todosForShare.get(i).getName()
                        + " \nDescription: " + todosForShare.get(i).getDescription()
                        + " \nCreated Date: " + todosForShare.get(i).getCreate_date()
                        + " \nDeadline: " + todosForShare.get(i).getDeadline()
                        + " \nComplated: " + todosForShare.get(i).getStatus()
                        + "\n\n";
            }
        }
        Log.e("TodoShare:", result + "");
        return result;
    }
}
