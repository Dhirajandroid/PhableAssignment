package com.example.phableassignment.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.phableassignment.db.UserDatabaseClass;
import com.example.phableassignment.db.model.UserModel;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {

    private final LiveData<List<UserModel>> userList;
    private final MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();

    private UserDatabaseClass userDatabaseClass;

    public UserListViewModel(Application application) {
        super(application);

        userDatabaseClass = UserDatabaseClass.getDatabase(this.getApplication());

        userList = userDatabaseClass.userDaoInterface().getAllUsers();
    }


    public LiveData<List<UserModel>> getUserList() {
        return userList;
    }

    public void deleteItem(UserModel userModel) {
        new deleteAsyncTask(userDatabaseClass).execute(userModel);
    }

    public void addItem(final UserModel userModel) {
        new addAsyncTask(userDatabaseClass).execute(userModel);
    }

    public void updateItem(final UserModel userModel) {
        new updateAsyncTask(userDatabaseClass).execute(userModel);
    }

    public void selectUser(UserModel userModel) {
        userModelMutableLiveData.setValue(userModel);
    }

    public MutableLiveData<UserModel> getSelectedUser() {
        return userModelMutableLiveData;
    }

    private static class addAsyncTask extends AsyncTask<UserModel, Void, Void> {

        private UserDatabaseClass db;

        addAsyncTask(UserDatabaseClass userDatabaseClass) {
            db = userDatabaseClass;
        }

        @Override
        protected Void doInBackground(final UserModel... params) {
            db.userDaoInterface().addNewUser(params[0]);
            return null;
        }

    }

    private static class deleteAsyncTask extends AsyncTask<UserModel, Void, Void> {

        private UserDatabaseClass db;

        deleteAsyncTask(UserDatabaseClass userDatabaseClass) {
            db = userDatabaseClass;
        }

        @Override
        protected Void doInBackground(final UserModel... params) {
            db.userDaoInterface().deleteUser(params[0]);
            return null;
        }

    }

    private static class updateAsyncTask extends AsyncTask<UserModel, Void, Void> {

        private UserDatabaseClass db;

        updateAsyncTask(UserDatabaseClass userDatabaseClass) {
            db = userDatabaseClass;
        }

        @Override
        protected Void doInBackground(final UserModel... params) {
            db.userDaoInterface().updateUser(params[0]);
            return null;
        }

    }


}
