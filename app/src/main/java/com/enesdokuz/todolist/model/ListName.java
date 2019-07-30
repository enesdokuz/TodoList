package com.enesdokuz.todolist.model;

import com.enesdokuz.todolist.utils.Constants;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 27.07.2019
 ***/
@Entity(tableName = Constants.LIST_NAME_TABLE)
public class ListName {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int owner_id;

    private String title;

    private String description;


    public ListName(String title, String description, int owner_id) {
        this.title = title;
        this.description = description;
        this.owner_id = owner_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getOwner_id() {
        return owner_id;
    }
}
