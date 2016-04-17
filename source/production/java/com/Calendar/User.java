package com.Calendar;

public class User {


    private String username;
    private String e_mail;
    private String password;
    private String password2;
    private String first_name;
    private String last_name;
    private int userID;

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getUserID() {
        return userID;
    }

}
