package com.enesdokuz.todolist;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.enesdokuz.todolist.ListNameDao;
import com.enesdokuz.todolist.TodoDao;
import com.enesdokuz.todolist.model.ListName;
import com.enesdokuz.todolist.model.Todo;
import com.enesdokuz.todolist.utils.Constants;
import com.enesdokuz.todolist.utils.PreferenceSingleton;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 2019-07-31
 ***/
@Database(entities = {ListName.class, Todo.class},version = Constants.VERSION_DB)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ListNameDao listNameDao();
    public  abstract TodoDao todoDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.LIST_DB)
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
        private ListNameDao listNameDao;
        private TodoDao todoDao;

        private PopulateDbAsycnTask(AppDatabase db) {
            listNameDao = db.listNameDao();
            todoDao = db.todoDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            listNameDao.insert(new ListName("Talimat 1", "Sağa ya da sola kaydırarak listeyi silebilirsiniz", PreferenceSingleton.getInstance().getUserId()));
            listNameDao.insert(new ListName("Talimat 2", "Listeye tıkladığınızda ilgili listeyi düzenlersiniz.", PreferenceSingleton.getInstance().getUserId()));
            return null;
        }
    }
}
