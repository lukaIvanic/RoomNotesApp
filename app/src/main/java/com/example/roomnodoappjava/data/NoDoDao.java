package com.example.roomnodoappjava.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomnodoappjava.model.NoDo;

import java.util.List;

@Dao
public interface NoDoDao {
    @Insert
    void insert(NoDo noDo);

    @Query("DELETE FROM nodo_table")
    void deleteAll();

    @Query("DELETE FROM nodo_table WHERE id = :id")
    void deleteNodo(int id);

    @Query("SELECT MAX(nodo_order) FROM nodo_table")
    int getLargestOrder();

    @Delete
    void deleteNodo(NoDo nodo);

//    @Query("UPDATE nodo_table SET nodo_col = :nodoText WHERE id = :id")
//    void updateItem(int id, String nodoText);

    @Update
    void updateNodo(NoDo nodo);

    @Update
    int update(List<NoDo>nodoList);

    @Query("SELECT * FROM nodo_table ORDER BY nodo_order ASC")
    LiveData<List<NoDo>> getAllNoDos();
}
