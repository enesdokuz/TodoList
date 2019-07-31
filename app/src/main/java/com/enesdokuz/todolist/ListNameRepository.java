package com.enesdokuz.todolist;

import android.app.Application;
import android.os.AsyncTask;

import com.enesdokuz.todolist.model.ListName;
import com.enesdokuz.todolist.utils.PreferenceSingleton;

import java.util.List;

import androidx.lifecycle.LiveData;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 27.07.2019
 ***/
public class ListNameRepository {

    private ListNameDao listNameDao;
    private LiveData<List<ListName>> allLists, allListsWithSort;

    public ListNameRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        listNameDao = database.listNameDao();

        allLists = listNameDao.getAllLists(PreferenceSingleton.getInstance().getUserId());
        allListsWithSort = listNameDao.getAllListWithSort(PreferenceSingleton.getInstance().getUserId(), PreferenceSingleton.getInstance().getListNameSortType());

    }

    public void insert(ListName listName) {
        new InsertListNameAsycnTask(listNameDao).execute(listName);

    }

    public void update(ListName listName) {
        new UpdateListNameAsycnTask(listNameDao).execute(listName);

    }

    public void delete(ListName listName) {
        new DeleteListNameAsycnTask(listNameDao).execute(listName);
    }

    public void deleteAllLists() {
        new DeleteAllListNameAsycnTask(listNameDao).execute();
    }

    public LiveData<List<ListName>> getAllLists() {

        return allLists;
    }

    public LiveData<List<ListName>> getAlllistsWithSort() {

        return allListsWithSort;
    }

    private static class InsertListNameAsycnTask extends AsyncTask<ListName, Void, Void> {

        private ListNameDao listNameDao;

        private InsertListNameAsycnTask(ListNameDao listNameDao) {
            this.listNameDao = listNameDao;
        }


        @Override
        protected Void doInBackground(ListName... listNames) {
            listNameDao.insert(listNames[0]);
            return null;
        }
    }

    private static class UpdateListNameAsycnTask extends AsyncTask<ListName, Void, Void> {

        private ListNameDao listNameDao;

        private UpdateListNameAsycnTask(ListNameDao listNameDao) {
            this.listNameDao = listNameDao;
        }


        @Override
        protected Void doInBackground(ListName... listNames) {
            listNameDao.update(listNames[0]);
            return null;
        }
    }

    private static class DeleteListNameAsycnTask extends AsyncTask<ListName, Void, Void> {

        private ListNameDao listNameDao;

        private DeleteListNameAsycnTask(ListNameDao listNameDao) {
            this.listNameDao = listNameDao;
        }


        @Override
        protected Void doInBackground(ListName... listNames) {
            listNameDao.delete(listNames[0]);
            return null;
        }
    }

    private static class DeleteAllListNameAsycnTask extends AsyncTask<ListName, Void, Void> {

        private ListNameDao listNameDao;

        private DeleteAllListNameAsycnTask(ListNameDao listNameDao) {
            this.listNameDao = listNameDao;
        }


        @Override
        protected Void doInBackground(ListName... listNames) {
            listNameDao.deleteAllLists(PreferenceSingleton.getInstance().getUserId());
            return null;
        }
    }
}
