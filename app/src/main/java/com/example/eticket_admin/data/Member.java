package com.example.eticket_admin.data;

public class Member {

    String username;
    String name;
    String email;
    String num;
    String password;
    String lock;

    public Member()
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
    public String getLock()
    {
        return lock;
    }
    public void setLock(String lock)
    {
        this.lock=lock;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

}
