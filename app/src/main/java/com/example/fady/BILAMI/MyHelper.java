package com.example.fady.BILAMI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fady on 4/13/2018.
 */

public class MyHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database.db";
    public static final int VERSION = 1;
    public MyHelper (Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    public void onCreate (SQLiteDatabase db){
        db.execSQL("CREATE TABLE category(id INTEGER PRIMARY KEY , " +
                "icone INTEGER," +
                "nomc TEXT," +
                "nbrn INTEGER," +
                "scorec DOUBLE," +
                "scorecAR DOUBLE)");
        db.execSQL("CREATE TABLE level(id INTEGER PRIMARY KEY , " +
                "nomlvl TEXT," +
                "nbrQST INTEGER," +
                "sn DOUBLE," +
                "Cat INTEGER," +
                "icone INTEGER," + "etat INTEGER," + "snAR DOUBLE,"+"etatAR INTEGER)");
        db.execSQL("CREATE TABLE image(id INTEGER PRIMARY KEY , " +
                "nomimage TEXT," +
                "si DOUBLE," +
                "icone int,"  +
                "Cat INTEGER," +
                "lvl INTEGER," +
                "aprononcer TEXT,"
                + "etat INTEGER," +  "arab TEXT," +  "scoreAR DOUBLE," + "etatAR INTEGER)");
//            db.execSQL("CREATE TABLE pics(idpic INTEGER PRIMARY KEY , " +
//                "picture Pic)");
    }

    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS level");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS image");
        onCreate(db);
    }


    /////////////// CATEGORIES /////////////////
    public void createCategory(Category c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", c.idCat);
        values.put("nomc", c.nomCat);
        values.put("nbrn", c.nbrNiv);
        values.put("scorec", c.sc);
        values.put("icone", c.icone);
        values.put("scorecAR",c.scAR);

        db.insert("category", null, values);
    }
    public void setsc(int icone , float sc ){
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE category SET scorec = " + sc +" where icone = "+icone   ;

        db.execSQL(strSQL);
    }
    public void updatesc( int categ , String lng ){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL=null;
        if(lng.equals("EN")){
      strSQL = "Select sum(sn) from level where Cat="+categ    ;}
        if(lng.equals("FR")){
            strSQL = "Select sum(snAR) from level where Cat="+categ    ;}
        Cursor cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        float i =(float)cursor.getDouble(0);
        db = this.getWritableDatabase();
        String strsSQL=null;
        if(lng.equals("EN")){
            strsSQL ="UPDATE category SET scorec = " + i +" where id="+categ ;}
        if(lng.equals("FR")){
            strsSQL = "UPDATE category SET scorecAR = " + i +" where id="+categ ;    ;}

        db.execSQL(strsSQL);
    }
    public List<Category> getAllCategories(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM category";
        List<Category> categories = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            categories.add(new Category(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),
                    cursor.getInt(3),cursor.getDouble(4),cursor.getDouble(5)));
            cursor.moveToNext();
        }
        return categories;
    }
    public float getsc(int cat,String lng){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL=null;
        if (lng.equals("EN")){
        strSQL = "Select sum(sn) from level where Cat="+cat    ;}
        if (lng.equals("FR")){
            strSQL = "Select sum(snAR) from level where Cat="+cat    ;}
        Cursor cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        return (float)cursor.getDouble(0);}

    /////////////////////////// LEVELS //////////////////////////


    public void createLevel(Level level){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", level.idLevel);
        values.put("nomlvl", level.nomLevel);
        values.put("nbrQST", level.nbrimage);
        values.put("sn", level.sn);
        values.put("Cat", level.Cat);
        values.put("icone",level.icone);
        values.put("etat",level.etat);
        values.put("snAR",level.snAR);
        db.insert("level", null, values);
    }
    public List<Level> getAllLevels(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM level";
        List<Level> levels = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            levels.add(new Level(cursor.getInt(0),cursor.getString(1),
                    cursor.getInt(2),cursor.getDouble(3),cursor.getInt(4), cursor.getInt(5),cursor.getInt(6),cursor.getDouble(7),cursor.getInt(8)));
            cursor.moveToNext();
        }
        return levels;
    }
    public void updatesn( int categ, int lvl ,String lng){

        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL=null;
        if (lng.equals("EN")){
        strSQL = "Select sum(si) from image where Cat="+categ+ " and lvl="+lvl    ;}
        if (lng.equals("FR")){
            strSQL = "Select sum(scoreAR) from image where Cat="+categ+ " and lvl="+lvl    ;}

        Cursor cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        float i =(float)cursor.getDouble(0);
        db = this.getWritableDatabase();
        String strsSQL=null;
        if (lng.equals("EN")){
            strsSQL = "UPDATE level SET sn = " + i +" where Cat="+categ +" and id ="+lvl   ;}
        if (lng.equals("FR")){
            strsSQL =  "UPDATE level SET snAR = " + i +" where Cat="+categ +" and id ="+lvl   ;}



        db.execSQL(strsSQL);
    }
    public float getsn(int cat,int lvl,String lng){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL=null;
        if(lng.equals("EN")){
         strSQL = "Select sum(si) from image where Cat="+cat + " and lvl="+lvl   ;}
        if(lng.equals("FR")){
            strSQL = "Select sum(scoreAR) from image where Cat="+cat + " and lvl="+lvl   ;}
        Cursor cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        return (float)cursor.getDouble(0);}
    public int SizeOfCategorieOfLVL(int id_cat){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT COUNT(id) FROM level where Cat="+id_cat;
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        int i =(int)cursor.getDouble(0);
        return i;
    }
    public void unlocklvl(int id_lvl,String lng){
        SQLiteDatabase db = this.getWritableDatabase();
        String strsSQL=null;
        if (lng.equals("EN")){
        strsSQL= "UPDATE level SET etat = 1 where id="+id_lvl;}
        if (lng.equals("FR")){
            strsSQL= "UPDATE level SET etatAR = 1 where id="+id_lvl;}
        db.execSQL(strsSQL);
    }

    /////////////////////////

    public void restore(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,db.getVersion(),db.getVersion()+1);
    }
    public void createImage(image image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", image.id);
        values.put("nomimage", image.nomimage);
        values.put("si", image.si);
        values.put("Cat", image.idcat);
        values.put("lvl",image.idniv);
        values.put("aprononcer",image.moten);
        values.put("etat",0);
        values.put("arab",image.motar);
        values.put("etatAR",image.etatar);
        db.insert("image", null, values);
    }
    public List<image> getAllimages(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM image";
        List<image> imagess = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            imagess.add(new image(cursor.getInt(0),cursor.getString(1),
                    cursor.getDouble(2),cursor.getInt(3), cursor.getInt(4), cursor.getString(5),cursor.getString(7),cursor.getDouble(8)));
            cursor.moveToNext();
        }
        return imagess;
    }
    public List<String> MotAprononcercat(int id_cat,String lng){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery=null;
        if (lng.equals("EN")){
      selectQuery = "SELECT aprononcer FROM image where Cat="+id_cat;}
        if (lng.equals("FR")){
            selectQuery = "SELECT arab FROM image where Cat="+id_cat;}
        List<String> Array = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Array.add(cursor.getString(0));
            cursor.moveToNext();
        }
        return Array;
    }
    public List<String> MotAprononcer(int id_lvl,String lng){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery=null;
        if (lng.equals("EN")){
           selectQuery = "SELECT aprononcer FROM image where lvl="+id_lvl;}
        if (lng.equals("FR")){
            selectQuery = "SELECT arab FROM image where lvl="+id_lvl;}

        List<String> Array = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Array.add(cursor.getString(0));
            cursor.moveToNext();
        }
    return Array;
    }
    public int SizeOfLevelOfImage(int id_lvl){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT COUNT(id) FROM image where lvl="+id_lvl;
        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        int i =(int)cursor.getDouble(0);
    return i;
    }
    public int ImageEtat(int id_img,String lng){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery=null;
        if (lng.equals("EN")){
            selectQuery = "SELECT etat FROM image where id="+id_img;}
        if (lng.equals("FR")){
            selectQuery = "SELECT etatAR FROM image where id="+id_img;}

        Cursor cursor = db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        int i =(int)cursor.getInt(0);
        return i;
    }
    public void ImageUnlock(int idct , int idlv , int idimage , String lng ){
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL=null;
        if (lng.equals("EN")){
            strSQL = "UPDATE image SET etat =1 where lvl = "+idlv +" and Cat="+idct +" and id=" + idimage  ;}
        if (lng.equals("FR")){
          strSQL=  "UPDATE image SET etatAR =1 where lvl = "+idlv +" and Cat="+idct +" and id=" + idimage  ;}


        db.execSQL(strSQL);
    }
    public void setimageScore(int idct , int idlv , int idimage , double sc , String lng){
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL=null;
        if (lng.equals("EN")){
            strSQL = "UPDATE image SET si = " + sc +" where lvl = "+idlv +" and Cat="+idct +" and id=" + idimage  ;}
        if (lng.equals("FR")){
            strSQL = "UPDATE image SET scoreAR = " + sc +" where lvl = "+idlv +" and Cat="+idct +" and id=" + idimage  ;}


        db.execSQL(strSQL);
    }
    public float getsi(int cat,int lvl,int idimg,String lng){
        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL=null;
        if (lng.equals("EN")){
            strSQL = "Select si from image where Cat="+cat + " and lvl="+lvl+" and id="+idimg   ;}
        if (lng.equals("FR")){
            strSQL = "Select scoreAR from image where Cat="+cat + " and lvl="+lvl+" and id="+idimg   ;}

        Cursor cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        return (float) cursor.getDouble(0);

    }


//////

    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }
}








    /*
    public void createPic(Pic c){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idpic", c.idpic);
        values.put("picture", c.nomCat);
        values.put("nbrn", c.nbrNiv);
        values.put("scorec", c.sc);
        values.put("icone", c.icone);

        db.insert("category", null, values);
    }
*/
