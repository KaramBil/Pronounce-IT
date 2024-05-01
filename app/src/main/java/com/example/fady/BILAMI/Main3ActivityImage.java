package com.example.fady.BILAMI;
/* ====================================================================
 * Copyright (c) 2014 Alpha Cephei Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY ALPHA CEPHEI INC. ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL CARNEGIE MELLON UNIVERSITY
 * NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 */



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;
//  import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import static android.widget.Toast.makeText;


public class Main3ActivityImage extends Activity implements RecognitionListener {
    private static final String PREF_JETON = "JETON";
    private SharedPreferences sharedPreferences,sharedPreferences2,sharedPreferences3;
    int RUN= 0;
    MediaPlayer mplayer;
    String grm;


    RatingBar ratingBar ;
    int SUI=0;
    int SUIDEV=0;
    int PRC=0;
int a,b,c,d,e,f,g;
    int score ;
    String Url="http://192.168.1.50/karam/";
    TextView test ;
    MyHelper db= new MyHelper(this);
    int id_cat ; int  id_lvl , id_img;
    String mode;
    static Button btnStart ;
    String cat;
    ArrayList<Bitmap> quiz = new ArrayList() ;
    String MonEtat;
    String myemail;
    RatingBar ratingBarbest;

    public void onBackPressed() {
        super.onBackPressed();
        if(mode.equals("multi")) {
            handler.removeCallbacksAndMessages(null);
            FinishGame();
        }


    }

    public int getIdGame() {
        return IdGame;
    }

    public void setIdGame(int idGame) {


        IdGame = idGame;
    }

    int IdGame;

    public String getMonResultat() {
        return MonResultat;
    }

    public void setMonResultat(String monResultat) {
        MonResultat = monResultat;
    }

    String MonResultat="faregh";


    ///////////// RING
//    RingProgressBar ringProgressBar1;
//    int progress=0;
//    Handler myHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if(msg.what == 0)
//            {
//                if(progress<100)
//                {
//                    progress++;
//                    ringProgressBar1.setProgress(progress);
//                }
//            }
//        }
//    };
    /////////////RING

    /* Named searches allow to quickly reconfigure the decoder */
    private static final String KWS_SEARCH = "wakeup";
    private static final String FORECAST_SEARCH = "forecast";
    private static final String DIGITS_SEARCH = "digits";
    private static final String PHONE_SEARCH = "phones";
    private static final String MENU_SEARCH = "menu";
    public TextToSpeech textToSpeech;



    public String getMysender() {
        return mysender;
    }

    public void setMysender(String mysender) {
        this.mysender = mysender;
    }

    String mysender;

    public Float[] OnlineScoreT;
    /* Keyword we are looking for puto activate menu */
    private static final String KEYPHRASE = "oh mighty computer";
    int PRCDEV=0;


    public String compteur (int s){
        return "image_"+s;
    }
    public String getdrawable (String s){
        return "R.drawable."+s;
    }
    /* Used to handle permission request */
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    //    public void remplissage() {
//
//        ArrayList<Drawable> quiz = new ArrayList() ;
//
//        int i=1;
//        ImageView Im1 = (ImageView) findViewById(R.id.IV);
//        TextView t = (TextView) findViewById(R.id.result_text);
//        while (i<36) {
//            try {
//
//                // get input stream'
//                InputStream ims = getAssets().open("Histoire/image (" + i + ").jpg");
//                // load image as Drawable
//                Drawable d = Drawable.createFromStream(ims, null);
//                // set image to ImageView
//                quiz.add(d);
//
//
//                i++;
//            } catch (IOException ex) {
//                return;
//            }
//        }
//        t.setText(cat+"  " + quiz.size());
//
//        Im1.setImageDrawable(quiz.get(id_img));
//    }
    private void listFiles(String dirFrom) throws IOException {
        Resources res = getResources(); //if you are in an activity
        AssetManager am = res.getAssets();
        String fileList[] = am.list(dirFrom);

        if (fileList != null)
        {
            for ( int i = 0;i<fileList.length;i++)
            {
                Log.d("",fileList[i]);
            }
        }
    }
    public void remplissageonline(String cat) {
        int i=0;
        ImageView Im1 = (ImageView) findViewById(R.id.IV);
        int[] cursor=new int[]{a+1,b+1,c+1,d+1,e+1,f+1,g+1};
        while (i<cursor.length) {
            try {
                InputStream ims = getAssets().open(cat+"/image (" + cursor[i] + ").jpg");
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                Bitmap e=drawableToBitmap(d);
                Bitmap f = Round(e);
                quiz.add(f);


                i++;
            } catch (IOException ex) {
                return;
            }
        }

       Im1.setImageBitmap(quiz.get(0));

    }


    public void remplissage(String cat) {
        int i=1;
        ImageView Im1 = (ImageView) findViewById(R.id.IV);
        while (i<8) {
            try {
                InputStream ims = getAssets().open(cat+"/image (" + (i+(7*(id_lvl%5)))+").jpg");
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                Bitmap e=drawableToBitmap(d);
                Bitmap f = Round(e);
                quiz.add(f);


                i++;
            } catch (IOException ex) {
                return;
            }
            Im1.setImageBitmap(quiz.get(0));

        }

//        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
//                quiz.get(imgafficher));
//        Bitmap mbitmap = ((BitmapDrawable) getResources().getDrawable(quiz.get(imgafficher))).getBitmap();
//        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
//        Canvas canvas = new Canvas(imageRounded);
//        Paint mpaint = new Paint();
//        mpaint.setAntiAlias(true);
//        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
//        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint);// Round Image Corner 100 100 100 100


                 Im1.setImageBitmap(quiz.get(imgafficher));





    //    Im1.setImageDrawable(quiz.get(imgafficher));

    }
        public Bitmap Round ( Bitmap mbitmap){
            Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
            Canvas canvas = new Canvas(imageRounded);
            Paint mpaint = new Paint();
            mpaint.setAntiAlias(true);
            mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP , Shader.TileMode.CLAMP ));
            canvas.drawRoundRect((new RectF(0, 0,  mbitmap.getWidth() ,mbitmap.getWidth())), 20.0f, 20.0f, mpaint);// Round Image Corner 100 100 100 100
            return imageRounded;
        }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    List<String> Mot2;
    List<String> Mot3;
    MediaPlayer mp;

    int imgafficher;
    List<String> MOT;
    private SpeechRecognizer recognizer;
    private HashMap<String, Integer> captions;
    int ender=0;
    Handler handler = new Handler();
int[] OnlineImageT;
String ptm,dict,gram;
String LANGUE;
    @Override
    public void onCreate(Bundle state) {

        super.onCreate(state);
        setContentView(R.layout.activity_main3_image);
        sharedPreferences = getBaseContext().getSharedPreferences(PREF_JETON, MODE_PRIVATE);
        sharedPreferences2 = getBaseContext().getSharedPreferences("SOUND", MODE_PRIVATE);
        sharedPreferences3 = getBaseContext().getSharedPreferences("LANG", MODE_PRIVATE);
            LANGUE=sharedPreferences3.getString("LN","");
        mp = MediaPlayer.create(getApplicationContext(), R.raw.jungle);
        mp.setLooping(true);
        mp.start();
        if(sharedPreferences2.getInt("SOUNDS",0)==2){
            mp.setVolume(0,0);
        }

        ////////// apres SUPPRESSION DU LAYOUT IMG
        final TextView prononcer=(TextView)findViewById(R.id.aprononcer);

        Button jetonbutton=(Button) findViewById(R.id.jetonimg);
        Button Pause = (Button)findViewById(R.id.Pause);
        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Main3ActivityImage.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.pause);
                dialog.setCancelable(false);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                final ImageView  mute = (ImageView)dialog.findViewById(R.id.PauseMute);
                int e = sharedPreferences2.getInt("SOUNDS",0);
                if (e==1){
                    mute.setImageResource(R.drawable.unmute);}
                if (e==2){

                    mute.setImageResource(R.drawable.mute); }

                mute.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        int s=sharedPreferences2.getInt("SOUNDS",0);
                        if (s==1) {
                            sharedPreferences2.edit().putInt("SOUNDS", 2).apply();
                            mute.setImageResource(R.drawable.mute);

                            mp.setVolume(0, 0);
                        }
                        if(s==2) {
                            sharedPreferences2.edit().putInt("SOUNDS", 1).apply();
                            mute.setImageResource(R.drawable.unmute);
                            mp.setVolume(1, 1);
                        }


                    }
                });



                ImageView Home =(ImageView) dialog.findViewById(R.id.PauseGoHome);
                Home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Main3ActivityImage.this,MainActivity.class);
                        if(mode.equals("multi")){
                            Lobby e = new Lobby();
                            e.SetOffline(myemail);
                        }
        startActivity(intent);

                    }
                });
                final Button close = (Button)dialog.findViewById(R.id.Pauseclose);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                dialog.getWindow().setAttributes(lp);

            }

        });

jetonbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        speakClick(prononcer.getText().toString());
    }
});
TextView jetonnbr = (TextView)findViewById(R.id.jetonnbr);

        jetonnbr.setText("" + sharedPreferences.getInt("JETON", 0));
        View backgroundImage = findViewById(R.id.imgbtns);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(100);

//        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                textToSpeech.setLanguage(Locale.ENGLISH);
//
//            }
//        });
//        textToSpeech.speak("", TextToSpeech.QUEUE_FLUSH, null);


        //PoPULATION//
        ratingBarbest = (RatingBar) findViewById(R.id.bestscore);

//        String[]words = getResources().getStringArray(R.array.WORDS);
//        String[]names = getResources().getStringArray(R.array.Image);
//        List<image> listImage = new ArrayList<>();
//        int id=0;
//        int im=0;
//        int C=0;
//        int L=0;
//        if(db.getAllimages().size()==0) {
//            while (L < db.getAllLevels().size()) {
//                for (String i : names) {
//
//                    image Image = new image(id, i, null, C, L, words[id]);
//                    listImage.add(Image);
//                    db.createImage(Image);
//                    im++;
//                    id++;
//                }
//                L++;
//                if (L % db.getAllCategories().size() == 0) {
//                    C++;
//                }
//                im = 0;
//            }
//
//        }
        ///////////////////////


        btnStart = (Button) findViewById(R.id.start);
        btnStart.setEnabled(false);

        Bundle bundle = getIntent().getExtras();
        Button bouton = (Button) findViewById(R.id.start);
        bouton.setVisibility(View.VISIBLE);
        ////////////////////////// RING PROGRESS BARRR ///////////

        //ringProgressBar1 = (RingProgressBar)findViewById(R.id.ringbar);
//        ringProgressBar1.setOnProgressListener(new RingProgressBar.OnProgressListener() {
//            @Override
//            public void progressToComplete() {
//                ringProgressBar1.setVisibility(View.INVISIBLE);
//
//            }
//        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=0;i<100;i++)
//                {
//                    try {
//                        Thread.sleep(20);
//                        myHandler.sendEmptyMessage(0);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();


        ////////////////  RING PROGRESS FINISHED
        // Prepare the data for UI
        captions = new HashMap<>();
        captions.put(KWS_SEARCH, R.string.kws_caption);
        captions.put(MENU_SEARCH, R.string.menu_caption);
        captions.put(DIGITS_SEARCH, R.string.digits_caption);
        captions.put(PHONE_SEARCH, R.string.phone_caption);
        captions.put(FORECAST_SEARCH, R.string.forecast_caption);
        id_cat=bundle.getInt("idcat");
        if (sharedPreferences3.getString("LN","").equals("EN")){
            ptm="en-us-ptm";
            dict="cmudict-en-us.dict";
            switch (id_cat) {
                case 0:   { grm = "history.gram"; break; }
                case 1:   { grm="science.gram"; break; }
                case 2:    {grm="sport.gram"; break; }
                case 3:     {grm="technology.gram"; break; }
                case 4:     {grm= "music.gram"; break; }
                case 5:     {grm= "animals.gram"; break; }
                case 6:     {grm= "art.gram"; break; }
                case 7:     {grm= "geography.gram"; break; }
                case 8:     {grm= "food.gram"; break; }
                case 9:     {grm= "shadow.gram"; break; }

            }

        }
        if (sharedPreferences3.getString("LN","").equals("FR")){
            ptm="ara";
            dict="ara.dic";
            switch (id_cat) {
                case 0:   { grm = "historyar.gram"; break; }
                case 1:   { grm="sciencear.gram"; break; }
                case 2:    {grm="sportar.gram"; break; }
                case 3:     {grm="technologyar.gram"; break; }
                case 4:     {grm= "musicar.gram"; break; }
                case 5:     {grm= "animalsar.gram"; break; }
                case 6:     {grm= "artar.gram"; break; }
                case 7:     {grm= "geographyar.gram"; break; }
                case 8:     {grm= "foodar.gram"; break; }
                case 9:     {grm= "shadowar.gram"; break; }

            }

        }
        Mot2=db.MotAprononcercat(id_cat,LANGUE);
        switch (id_cat) {
            case 0:   { cat = "Histoire"; break; }
            case 1:   { cat="Science"; break; }
            case 2:    {cat="Sport"; break; }
            case 3:     {cat="tech"; break; }
            case 4:     {cat= "Music"; break; }
            case 5:     {cat= "Animals"; break; }
            case 6:     {cat= "Art"; break; }
            case 7:     {cat= "Geography"; break; }
            case 8:     {cat= "Food"; break; }
            case 9:     {cat= "shadow"; break; }

        }
        Button suivant = (Button)findViewById(R.id.suivant);

        id_lvl=bundle.getInt("idlvl");
        mode=bundle.getString("mode");
        MonEtat=bundle.getString("etat");
        if(mode.equals("multi")){
            jetonbutton.setVisibility(View.INVISIBLE);
            jetonnbr.setVisibility(View.INVISIBLE);
            a=bundle.getInt("a");
            b=bundle.getInt("b");
            c=bundle.getInt("c");
            d=bundle.getInt("d");
            e=bundle.getInt("e");
            f=bundle.getInt("f");
            g=bundle.getInt("g");
            myemail=bundle.getString("myemail");

            OnlineImageT= new int[]{a,b,c,d,e,f,g};
            if (MonEtat.equals("receiver")) {
                setMysender(bundle.getString("sender"));
                setIdGame(bundle.getInt("id"));
                Toast.makeText(this, "idgame="+getIdGame(), Toast.LENGTH_SHORT).show();
            }
            if (MonEtat.equals("sender")) {
                setIdGame(bundle.getInt("id"));
            }
        }


        imgafficher=0;


TextView Apr=(TextView)findViewById(R.id.aprononcer1);
        if (mode.equals("solo")){

            if(id_lvl%5>3){
//                prononcer.setVisibility(View.INVISIBLE);
               prononcer.setText("Guess what is in the picture ?!!");
            }
            MOT=db.MotAprononcer(id_lvl,LANGUE);
            remplissage(cat);
            prononcer.setText(MOT.get(imgafficher));}
        if(mode.equals("multi")){
            OnlineScoreT = new Float[]{Float.valueOf(0),Float.valueOf(0),Float.valueOf(0),Float.valueOf(1),Float.valueOf(1),Float.valueOf(1),Float.valueOf(1)};

            suivant.setClickable(false);
          Mot3=new ArrayList<>();
            Mot3.add(Mot2.get(a));
            Mot3.add(Mot2.get(b));
            Mot3.add(Mot2.get(c));
            Mot3.add(Mot2.get(d));
            Mot3.add(Mot2.get(e));
            Mot3.add(Mot2.get(f));
            Mot3.add(Mot2.get(g));
            prononcer.setText(Mot3.get(imgafficher));

            remplissageonline(cat);

            endgame();
        }


        ((TextView) findViewById(R.id.aprononcer1)).setText("You Said :");






        ((TextView) findViewById(R.id.caption_text))
                .setText("Preparing the recognizer");

        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new SetupTask(this).execute();


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        if (mode.equals("solo")){

                if (db.getsi(id_cat,id_lvl,imgafficher+id_lvl*7,LANGUE)<2.50){
                    suivant.setClickable(false);}
                     ratingBarbest.setRating(db.getsi(id_cat,id_lvl,imgafficher+id_lvl*7,LANGUE));

        }

        ratingBar.setRating(0);
        Button PRC = (Button)findViewById(R.id.precedent);
        PRC.setVisibility(View.INVISIBLE);
    }



        public void setWinner(String email){
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"setWinner.php?id="+getIdGame()+"&email="+email, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {

//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


                    try {

                        JSONArray jsonArray =new JSONArray(response);
                                } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(Main3ActivityImage.this,"Errorcnx",Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String,String> params = new HashMap<String, String>();


                    return params;
                }
            };
            queue.add(stringRequest);
        }
    public void GamePlayedInc(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"gplayedinc.php?email="+myemail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


                try {

                    JSONArray jsonArray =new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Main3ActivityImage.this,"Errorcnx",Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> params = new HashMap<String, String>();


                return params;
            }
        };
        queue.add(stringRequest);

    }

    public void GameWonInc(){

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"gwoninc.php?email="+myemail, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {

//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


                    try {

                        JSONArray jsonArray =new JSONArray(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(Main3ActivityImage.this,"Errorcnx",Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String,String> params = new HashMap<String, String>();


                    return params;
                }
            };
            queue.add(stringRequest);

        }
    public void PlayedInc(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"playedinc.php?id="+myemail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


                try {

                    JSONArray jsonArray =new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Main3ActivityImage.this,"Errorcnx",Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> params = new HashMap<String, String>();


                return params;
            }
        };
        queue.add(stringRequest);

    }
    public void endgame() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                setScore(MonEtat);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                GetScores();


            }
        }, 25000);


    }

    public void onPause() {
        super.onPause();
        if(mode.equals("multi"))
        FinishGame();
        mp.stop();




    }


    public void getidgame(String receiver,String sender){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"getidgame.php?receiver="+receiver+"&sender="+sender, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


                try {

                    JSONArray jsonArray =new JSONArray(response);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    int id = jsonObject1.getInt("id");
                    setIdGame(id);


//                prefs = getSharedPreferences("table",MODE_PRIVATE);
//                SharedPreferences.Editor editor = prefs.edit();
//
//                editor.putString("invited","salut");
//                editor.commit();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Main3ActivityImage.this,"Errorcnx",Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> params = new HashMap<String, String>();


                return params;
            }
        };
        queue.add(stringRequest);



        //     Toast.makeText(Lobby.this,s,Toast.LENGTH_LONG).show();


    }
    public void setScore(String monEtat){
        float myscore=0;
            PlayedInc();
            myscore = myscore+OnlineScoreT[0]+OnlineScoreT[1]+OnlineScoreT[2]+OnlineScoreT[3]+OnlineScoreT[4]+OnlineScoreT[5]+OnlineScoreT[6];
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"setscore"+monEtat+".php?email="+myemail+"&idgame="+getIdGame()+"&score="+myscore, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


                try {


                    JSONArray jsonArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main3ActivityImage.this, "Errorcnx", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                return params;
            }
        };
        queue.add(stringRequest);



    }
    public void GetScores(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"getscores.php?idgame="+getIdGame(), new Response.Listener<String>() {
            @Override

            public void onResponse(String response)
            {

//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


                try {
                    GamePlayedInc();

                    JSONArray jsonArray =new JSONArray(response);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    int scoresender= jsonObject1.getInt("scoresender");
                    int scorereceiver= jsonObject1.getInt("scorereceiver");
                    String sender=jsonObject1.getString("sender");
                    String receiver=jsonObject1.getString("receiver");
                         String winner="";
                         String looser="";
                    if (MonEtat.equals("receiver")) {

                        if (scorereceiver < scoresender) {
                            setWinner(getMysender());
                            winner=("Winner : "+getMysender()+ " Score : " +scoresender);
                            looser=("Looser: "+myemail + " Score : "+scorereceiver);
                            Gamefinished("You Lost" ,winner,looser);

                        }
                        if (scorereceiver > scoresender) {
                            setWinner(myemail);
                            winner=("Winner : "+myemail+ " Score : " +scorereceiver);
                            looser=("Looser: "+getMysender() + " Score : "+scoresender);
                            Gamefinished("You won!",winner,looser);
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.won);

                            mp.start();
                            GameWonInc();
                        }
                        if (scorereceiver==scoresender){
                            setWinner("");
                            winner=( myemail+ " Score : " +scorereceiver);
                            looser=( getMysender() + " Score : "+scoresender);
                            Gamefinished("Equality !!",winner,looser);
                        }
                    }
                    if (MonEtat.equals("sender")) {
                        if (scorereceiver < scoresender) {
                            setWinner(myemail);
                            winner=("Winner : "+myemail+ " Score : " +scoresender);
                            looser=("Looser: "+receiver + " Score : "+scorereceiver);
                            Gamefinished("You won!",winner,looser);
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.won);
                            mp.start();
                            GameWonInc();

                        }
                        if (scorereceiver > scoresender) {
                            winner=("Winner : "+receiver+ " Score : " +scorereceiver);
                            looser=("Looser: "+ myemail+ " Score : "+scoresender);
                            setWinner(getMysender());
                            Gamefinished("You Lost",winner,looser);

                        }
                        if (scorereceiver==scoresender){
                            setWinner("");
                            winner=( myemail+ " Score : " +scorereceiver);
                            looser=( receiver + " Score : "+scoresender);
                            Gamefinished("Equality !!",winner,looser);
                        }

                    }



//                prefs = getSharedPreferences("table",MODE_PRIVATE);
//                SharedPreferences.Editor editor = prefs.edit();
//
//                editor.putString("invited","salut");
//                editor.commit();



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Main3ActivityImage.this,"Errorcnx",Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> params = new HashMap<String, String>();


                return params;
            }
        };
        int i =0;
        if (i==0) {
            queue.add(stringRequest);
            i++;
        }
    }
    public void FinishGame(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"finishgame.php?idgame="+getIdGame(), new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {

//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


                try {


                    JSONArray jsonArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main3ActivityImage.this, "Errorcnx", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                return params;
            }
        };
        queue.add(stringRequest);

    }

    public void Gamefinished(String Resultat , String winner , String looser){
        FinishGame();
        if (RUN==0) {
            RUN=1;
            final Dialog dialog = new Dialog(Main3ActivityImage.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.resultat_online);
            dialog.setCancelable(true);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            TextView winnertxt=(TextView)dialog.findViewById(R.id.winner);
            winnertxt.setText(winner);
            TextView loosertxt=(TextView)dialog.findViewById(R.id.looser);
            loosertxt.setText(looser);
            TextView t1=(TextView)dialog.findViewById(R.id.resultattxt);
            t1.setText( Resultat );
            Button next = (Button) dialog.findViewById(R.id.golobby);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    finish();
                    RUN=0;
                }
            });

            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();


    }

    public void startClick(View view) {
        mp.setVolume(0,0);
        Button start = (Button) findViewById(R.id.start);
        start.setBackgroundResource(R.drawable.micact);
        switchSearch(DIGITS_SEARCH);
    }

    public void suivant(View view) {
        ImageView image = (ImageView)findViewById(R.id.IV);
        ((TextView) findViewById(R.id.aprononcer1)).setText("You Said :");
        if (imgafficher%7==0){
            SUI=imgafficher+1;
            PRC=imgafficher;
        }
        if((imgafficher>0)&&(imgafficher<6)){
            SUI=imgafficher+1;
            PRC=SUI-2;
        }
        if(imgafficher%7==6) {
            PRC=imgafficher-1;
            SUI=imgafficher;
            imgafficher--;
        }
        imgafficher++;
        TextView prononcer=(TextView)findViewById(R.id.aprononcer);
        image.setImageBitmap(quiz.get(imgafficher));
        RatingBar ratingBar1 = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar1.setRating(0);

        ratingBar = (RatingBar) findViewById(R.id.bestscore);
        Button sui = (Button) findViewById(R.id.suivant);
        if (imgafficher==6){
            sui.setVisibility(View.INVISIBLE);
        }
        Button PRC = (Button) findViewById(R.id.precedent);

        if (mode.equals("solo")){
            prononcer.setText(MOT.get((imgafficher)%7));
            ratingBar.setRating(db.getsi(id_cat,id_lvl,imgafficher+id_lvl*7,LANGUE));
        if (db.getsi(id_cat,id_lvl,imgafficher+id_lvl*7,LANGUE)<2.5){
            sui.setClickable(false);}


        }

        if(mode.equals("multi")){
            prononcer.setText( Mot3.get((imgafficher) ));
            ((TextView) findViewById(R.id.aprononcer1)).setText("You Said :");
            image.setImageBitmap(quiz.get(imgafficher));
            ratingBar1.setRating(0);

            ratingBar.setRating(OnlineScoreT[imgafficher]);
            if (OnlineScoreT[imgafficher] < 2.5) {
                sui.setClickable(false);
            }



        }
        PRC.setVisibility(View.VISIBLE);
    }


    public void precedent(View view) {




                ImageView image = (ImageView) findViewById(R.id.IV);
                if (imgafficher % 7 == 0) {
                    SUI = imgafficher + 1;
                    PRC = imgafficher;
                }
                if ((imgafficher > 0) && (imgafficher < 6)) {
                    SUI = imgafficher + 1;
                    PRC = SUI - 2;
                    imgafficher--;
                }
                if (imgafficher % 7 == 6) {
                    PRC = imgafficher - 1;
                    SUI = imgafficher;
                    imgafficher--;
                }
                image.setImageBitmap(quiz.get(PRC));
                TextView prononcer = (TextView) findViewById(R.id.aprononcer);
                Button PRC = (Button) findViewById(R.id.precedent);
                Button sui = (Button) findViewById(R.id.suivant);
                 ratingBar = (RatingBar) findViewById(R.id.bestscore);

        RatingBar ratingBar1 = (RatingBar) findViewById(R.id.ratingBar);
                 ((TextView) findViewById(R.id.aprononcer1)).setText("You Said :");
                 ratingBar1.setRating(0);
                ratingBar = (RatingBar) findViewById(R.id.bestscore);
        sui.setVisibility(View.VISIBLE);

        if (imgafficher == 0) {
            PRC.setVisibility(View.INVISIBLE);
        }
        if(mode.equals("solo")) {
            prononcer.setText( MOT.get((imgafficher) % 7));

                ratingBar.setRating(db.getsi(id_cat, id_lvl, imgafficher + id_lvl * 7,LANGUE));


                if (db.getsi(id_cat, id_lvl, imgafficher + id_lvl * 7,LANGUE) > 2.5) {
                    sui.setClickable(true);
                }

            }


        if(mode.equals("multi")){
            ((TextView) findViewById(R.id.aprononcer1)).setText("You Said :");
            prononcer.setText(Mot3.get(imgafficher));
            ratingBar.setRating(OnlineScoreT[imgafficher]);

            if (OnlineScoreT[imgafficher]>2.5) {
                sui.setClickable(true);
            }
           }

    }

    public void suivantonline(View view) {

    }

    public void precedentonline(View view) {

    }

    public void updateleveletat(){
        int i=0;
        int x=1;

        while (i<db.SizeOfLevelOfImage(id_lvl)){
            if (db.getsi(id_cat,id_lvl,6+id_lvl*7,LANGUE)>2.5){
                x=x*x;
//                db.unlocklvl(i+1);
            }
            else{
                x=x*0;
            }
            i++;
        }
        if (x==1){
            mp = MediaPlayer.create(getApplicationContext(), R.raw.won);
            mp.setLooping(true);
            mp.start();
            db.unlocklvl(id_lvl+1,LANGUE);
        }
    }

    private static class SetupTask extends AsyncTask<Void, Void, Exception> {
        WeakReference<Main3ActivityImage> activityReference;
        SetupTask(Main3ActivityImage activity) {
            this.activityReference = new WeakReference<>(activity);
        }
        @Override
        protected Exception doInBackground(Void... params) {
            try {
                Assets assets = new Assets(activityReference.get());
                File assetDir = assets.syncAssets();
                activityReference.get().setupRecognizer(assetDir);
            } catch (IOException e) {
                return e;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Exception result) {

            if (result != null) {
                ((TextView) activityReference.get().findViewById(R.id.caption_text))
                        .setText("Failed to init recognizer " + result);
            } else {
                activityReference.get().switchSearch(KWS_SEARCH);
                btnStart.setEnabled(true);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull  int[]
                                                   grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                // Recognizer initialization is a time-consuming and it involves IO,
                // so we execute it in async task
                new SetupTask(this).execute();
            } else {

                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

            if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {

        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();
        if (text.equals(KEYPHRASE))
            switchSearch(DIGITS_SEARCH);

        else
            ((TextView) findViewById(R.id.aprononcer1)).setText("You Said : "+text);



        score = hypothesis.getBestScore();

    }



    /**
     * This callback is called when we stop the recognizer.
     */

    @Override
    public void onResult(Hypothesis hypothesis) {
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        RatingBar best = (RatingBar) findViewById(R.id.bestscore);
        ratingBar.setRating(0);
        TextView aprononcer=(TextView)findViewById(R.id.aprononcer);
        TextView jetonnbr = (TextView)findViewById(R.id.jetonnbr);
        Button suivant = (Button) findViewById(R.id.suivant);
        ///////////////////////////////////////////////////
        if (hypothesis != null) {
            if (if_word_correct((String) aprononcer.getText(), hypothesis) == true) {
                score = hypothesis.getBestScore();
            Toast.makeText(this, "score="+score + "star="+getScoreStars(score), Toast.LENGTH_SHORT).show();ratingBar.setRating(getScoreStars(score));
                if (mode.equals("solo")) {
                    if (ratingBar.getRating()>2.5){
                           if (db.ImageEtat(imgafficher + id_lvl * 7,LANGUE)==0){
                               db.ImageUnlock(id_cat,id_lvl,imgafficher + id_lvl * 7,LANGUE);
                               sharedPreferences.edit().putInt("JETON",sharedPreferences.getInt("JETON",0)+(int)ratingBar.getRating()-1).apply();
                               jetonnbr.setText("" + sharedPreferences.getInt("JETON", 0));}}
                    if (db.getsi(id_cat, id_lvl, imgafficher + id_lvl * 7,LANGUE) < getScoreStars(score)) {
                        best.setRating(getScoreStars(score));
                        db.setimageScore(id_cat, id_lvl, imgafficher + id_lvl * 7, (double) getScoreStars(score),LANGUE);
                        db.updatesn(id_cat, id_lvl,LANGUE);
                        db.updatesc(id_cat,LANGUE);
                    }
                }
                if (mode.equals("multi")) {   if (getScoreStars(score) > OnlineScoreT[imgafficher]) { OnlineScoreT[imgafficher] = (getScoreStars(score));}
                    best.setRating(OnlineScoreT[imgafficher]);  ratingBar.setRating(getScoreStars(score));  }
                if (mode.equals("solo")) {
                    if (db.getsi(id_cat, id_lvl, imgafficher + id_lvl * 7,LANGUE) > 2.5) {
                        suivant.setClickable(true);
                        updateleveletat();

                    }
                }
                if (mode.equals("multi")) {
                    if (OnlineScoreT[imgafficher] >= 2.5) {
                        suivant.setClickable(true);
                    }
                }


            }
            Button start = (Button) findViewById(R.id.start);

            start.setBackgroundResource(R.drawable.start);
        }

    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech(){
        if (!recognizer.getSearchName().equals(KWS_SEARCH))
            switchSearch(KWS_SEARCH);


    }

    private void switchSearch(String searchName) {
        recognizer.stop();

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        if (searchName.equals(KWS_SEARCH))
            recognizer.startListening(searchName);
        else
            recognizer.startListening(searchName, 10000);

        String caption = getResources().getString(captions.get(searchName));
        ((TextView) findViewById(R.id.caption_text)).setText(caption);
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, ptm))
                .setDictionary(new File(assetsDir, dict))

                .setRawLogDir(assetsDir) /* To disable logging of raw audio comment out this
        call (takes a lot of space on the device)*/

                .getRecognizer();
        recognizer.addListener(this);

        /* In your application you might not need to add all those searches.
          They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);

        // Create grammar-based search for selection between demos
        //  File menuGrammar = new File(assetsDir, "menu.gram");
        //recognizer.addGrammarSearch(MENU_SEARCH, menuGrammar);

        // Create grammar-based search for digit recognition
        File digitsGrammar = new File(assetsDir, grm);
        recognizer.addGrammarSearch(DIGITS_SEARCH, digitsGrammar);

        // Create language model search
        //File languageModel = new File(assetsDir, "weather.dmp");
        //recognizer.addNgramSearch(FORECAST_SEARCH, languageModel);

        // Phonetic search
        //File phoneticModel = new File(assetsDir, "en-phone.dmp");
        //recognizer.addAllphoneSearch(PHONE_SEARCH, phoneticModel);
    }

    @Override
    public void onError(Exception error) {
        ((TextView) findViewById(R.id.caption_text)).setText(error.getMessage());
    }

    @Override
    public void onTimeout() {
        switchSearch(KWS_SEARCH);
    }
    /*
        public float getScoreStars(int paramInt) {
            if ((paramInt <= 0) && (paramInt > 64536)) {
                return 3.0F;
            }
            if ((paramInt <= 64536) && (paramInt > 63536)) {
                return 2.5F;
            }
            if ((paramInt <= 63536) && (paramInt > 62536)) {
                return 2.0F;
            }
            if ((paramInt <= 62536) && (paramInt > 61536)) {
                return 1.5F;
            }
            if ((paramInt <= 61536) && (paramInt > 60536)) {
                return 1.0F;
            }
            if ((paramInt <= 60536) && (paramInt > 59536)) {
                return 0.5F;
            }
            return 0.0F;
        }
    */
    @SuppressLint("Range")
    public void speakClick(final String text) {
        TextView prononcer=(TextView)findViewById(R.id.aprononcer);


        if (sharedPreferences.getInt("JETON", 0)>=3) {

            final Dialog dialog = new Dialog(Main3ActivityImage.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.confirmspeak);
            dialog.setCancelable(true);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            final TextView jetonnbr = (TextView) findViewById(R.id.jetonnbr);
            Button yes = (Button) dialog.findViewById(R.id.startspeak);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textToSpeech.speak("You should Say " + text, TextToSpeech.QUEUE_FLUSH, null);
                    sharedPreferences
                            .edit()
                            .putInt("JETON", sharedPreferences.getInt("JETON", 0) - 3)
                            .apply();
                    jetonnbr.setText("" + sharedPreferences.getInt("JETON", 0));
                    if (sharedPreferences.getInt("JETON", 0) < 3) {
                        Button jetonimg = (Button) findViewById(R.id.jetonimg);
                        jetonimg.setClickable(false);
                    }
                    dialog.cancel();
                }
            });
            final Button suivant = (Button) findViewById(R.id.suivant);

            final Button skip = (Button) dialog.findViewById(R.id.skipimg);

            if (db.ImageEtat(imgafficher + id_lvl * 7,LANGUE) == 1 || sharedPreferences.getInt("JETON", 0) < 5) {
                skip.setEnabled(false);
//                skip.setAlpha(50.0f);
                skip.setBackgroundResource(R.drawable.boutonnonclickable);
            }
                skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        db.setimageScore(id_cat, id_lvl, imgafficher + id_lvl * 7, 5.0f,LANGUE);
                        db.updatesn(id_cat, id_lvl,LANGUE);
                        db.updatesc(id_cat,LANGUE);
                        suivant.setClickable(true);
                        updateleveletat();
                        db.ImageUnlock(id_cat, id_lvl, imgafficher + id_lvl * 7,LANGUE);
                        suivant.performClick();
                        sharedPreferences
                                .edit()
                                .putInt("JETON", sharedPreferences.getInt("JETON", 0) - 5)
                                .apply();
                        jetonnbr.setText("" + sharedPreferences.getInt("JETON", 0));
                        dialog.cancel();

                    }
                });

                dialog.show();
                dialog.getWindow().setAttributes(lp);

        }
    }
    public float getScoreStars(int getBestScore) {
        float e=0.0f;
        if (LANGUE.equals("EN")) {
            if (getBestScore <= 0 && getBestScore > -1500)
                e= 5.0f;
            else if (getBestScore <= -1500 && getBestScore > -2200)
                e=  4.5f;
            else if (getBestScore <= -2200 && getBestScore > -2700)
                e=  4.0f;
            else if (getBestScore <= -2700 && getBestScore > -3500)
                e=  3.0f;
            else if (getBestScore <= -3500 && getBestScore > -4000)
                e=  2.0f;
            else if (getBestScore <= -4000 && getBestScore > -4500)
                e=  1.0f;
            else
                e=  0.0f;
        }
        if (LANGUE.equals("FR")) {
            if (getBestScore <= 0 && getBestScore > -15000)
                e= 5.0f;
            else if (getBestScore <= -15000 && getBestScore > -22000)
                e=  4.5f;
            else if (getBestScore <= -22000 && getBestScore > -27000)
                e=  4.0f;
            else if (getBestScore <= -27000 && getBestScore > -35000)
                e=  3.0f;
            else if (getBestScore <= -35000 && getBestScore > -40000)
                e=  2.0f;
            else if (getBestScore <= -40000 && getBestScore > -45000)
                e=  1.0f;
            else
                e=  0.0f;
        }
        return  e;

    }
    //    public void onBackPressed(){
//        Bundle bundle = new Bundle();
//        bundle.putInt("idcat", id_cat);
//        bundle.putInt("idlvl" , id_lvl);
//        Intent intent = new Intent(Main3ActivityImage.this,Main3ActivityImg.class);
//        intent.putExtras(bundle);
//        finish();
//        startActivity(intent);
//    }
    public Boolean if_word_correct(String mot_a_prononce, Hypothesis hypothesis){
        if (hypothesis.getHypstr().equals(mot_a_prononce))
            return true;
        else
            return false;
    }

}
