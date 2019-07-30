package com.enesdokuz.todolist.utils;

import java.io.Serializable;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 28.07.2019
 ***/
public class Constants implements Serializable {

    //for database
    public static final String LIST_DB = "list_database";
    public static final String LIST_NAME_TABLE = "list_name_table";
    public static final String LIST_TODO_TABLE = "list_todo_table";
    public static final int VERSION_DB = 4;

    //for intent.
    public static final String EXTRA_TITLE_LIST = "EXTRA_TITLE_LIST";
    public static final String EXTRA_DESCRIPTION_LIST = "EXTRA_DESCRIPTION_LIST";
    public static final String EXTRA_ID_LIST = "EXTRA_ID_LIST";

    public static final String EXTRA_ID_ITEM = "EXTRA_ID_ITEM";
    public static final String EXTRA_LIST_ID_ITEM = "EXTRA_LIST_ID_ITEM";
    public static final String EXTRA_NAME_ITEM = "EXTRA_NAME_ITEM";
    public static final String EXTRA_DESCRIPTION_ITEM = "EXTRA_DESCRIPTION_ITEM";
    public static final String EXTRA_DEADLINE_DAY_ITEM = "EXTRA_DEADLINE_DAY_ITEM";
    public static final String EXTRA_DEADLINE_MONTH_ITEM = "EXTRA_DEADLINE_MONTH_ITEM";
    public static final String EXTRA_DEADLINE_YEAR_ITEM = "EXTRA_DEADLINE_YEAR_ITEM";
    public static final String EXTRA_STATUS_ITEM = "EXTRA_STATUS_ITEM";
    public static final String EXTRA_CREATE_DATE_ITEM = "EXTRA_CREATE_DATE_ITEM";

    //for url
    private static final String SERVER_URL = "http://api.enesdokuz.com/todo";
    public static final String LOGIN_URL = SERVER_URL + "/login.php";
    public static final String REGISTER_URL = SERVER_URL + "/register.php";

}
