package com.example.fady.BILAMI;

/**
 * Created by LABS on 24/04/2018.
 */

public class image {
    int id ;
    String nomimage;
    int idcat;
    int idniv;
    Double si;
    String moten;
    String motar;
    Double scorear;
    int etat=0;
    int etatar=0;

    public image(int id , String nomimage , Double si ,  int idcat , int idniv,String moten,String motar,Double scorear){
        this.id=id;
        this.nomimage=nomimage;
        this.si=si;

        this.idcat=idcat;
        this.idniv=idniv;
        this.moten=moten;
        this.motar=motar;
this.scorear=scorear;
    }
    public int getidcat(){
        return idcat;
    }
    public int getidniv(){
        return idniv;
    }

}
