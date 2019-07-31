package com.enesdokuz.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.enesdokuz.todolist.model.Todo;

import java.util.List;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 2019-07-30
 ***/
@Dao
public interface TodoDao {

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("DELETE FROM list_todo_table WHERE owner_id LIKE :owner_id AND list_id LIKE :list_id")
    void deleteAllTodo(int owner_id,int list_id);

    @Query("SELECT * FROM list_todo_table WHERE owner_id LIKE :owner_id AND list_id LIKE :list_id ORDER BY deadline DESC")
    LiveData<List<Todo>> getAllTodo(int owner_id,int list_id);

    @Query("SELECT * FROM list_todo_table WHERE owner_id LIKE :owner_id AND list_id LIKE :list_id ORDER BY :order_type DESC")
    LiveData<List<Todo>> getAllTodoOrderDesc(int owner_id,int list_id,String order_type);

    @Query("SELECT * FROM list_todo_table WHERE owner_id LIKE :owner_id AND list_id LIKE :list_id ORDER BY :order_type ASC")
    LiveData<List<Todo>> getAllTodoOrderAsc(int owner_id,int list_id,String order_type);
}
