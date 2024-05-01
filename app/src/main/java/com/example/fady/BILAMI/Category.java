package com.example.fady.BILAMI;

/**
 * Created by Fady on 4/13/2018.
 */

public class Category {
    int idCat;
    String nomCat;
    int nbrNiv;
    Double sc;
    int icone;
    Double scAR;

    public Category(int idCat, int icone ,String nomCat, int nbrNiv, Double sc,Double scAR) {
        this.idCat = idCat;
        this.nomCat = nomCat;
        this.nbrNiv = nbrNiv;
        this.sc = sc;
        this.icone = icone;
        this.scAR=scAR;
    }

    public int getIdCat() {
        return idCat;
    }

    public String getNomCat() {
        return nomCat;
    }

    public int getNbrNiv() {
        return nbrNiv;
    }

    public Double getSc() {
        return sc;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    public void setNbrNiv(int nbrNiv) {
        this.nbrNiv = nbrNiv;
    }

    public void setSc(Double sc) {
        this.sc = sc;
    }

    public int getIcone() {
        return this.icone;
    }

}
