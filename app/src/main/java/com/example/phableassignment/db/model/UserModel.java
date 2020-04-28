package com.example.phableassignment.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String name;
    private String emailId;

    public UserModel(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
