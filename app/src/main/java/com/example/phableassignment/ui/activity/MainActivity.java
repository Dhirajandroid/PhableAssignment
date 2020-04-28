package com.example.phableassignment.ui.activity;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.phableassignment.R;
import com.example.phableassignment.ui.fragment.UserListFragment;

public class MainActivity extends AppCompatActivity  {

    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setSubtitle("User List");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserListFragment userListFragment = new UserListFragment();
        fragmentTransaction.add(R.id.fragmentHolder, userListFragment, "userList");
        fragmentTransaction.commit();

    }

    }
