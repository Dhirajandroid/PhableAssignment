package com.example.phableassignment.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.phableassignment.db.model.UserModel;
import static com.example.phableassignment.utils.GlobalConstants.DATABASE_VERSION;

@Database(entities = {UserModel.class}, version = DATABASE_VERSION)
public abstract class UserDatabaseClass extends RoomDatabase {

    private static UserDatabaseClass INSTANCE;

    public static synchronized UserDatabaseClass getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), UserDatabaseClass.class, "user_db")
                            .build();
        }
        return INSTANCE;
    }

    public abstract UserDaoInterface userDaoInterface();
}
