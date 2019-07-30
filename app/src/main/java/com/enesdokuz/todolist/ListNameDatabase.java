package com.enesdokuz.todolist;

import android.content.Context;
import android.os.AsyncTask;

import com.enesdokuz.todolist.model.ListName;
import com.enesdokuz.todolist.utils.Constants;
import com.enesdokuz.todolist.utils.PreferenceSingleton;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 27.07.2019
 ***/

@Database(entities = {ListName.class}, version = Constants.VERSION_DB)
public abstract class ListNameDatabase extends RoomDatabase {

    private static ListNameDatabase instance;

    public abstract ListNameDao listNameDao();

    public static synchronized ListNameDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ListNameDatabase.class, Constants.LIST_DB)
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

        private PopulateDbAsycnTask(ListNameDatabase db) {
            listNameDao = db.listNameDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            listNameDao.insert(new ListName("Talimat 1", "Sağa ya da sola kaydırarak listeyi silebilirsiniz", PreferenceSingleton.getInstance().getUserId()));
            listNameDao.insert(new ListName("Talimat 2", "Listeye tıkladığınızda ilgili listeyi düzenlersiniz.", PreferenceSingleton.getInstance().getUserId()));
            listNameDao.insert(new ListName("Talimat 3", "Burası yazılacak.", PreferenceSingleton.getInstance().getUserId()));
            return null;
        }
    }
}
