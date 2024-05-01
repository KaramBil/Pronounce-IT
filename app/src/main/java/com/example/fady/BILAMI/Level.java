package com.example.fady.BILAMI;

/**
 * Created by Fady on 4/13/2018.
 */

public class Level {
    int idLevel;
    String nomLevel;
    int nbrimage;
    Double sn;
    Double snAR=0.0;
    int Cat;
    int icone;
    int etatAR;
    int etat;
    public Level(int idLevel, String nomLevel, int nbrQustion, Double sn , int cat , int icone , int etat, Double snAR,int etatAR)  {
        this.idLevel = idLevel;
        this.nomLevel = nomLevel;
        this.nbrimage = nbrQustion;
        this.sn = sn;
        this.Cat = cat;
        this.icone=icone;
        this.snAR=snAR;
        this.etat=etat;
        this.etatAR=etatAR;
    }

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public String getNomLevel() {
        return nomLevel;
    }

    public void setNomLevel(String nomLevel) {
        this.nomLevel = nomLevel;
    }

    public int getNbrQustion() {
        return nbrimage;
    }

    public void setNbrQustion(int nbrQustion) {
        this.nbrimage = nbrQustion;
    }

    public Double getSn() {
        return sn;
    }

    public void setSn(Double sn) {
        this.sn = sn;
    }
    public int getCat(){ return this.Cat;}

}
