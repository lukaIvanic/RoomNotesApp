package com.example.roomnodoappjava.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.roomnodoappjava.model.NoDo;

@Database(entities = {NoDo.class}, version = 1)
public abstract class NoDoRoomDatabase extends RoomDatabase {

    public static volatile NoDoRoomDatabase INSTANCE;

    public abstract NoDoDao noDoDao();

    public static NoDoRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (NoDoRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NoDoRoomDatabase.class, "nodo_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
                //new PopulateDbTask(INSTANCE);
        }
    };

    private static class PopulateDbTask {

        private final NoDoDao noDoDao;
        public PopulateDbTask(NoDoRoomDatabase db) {
            noDoDao = db.noDoDao();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    noDoDao.insert(new NoDo("Note#" + 0, "Become an android dev \nGet the android job \nDrop out from college", noDoDao.getLargestOrder() + 1));
                    for(int i = 1; i < 100; i++){
                        try {
                            NoDo noDo = new NoDo("Note#" + i, "Become an android dev \nGet the android job \nDrop out from college", noDoDao.getLargestOrder() + 1);
                            noDoDao.insert(noDo);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
