package com.enesdokuz.todolist.viewmodel;

import android.app.Application;

import com.enesdokuz.todolist.model.ListName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/***
 * TodoList
 * Enes Dokuz enesdokuz
 * www.enesdokuz.com
 * 27.07.2019
 ***/
public class ListNameViewModel extends AndroidViewModel {
    private exListNameRepository repository;
    private LiveData<List<ListName>> allLists,allListsWithSort;

    public ListNameViewModel(@NonNull Application application) {
        super(application);
        repository  = new exListNameRepository(application);
        allLists = repository.getAllLists();
        allListsWithSort = repository.getAlllistsWithSort();
    }

    public void insert (ListName listName){
        repository.insert(listName);
    }

    public void update (ListName listName){
        repository.update(listName);
    }

    public void delete(ListName listName){
        repository.delete(listName);
    }

    public void deleteAllList(){
        repository.deleteAllLists();
    }

    public LiveData<List<ListName>> getAllLists(){
        return allLists;
    }

    public LiveData<List<ListName>> getAllListsWithSort(){
        return allListsWithSort;
    }


}
