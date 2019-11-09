package com.qyz.malls.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.qyz.malls.AppExecutors;

@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    //singleton class
    private static LocalDatabase sInstance;
    public static final String DATABASE_NAME = "QzyDB";

    public abstract UserDao userDao();

    /**
     * Gets the singleton instance of LocalDatabase.
     *
     * @param context The context.
     * @return The singleton instance of LocalDatabase.
     */
    public static LocalDatabase getInstance(final Context context){
        if(sInstance == null){
            synchronized (LocalDatabase.class){
                if(sInstance == null){
                    sInstance = Room.databaseBuilder(context,LocalDatabase.class,DATABASE_NAME)
                            .build();
                }
            }
        }
        return sInstance;
    }



}
