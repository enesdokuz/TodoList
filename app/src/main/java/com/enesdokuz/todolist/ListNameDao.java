package com.enesdokuz.todolist;

import com.enesdokuz.todolist.model.ListName;
import com.enesdokuz.todolist.utils.Constants;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 27.07.2019
 ***/
@Dao
public interface ListNameDao {

    @Insert
    void insert(ListName listName);

    @Update
    void update(ListName listName);

    @Delete
    void delete(ListName listName);

    @Query("DELETE FROM list_name_table WHERE owner_id LIKE :ownId")
    void deleteAllLists(int ownId);

    @Query("SELECT * FROM list_name_table WHERE owner_id LIKE :ownId ORDER BY description ASC")
    LiveData<List<ListName>> getAllLists(int ownId);

    @Query("SELECT * FROM list_name_table WHERE owner_id LIKE :ownId ORDER BY :type DESC")
    LiveData<List<ListName>> getAllListWithSort(int ownId,String type);
}
