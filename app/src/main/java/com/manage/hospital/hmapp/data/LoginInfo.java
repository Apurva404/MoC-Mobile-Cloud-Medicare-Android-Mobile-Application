package com.manage.hospital.hmapp.data;

/**
 * Created by divyankithaRaghavaUrs on 4/28/17.
 */

public class LoginInfo
{
    public String Username,Password;
    public int ID = 0;

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
