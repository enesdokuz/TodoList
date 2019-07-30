package com.enesdokuz.todolist;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.enesdokuz.todolist.model.Todo;
import com.enesdokuz.todolist.utils.Constants;
import com.enesdokuz.todolist.utils.PreferenceSingleton;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 2019-07-30
 ***/
@Database(entities = {Todo.class}, version = Constants.VERSION_DB)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase instance;

    public abstract TodoDao todoDao();

    public static synchronized TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TodoDatabase.class, Constants.LIST_DB)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsycnTask(instance).execute();
        }
    };

    private static class PopulateDbAsycnTask extends AsyncTask<Void, Void, Void> {
        private TodoDao todoDao;

        private PopulateDbAsycnTask(TodoDatabase db) {
            todoDao = db.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.insert(new Todo(PreferenceSingleton.getInstance().getListId(), PreferenceSingleton.getInstance().getUserId(), "Önemli bir todo", "Önemli Todo'nun açıklamasıdır.", "20.09.2019"
                    , "20.09.2019", false));
            return null;
        }
    }

}
