package com.enesdokuz.todolist.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.enesdokuz.todolist.ListsAdapter;
import com.enesdokuz.todolist.utils.PreferenceSingleton;
import com.enesdokuz.todolist.model.ListName;
import com.enesdokuz.todolist.R;
import com.enesdokuz.todolist.utils.Constants;
import com.enesdokuz.todolist.utils.Methods;
import com.enesdokuz.todolist.viewmodel.ListNameViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_LIST_REQUEST = 1;
    public static final int EDIT_LIST_REQUEST = 2;
    private ListNameViewModel listNameViewModel;
    private Methods methods;
    private ListsAdapter listsAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        methods = new Methods(MainActivity.this);
        FloatingActionButton btnAdd = findViewById(R.id.btn_add_list_main);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditListActivity.class);
                startActivityForResult(intent, ADD_LIST_REQUEST);
            }
        });

        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        listsAdapter = new ListsAdapter();
        recyclerView.setAdapter(listsAdapter);

        listNameViewModel = ViewModelProviders.of(this).get(ListNameViewModel.class);
        listNameViewModel.getAllLists().observe(this, new Observer<List<ListName>>() {
            @Override
            public void onChanged(List<ListName> listNames) {

                listsAdapter.setListNames(listNames);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                listNameViewModel.delete(listsAdapter.getListAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "" + getResources().getString(R.string.list_deleted), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        listsAdapter.setOnItemClickListener(new ListsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListName listName) {
                PreferenceSingleton.getInstance().setListId(listName.getId());
                Intent intent = new Intent(MainActivity.this, TodoActivity.class);
                intent.putExtra(Constants.EXTRA_ID_LIST, listName.getId());
                startActivity(intent);
            }
        });

        listsAdapter.setOnLongItemClickListener(new ListsAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(ListName listName) {
                Intent intent = new Intent(MainActivity.this, AddEditListActivity.class);
                intent.putExtra(Constants.EXTRA_TITLE_LIST, listName.getTitle());
                intent.putExtra(Constants.EXTRA_DESCRIPTION_LIST, listName.getDescription());
                intent.putExtra(Constants.EXTRA_ID_LIST, listName.getId());
                startActivityForResult(intent, EDIT_LIST_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_LIST_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(Constants.EXTRA_TITLE_LIST);
            String description = data.getStringExtra(Constants.EXTRA_DESCRIPTION_LIST);

            ListName listName = new ListName(title, description, PreferenceSingleton.getInstance().getUserId());
            listNameViewModel.insert(listName);

            Toast.makeText(this, "" + getResources().getString(R.string.list_generated), Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_LIST_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(Constants.EXTRA_ID_LIST, -1);

            if (id == -1) {
                Toast.makeText(this, "" + getResources().getString(R.string.list_no_edited), Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(Constants.EXTRA_TITLE_LIST);
            String description = data.getStringExtra(Constants.EXTRA_DESCRIPTION_LIST);

            ListName listName = new ListName(title, description, PreferenceSingleton.getInstance().getUserId());
            listName.setId(id);
            listNameViewModel.update(listName);

            Toast.makeText(this, "" + getResources().getString(R.string.list_updated), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_main_menu:
                listNameViewModel.deleteAllList();
                Toast.makeText(this, "" + getResources().getString(R.string.list_all_deleted), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout_main_menu:
                PreferenceSingleton.getInstance().setUserId(-1);
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();
                return true;
            case R.id.sort_main_menu:
                PreferenceSingleton.getInstance().setListNameSortType("title");
                listNameViewModel.getAllListsWithSort().observe(this, new Observer<List<ListName>>() {
                    @Override
                    public void onChanged(List<ListName> listNames) {

                        listsAdapter.setListNames(listNames);
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}