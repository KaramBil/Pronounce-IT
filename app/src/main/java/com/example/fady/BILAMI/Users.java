package com.example.fady.BILAMI;

/**
 * Created by Labo on 25/05/2018.
 */

public class Users {
    public int id;
    public String Email;
    int invited;
    String sender;
    int gp;
    int gw;
    int avatar;

    public Users(int id , String email , int invited,String sender,int gp , int gw , int avatar) {
        this.id = id;
        this.Email = email;
this.sender=sender;
    this.invited=invited;
    this.gp=gp;
    this.gw=gw;
    this.avatar=avatar;
    }
}
