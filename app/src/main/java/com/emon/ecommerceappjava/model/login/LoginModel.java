package com.emon.ecommerceappjava.model.login;

import androidx.annotation.Keep;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "login")
@Keep
public class LoginModel {
    @PrimaryKey (autoGenerate = true)
    public int id;
    public String username,number,email,password;

    public LoginModel(String username, String number, String email, String password) {
        this.username = username;
        this.number = number;
        this.email = email;
        this.password = password;
    }
}
