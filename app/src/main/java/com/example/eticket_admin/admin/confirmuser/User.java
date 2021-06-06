package com.example.eticket_admin.admin.confirmuser;

public class User {
    String name;
    String username;
    String email;
    String password;
    String num;

    public User(String name,String username,String email,String password,String num){
        this.name = name;
        this.email = email;
        this.num = num;
        this.password = password;
        this.username = username;
    }

}
