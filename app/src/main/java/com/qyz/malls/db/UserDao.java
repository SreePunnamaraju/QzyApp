package com.qyz.malls.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.qyz.malls.db.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDetails(User user);

    @Query("SELECT phonenumber FROM user")
    String getPhoneNumber();

    @Query("SELECT gcm_token FROM user")
    String getGcmToken();

    @Query("SELECT Count(*) FROM user")
    int getUserCount();

    @Update
    void updateDetails(User user);
}
