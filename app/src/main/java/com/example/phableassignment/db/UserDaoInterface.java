package com.example.phableassignment.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.phableassignment.db.model.UserModel;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDaoInterface {

    @Query("select * from UserModel")
    LiveData<List<UserModel>> getAllUsers();

    @Query("select * from UserModel where id = :id")
    UserModel getSelectedUser(String id);

    @Insert(onConflict = REPLACE)
    void addNewUser(UserModel userModel);

    @Delete
    void deleteUser(UserModel userModel);

    @Update
    void updateUser(UserModel userModel);

}
