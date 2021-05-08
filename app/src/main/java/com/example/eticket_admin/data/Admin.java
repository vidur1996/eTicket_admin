package com.example.eticket_admin.data;

public class Admin  {

    String username;
    String name;
    String email;
    String pnum;
    String password;


    public Admin()
    {

    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;

    }
    public String getPassword()
    {
        return password;
    }


    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNum() {
        return pnum;
    }

    public void setNum(String pnum) {
        this.pnum = pnum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

}