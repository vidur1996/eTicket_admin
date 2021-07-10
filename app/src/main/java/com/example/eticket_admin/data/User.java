package com.example.eticket_admin.data;

public class User {
    public String name;
    public String username;
    public String email;
    public String password;
    public String num;
    public String bus;

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    // public User(String name,String username,String email,String password,String num){
     //   this.name = name;
    //    this.email = email;
    //    this.num = num;
    //    this.password = password;
     //   this.username = username;
   // }
   public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
