package com.example.fady.BILAMI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_JETON = "JETON";
    private static final String LANGUAGE = "LANG";
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;
    SharedPreferences sharedPreferences,sharedPreferences2,sharedPreferences3;
    int rightvolume,leftvolume;
        public static MediaPlayer mp;
    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
    RemplissageDB();
        sharedPreferences = getBaseContext().getSharedPreferences(PREF_JETON, MODE_PRIVATE);
        sharedPreferences2 = getBaseContext().getSharedPreferences("SOUND", MODE_PRIVATE);
        sharedPreferences3 = getBaseContext().getSharedPreferences(LANGUAGE, MODE_PRIVATE);

            sharedPreferences2
                    .edit()
                    .putInt("SOUNDS",1)
                    .apply();

        if (!sharedPreferences3.contains("LN")) {
            sharedPreferences3
                    .edit()
                    .putString("LN","EN")
                    .apply();
        }



        mp = MediaPlayer.create(getApplicationContext(), R.raw.jungle);
        mp.setLooping(true);
        mp.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sharedPreferences2.getInt("SOUNDS",0)==2){
            mp.setVolume(0,0);
        }
    }

    public void Go(View view) {
        startActivity(new Intent(MainActivity.this,Main2Activity.class));
    }

    public void GoMulti(View view) {
        startActivity(new Intent(MainActivity.this,login.class));
    }

    MyHelper db= new MyHelper(this);
    int imgNiv[] = {
            R.drawable.level1,
            R.drawable.level2,
            R.drawable.level3,
            R.drawable.level4,
            R.drawable.level5,};
    int imgCat[] = {
            R.drawable.historyicone,
            R.drawable.scienceicone,
            R.drawable.sporticone,
            R.drawable.technoicone,
            R.drawable.musicicone,
            R.drawable.animalsicone,
            R.drawable.articone,
            R.drawable.geoicon,
            R.drawable.foodicone,
            R.drawable.shadowicone};
    public void RemplissageDB(){
        String[]names = getResources().getStringArray(R.array.Names);
        int id=0;
        if(db.getAllCategories().size()==0) {
            for (String i : names) {
                Category C = new Category(id, imgCat[id], i, 5, null,null);
                db.createCategory(C);
                id++;
            }
        }

        String[]LVLS = getResources().getStringArray(R.array.Niveau);
        List<Level> listLVL = new ArrayList<>();

        id=0;
        int im=0;
        if (db.getAllLevels().size()==0){
            for ( int C=0 ; C<10 ; C++) {
                for(String i : LVLS) {

                    Level L = new Level (id,i,7,null, C , imgNiv[im],0,null,0);
                    listLVL.add(L);
                    db.createLevel(listLVL.get(id));

                    im++;                id++;
                }
                im=0;}}
        String[]wordsen = getResources().getStringArray(R.array.WORDS);

        String[]wordsar = getResources().getStringArray(R.array.arab);
        String[]IMGS = getResources().getStringArray(R.array.Image);
        List<image> listImage = new ArrayList<>();
        id=0;
        im=0;
        int C=0;
        int L=0;
        if(db.getAllimages().size()==0) {
            while (L < db.getAllLevels().size()) {
                for (String i : IMGS) {

                    image Image = new image(id, i, 0.0, C, L, wordsen[id],wordsar[id],0.0);
                    listImage.add(Image);
                    db.createImage(Image);
                    im++;
                    id++;
                }
                L++;
                if (L % imgNiv.length == 0) {
                    C++;
                }
                im = 0;
            }

        }
    }

    public void GoSetting(View view) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.setting);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        final Button mute = (Button)dialog.findViewById(R.id.muteall);
        int e = sharedPreferences2.getInt("SOUNDS",0);
        if (e==1){
            mute.setText("MUTE SOUNDS");}
        if (e==2){

            mute.setText("UNMUTE SOUNDS"); }

            mute.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int s=sharedPreferences2.getInt("SOUNDS",0);
                        if (s==1) {
                            sharedPreferences2.edit().putInt("SOUNDS", 2).apply();
                            mute.setText("UNMUTE SOUNDS");

                            mp.setVolume(0, 0);
                        }
                        if(s==2) {
                            sharedPreferences2.edit().putInt("SOUNDS", 1).apply();
                            mute.setText("MUTE SOUNDS");
                            mp.setVolume(1, 1);
                        }


                }
            });


        LinearLayout LNG = (LinearLayout)dialog.findViewById(R.id.changerlangue);
        final ImageView LN = (ImageView)dialog.findViewById(R.id.FLAG);
        if (sharedPreferences3.getString("LN","").equals("EN")){
            LN.setImageResource(R.drawable.ukicon);
        }
        else LN.setImageResource(R.drawable.fricone);
        LNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        if (                sharedPreferences3.getString("LN","").equals("EN")){
                    LN.setImageResource(R.drawable.fricone);
                    sharedPreferences3.edit().putString("LN","FR").apply();

                }
        else {
            LN.setImageResource(R.drawable.ukicon);
            sharedPreferences3.edit().putString("LN","EN").apply();
        }

            }
            });
        final Button close = (Button)dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        Button Restore = (Button)dialog.findViewById(R.id.restore);
        Restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.restore();
                RemplissageDB();

                sharedPreferences.edit().clear().apply();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
}
