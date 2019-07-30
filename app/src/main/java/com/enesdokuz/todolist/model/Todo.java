package com.enesdokuz.todolist.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.enesdokuz.todolist.utils.Constants;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 2019-07-30
 ***/
@Entity(tableName = Constants.LIST_TODO_TABLE)
public class Todo {

    @PrimaryKey (autoGenerate = true)
    private int id;

    private int list_id;

    private int owner_id;

    private String name;

    private String description;

    private String deadline;

    private String create_date;

    private Boolean status;

    public Todo(int list_id, int owner_id, String name, String description, String deadline, String create_date, Boolean status) {
        this.list_id = list_id;
        this.owner_id = owner_id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.create_date = create_date;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getList_id() {
        return list_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getCreate_date() {
        return create_date;
    }

    public Boolean getStatus() {
        return status;
    }
}
