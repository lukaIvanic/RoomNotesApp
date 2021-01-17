package com.example.roomnodoappjava.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

@Entity(tableName = "nodo_table")
public class NoDo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "nodo_order")
    private Integer order;

    @NonNull
    @ColumnInfo(name = "nodo_title")
    private String noDoTitle;

    @NonNull
    @ColumnInfo(name = "nodo_desc")
    private String noDoDesc;

    public NoDo(){

    }

    public NoDo(@NonNull String noDoTitle, @NonNull String noDoDesc, @NonNull Integer order) {
        this.noDoTitle = noDoTitle;
        this.noDoDesc = noDoDesc;
        this.order = order;
    }

    @NotNull
    public String getNoDoTitle() {
        return noDoTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNoDoTitle(@NonNull String noDoTitle) {
        this.noDoTitle = noDoTitle;
    }

    @NonNull
    public String getNoDoDesc() {
        return noDoDesc;
    }

    public void setNoDoDesc(@NonNull String noDoDesc) {
        this.noDoDesc = noDoDesc;
    }

    @NonNull
    public Integer getOrder() {
        return order;
    }

    public void setOrder(@NonNull Integer order) {
        this.order = order;
    }
}
