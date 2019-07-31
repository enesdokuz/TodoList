package com.enesdokuz.todolist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.enesdokuz.todolist.TodoRepository;
import com.enesdokuz.todolist.model.Todo;

import java.util.List;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 2019-07-30
 ***/
public class TodoViewModel extends AndroidViewModel {

    private TodoRepository repository;
    private LiveData<List<Todo>> allTodo, allTodoDesc, allTodoAsc;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new TodoRepository(application);
        allTodo = repository.getAllTodo();
        allTodoAsc = repository.getAllTodoWithOrderAsc();
        allTodoDesc = repository.getAllTodoWithOrderDesc();
    }

    public void insert(Todo todo) {
        repository.insert(todo);
    }

    public void update(Todo todo) {
        repository.update(todo);
    }

    public void delete(Todo todo) {
        repository.delete(todo);
    }

    public void deleteAllTodo() {
        repository.deleteAllTodo();
    }

    public LiveData<List<Todo>> getAllTodo() {
        return allTodo;
    }

    public LiveData<List<Todo>> getAllTodoDesc() {
        return allTodoDesc;
    }

    public LiveData<List<Todo>> getAllTodoAsc() {
        return allTodoAsc;
    }
}
