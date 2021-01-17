package com.example.roomnodoappjava.util;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.example.roomnodoappjava.data.NoDoDao;
import com.example.roomnodoappjava.data.NoDoRoomDatabase;
import com.example.roomnodoappjava.model.NoDo;

import java.util.List;

public class NoDoRepository {
    private static NoDoDao noDoDao;
    private LiveData<List<NoDo>> noDoList;

    public NoDoRepository(Application application) {
        //Can also get data from api here
        NoDoRoomDatabase db = NoDoRoomDatabase.getDatabase(application);
        noDoDao = db.noDoDao();
        noDoList = noDoDao.getAllNoDos();
    }

    public  LiveData<List<NoDo>> getAllNoDos(){
        return noDoList;
    }

    public void insert(NoDo noDo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noDoDao.insert(noDo);
            }
        }).start();
    }

    public void update (List<NoDo> noDoList){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noDoDao.update(noDoList);
            }
        }).start();
    }

    public void editNodo (NoDo nodo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noDoDao.updateNodo(nodo);
            }
        }).start();
    }

    public void deleteAll(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noDoDao.deleteAll();
            }
        }).start();
    }

    public void deleteNodo(NoDo nodo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noDoDao.deleteNodo(nodo);
            }
        }).start();
    }

    public void deleteNodo(int position){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noDoDao.deleteNodo(position);
            }
        }).start();
    }


}
