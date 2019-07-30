package com.enesdokuz.todolist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.enesdokuz.todolist.model.Todo;
import com.enesdokuz.todolist.utils.PreferenceSingleton;

import java.util.List;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 2019-07-30
 ***/
public class TodoRepository {

    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodo, allTodoWithOrderAsc, allTodoWithOrderDesc;

    public TodoRepository(Application application) {
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();
        allTodo = todoDao.getAllTodo(PreferenceSingleton.getInstance().getUserId(), PreferenceSingleton.getInstance().getListId());
        allTodoWithOrderAsc = todoDao.getAllTodoOrderAsc(PreferenceSingleton.getInstance().getUserId(), PreferenceSingleton.getInstance().getListId(), PreferenceSingleton.getInstance().getTodoOrderType());
        allTodoWithOrderDesc = todoDao.getAllTodoOrderDesc(PreferenceSingleton.getInstance().getUserId(), PreferenceSingleton.getInstance().getListId(), PreferenceSingleton.getInstance().getTodoOrderType());
    }

    public void insert(Todo todo) {
        new InsertTodoAsycnTask(todoDao).execute(todo);
    }

    public void update(Todo todo) {
        new UpdateTodoAsycnTask(todoDao).execute(todo);
    }

    public void delete(Todo todo) {
        new DeleteTodoAsycnTask(todoDao).execute(todo);
    }

    public void deleteAllTodo() {
        new DeleteAllTodoAsycnTask(todoDao).execute();
    }

    public LiveData<List<Todo>> getAllTodo() {
        return allTodo;
    }

    public LiveData<List<Todo>> getAllTodoWithOrderAsc() {
        return allTodoWithOrderAsc;
    }

    public LiveData<List<Todo>> getAllTodoWithOrderDesc() {
        return allTodoWithOrderDesc;
    }

    private static class InsertTodoAsycnTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao todoDao;

        private InsertTodoAsycnTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.insert(todos[0]);
            return null;
        }
    }

    private static class UpdateTodoAsycnTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao todoDao;

        private UpdateTodoAsycnTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.update(todos[0]);
            return null;
        }
    }

    private static class DeleteTodoAsycnTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao todoDao;

        private DeleteTodoAsycnTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.delete(todos[0]);
            return null;
        }
    }

    private static class DeleteAllTodoAsycnTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao todoDao;

        private DeleteAllTodoAsycnTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.deleteAllTodo(PreferenceSingleton.getInstance().getUserId(), PreferenceSingleton.getInstance().getListId());
            return null;
        }
    }
}
