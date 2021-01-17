package com.example.roomnodoappjava.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomnodoappjava.util.NoDoRepository;

import java.util.List;

public class NoDoViewModel extends AndroidViewModel {

    private NoDoRepository repository;
    private LiveData<List<NoDo>> noDoList;

    public NoDoViewModel(@NonNull Application application) {
        super(application);
        repository = new NoDoRepository(application);
        noDoList = repository.getAllNoDos();
    }

    public LiveData<List<NoDo>> getNoDoList() {
        return noDoList;
    }

    public void insert(NoDo nodo){
        repository.insert(nodo);
    }

    public void update(List<NoDo> noDoList){
        repository.update(noDoList);
    }

    public int getLargestOrder(){
        return 0;
    }

    public void editNodo(NoDo nodo){
        repository.editNodo(nodo);
    }

    public void deleteNodo(NoDo nodo){
        repository.deleteNodo(nodo);
    }

    public void deleteNodo(int position){
        repository.deleteNodo(position);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
