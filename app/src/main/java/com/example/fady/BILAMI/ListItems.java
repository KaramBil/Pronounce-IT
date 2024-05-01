package com.example.fady.BILAMI;

/**
 * Created by Fady on 4/4/2018.
 */

public class ListItems {
    public int id;
    public int img;
    public String name;
int idlvl;
int etatAR;
int etat;


    public ListItems(int id, int img , String name) {
        this.id = id;
        this.img = img;
        this.name = name;

    }
    public ListItems(int id, int img , String name, int etat,int etatAR) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.etatAR=etatAR;
        this.etat=etat;

    }

}
