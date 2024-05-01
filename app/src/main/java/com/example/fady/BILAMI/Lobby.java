package com.example.fady.BILAMI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import android.os.Handler;

public class Lobby extends AppCompatActivity {

    public int getIdGameCreated() {
        return IdGameCreated;
    }

    public void setIdGameCreated(int idGame) {


        IdGameCreated = idGame;
    }
    SpinnerAdapter adapter;

    int IdGameCreated;

    final ArrayList<Integer> invited=new ArrayList<>();

    final ArrayList<Users> list = new ArrayList<>();
    int reqCompteur=0;
Button pauselob;
    long startTime = 0;
    SharedPreferences prefs;
    String myemail;
    int isInvited=0;
    String Url="http://192.168.1.50/karam/";

    int INV= 0;
    String[] Spinneritems;


int spineritem;
    public String getMysender() {
        return mysender;
    }

    public void setMysender(String mysender) {
        this.mysender = mysender;
    }

    public int getCategtoplay() {
        return categtoplay;
    }

    public void setCategtoplay(int categtoplay) {
        this.categtoplay = categtoplay;
    }
SharedPreferences sharedPreferences2;
    MediaPlayer mp;
    int categtoplay;
    String mysender;
TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        myemail=bundle.getString("email");

        sharedPreferences2 = getBaseContext().getSharedPreferences("SOUND", MODE_PRIVATE);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.jungle);
        mp.setLooping(true);
        mp.start();
        if(sharedPreferences2.getInt("SOUNDS",0)==2){
            mp.setVolume(0,0);
        }


        setContentView(R.layout.activity_lobby);
        adapter=new SpinnerAdapter(getApplicationContext());
        Spinneritems = getResources().getStringArray(R.array.Names);
        Button Pause = (Button)findViewById(R.id.LobbySetting);
        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Lobby.this);
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
                        Intent intent = new Intent(Lobby.this,MainActivity.class);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyProfileremplissage();
        SetOnline(myemail);
        ChekRequest();
        INV=0;
    }

    private void MyProfileremplissage() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"getsender.php?email="+myemail, new Response.Listener<String>() {
            @Override

            public void onResponse(String response)
            {


                try {

                    JSONArray jsonArray =new JSONArray(response);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                   int Gplayed = jsonObject1.getInt("played");
                   int avatar = jsonObject1.getInt("avatar");
                   int Gwon=jsonObject1.getInt("won");
                    TextView gpl =(TextView)findViewById(R.id.mygameplayed);
                    TextView gwn = (TextView)findViewById(R.id.mygamewon);
                    TextView mgacc = (TextView)findViewById(R.id.mywinacc);
                    gpl.setText("G/Played : "+Gplayed);
                    gwn.setText("G/won : "+Gwon);
                    double M=(double)Gwon/Gplayed;
                    M=M*100;
                    if (Gplayed==0)
                        M=0;
                    int m=(int)M;
                    mgacc.setText("Win ACC : "+m+"%");
                    TextView email = (TextView)findViewById(R.id.mynomdeprofile);
                    email.setText(myemail);
                    ImageView im = (ImageView)findViewById(R.id.photodeprofile);
                    im.setImageResource(avatar);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Lobby.this,"Errorcnx",Toast.LENGTH_LONG).show();
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

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        SetOffline(myemail);
        mp.stop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void SetOnline(String email){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"setonline.php?email="+email, new Response.Listener<String>() {
            @Override

            public void onResponse(String response)
            {
                try {



                    // JSONArray jsonArray =new JSONArray(response);

                    JSONArray jsonArray =new JSONArray(response);

                    // JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    //String code = jsonObject.getString("code");
                    String code = jsonObject1.getString("code");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {                error.printStackTrace();

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
    public int getImInvited() {
        return ImInvited;
    }

    public void setImInvited(int imInvited) {
        ImInvited = imInvited;
    }

    int ImInvited=0;

    public int getIsInvited() {
        return isInvited;
    }



    public void setIsInvited(int isInvited) {
        this.isInvited = isInvited;
    }




    public void ChekRequest(){

        timerHandler.postDelayed(timerRunnable, 0);
    }
    //runs without a timer by repostging this handler at the end of the runnable
    Handler timerHandler = new Handler();


    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            IfMyInvitationAccepted();
            IfImInvited();
            Getonlineusers();
            timerHandler.postDelayed(this, 1000);
        }
    };

    public void RefuserInv(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url+"refuserinv.php?email="+myemail, new Response.Listener<String>() {
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
                Toast.makeText(Lobby.this,"Errorcnx",Toast.LENGTH_LONG).show();
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


    public void GetRequestSender(){


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"getsender.php?email="+myemail, new Response.Listener<String>() {
            @Override

            public void onResponse(String response)
            {


                try {

                    JSONArray jsonArray =new JSONArray(response);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String sender = jsonObject1.getString("sender");
                    setMysender(sender);
                    int cat = jsonObject1.getInt("categ");
                    setCategtoplay(cat);




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Lobby.this,"Errorcnx",Toast.LENGTH_LONG).show();
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


    public void SetOffline(String email){
        RefuserInv();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"setoffline.php?email="+email, new Response.Listener<String>() {
            @Override

            public void onResponse(String response)
            {
                try {



                    // JSONArray jsonArray =new JSONArray(response);

                    JSONArray jsonArray =new JSONArray(response);

                    // JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    //String code = jsonObject.getString("code");
                    String code = jsonObject1.getString("code");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {                error.printStackTrace();

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


    public void Getonlineusers() {


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"getonline.php", new Response.Listener<String>() {
            @Override

            public void onResponse(String response)
            {
//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();

                list.clear();

                try {

                    JSONArray jsonArray =new JSONArray(response);

                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String code = jsonObject1.getString("email");
                        if(!code.equals(myemail)){
                            int id = jsonObject1.getInt("id");
                            int invited = jsonObject1.getInt("invited");
                            String sender = jsonObject1.getString("sender");
                            int gp=jsonObject1.getInt("played");
                            int gw=jsonObject1.getInt("won");
                            int avatar=jsonObject1.getInt("avatar");
                            list.add(new Users(id,code,invited,sender,gp,gw,avatar));}
                    }
                    ListAdapter listAdapter = new ListAdapter(list);
                    ListView ls =(ListView)findViewById(R.id.OnlinePlayers);
                    ls.setAdapter(listAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Lobby.this,"Errorcnx",Toast.LENGTH_LONG).show();
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
    public void getidgameCreated(String sender){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"getidgame.php?receiver="+myemail+"&sender="+sender, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


                try {

                    JSONArray jsonArray =new JSONArray(response);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    int id = jsonObject1.getInt("id");
                   setIdGameCreated(id);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Lobby.this,"Errorcnx",Toast.LENGTH_LONG).show();
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
    int a,b,c,d,e,f,g;
    public void randomImages(){

        a=(int)(Math.random()*34);
        b=(int)(Math.random()*34);
        while (b==a){            b=(int)(Math.random()*34);        }
        c=(int)(Math.random()*34);
        while (c==b || c==a){           c=(int)(Math.random()*34);        }
        d=(int)(Math.random()*34);
        while (d==c || d==b || d==a){         d=(int)(Math.random()*34);        }
        e=(int)(Math.random()*34);
        while (e==d || e==b || e==c ||e==a){         e=(int)(Math.random()*34);        }
        f=(int)(Math.random()*34);
        while (f==e|| f==d || f==c || f==b || f==a) {            f=(int)(Math.random()*34);         }
        g=(int)(Math.random()*34);
        while (g==f || g==e || g==d || g==c || g==b || g==a){            g=(int)(Math.random()*34);         }
    }
    public void CreateGame(String sender){

        randomImages();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url+"creategame.php?email="+myemail+"&sender="+getMysender()+"&categ=0&a=1&b=2&c=5&d=9&e=7&f=8&g=9", new Response.Listener<String>() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url+"creategame.php?email="+myemail+"&sender="+sender+"&categ="+getCategtoplay()+"&a="+a+"&b="+b+"&c="+c+"&d="+d+"&e="+e+"&f="+f+"&g="+g, new Response.Listener<String>() {


            @Override

            public void onResponse(String response)
            {
//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();

                try {
                    JSONArray jsonArray =new JSONArray(response);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    int idgame = jsonObject1.getInt("id");

                    Bundle bundle = new Bundle();
                    bundle.putInt("id",idgame);
                    bundle.putInt("a",a);
                    bundle.putInt("b",b);
                    bundle.putInt("c",c);
                    bundle.putInt("d",d);
                    bundle.putInt("e",e);
                    bundle.putInt("f",f);
                    bundle.putInt("g",g);
                    bundle.putString("mode", "multi");
                    bundle.putString("etat", "receiver");
                    bundle.putInt("idcat",getCategtoplay());
                    bundle.putString("myemail", myemail);
                    bundle.putString("sender", getMysender());
                    Intent intent1 = new Intent(Lobby.this, Main3ActivityImage.class);
                    intent1.putExtras(bundle);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    startActivity(intent1);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Lobby.this,"Errorcnx",Toast.LENGTH_LONG).show();
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



    public void RequestIN(final String email, int categ,int avat){
       if (INV==0) {
           INV=1;
           String cat="";
           switch (categ) {
               case 0: {
                   cat = "Histoire";
                   break;
               }
               case 1: {
                   cat = "Science";
                   break;
               }
               case 2: {
                   cat = "Sport";
                   break;
               }
               case 3: {
                   cat = "Technologie";
                   break;
               }
               case 4: {
                   cat = "Music";
                   break;
               }
               case 5: {
                   cat = "Animals";
                   break;
               }
               case 6: {
                   cat = "Art";
                   break;
               }
               case 7: {
                   cat = "Geography";
                   break;
               }
               case 8: {
                   cat = "Food";
                   break;
               }
               case 9: {
                   cat = "Shadow";
                   break;
               }

           }
           final Dialog dialog = new Dialog(Lobby.this);
           dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
           dialog.setContentView(R.layout.requestin);
           dialog.setCancelable(false);
           WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
           dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           lp.copyFrom(dialog.getWindow().getAttributes());
           lp.width = WindowManager.LayoutParams.MATCH_PARENT;
           TextView inviter=(TextView)dialog.findViewById(R.id.invited);
           inviter.setText( " Sender : "+ email );
           TextView catego=(TextView)dialog.findViewById(R.id.categoryinv);
           catego.setText(" Category =" +cat );
        ImageView im =(ImageView)dialog.findViewById(R.id.senderavatar);
           im.setImageResource(avat);
           Button accept = (Button) dialog.findViewById(R.id.acceptinv);
           accept.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   CreateGame(email);
                   dialog.cancel();
               }
           });


           Button refuse = (Button) dialog.findViewById(R.id.refuseinv);
           refuse.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   RefuserInv();
                   INV=0;
                   dialog.cancel();
               }
           });

           dialog.show();
           dialog.getWindow().setAttributes(lp);


        }
    }

    public void INVITE(String sender , String receiver , int categ){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url+"inviting.php?sender="+sender+"&rec="+receiver+"&categ="+categ, new Response.Listener<String>() {
            @Override

            public void onResponse(String response)
            {
                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


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
                Toast.makeText(Lobby.this,"Errorcnx",Toast.LENGTH_LONG).show();
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


    public void IfMyInvitationAccepted(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"IfMyInvitationAccepted.php?email="+myemail, new Response.Listener<String>() {
            @Override

            public void onResponse(String response)
            {

//                Toast.makeText(Lobby.this,response,Toast.LENGTH_LONG).show();


                try {
                    JSONArray jsonArray =new JSONArray(response);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String accepted = jsonObject1.getString("COUNT(*)");
                    JSONObject jsonObject2 = jsonArray.getJSONObject(1);
                    if (accepted.equals("1")){
                        Toast.makeText(Lobby.this, "accepté id game = "+jsonObject2.getInt("id"), Toast.LENGTH_SHORT).show();

                        int idgame = jsonObject2.getInt("id");
                        int categ = jsonObject2.getInt("categ");
                        Bundle bundle = new Bundle();
                        bundle.putInt("a",jsonObject2.getInt("img1"));
                        bundle.putInt("b",jsonObject2.getInt("img2"));
                        bundle.putInt("c",jsonObject2.getInt("img3"));
                        bundle.putInt("d",jsonObject2.getInt("img4"));
                        bundle.putInt("e",jsonObject2.getInt("img5"));
                        bundle.putInt("f",jsonObject2.getInt("img6"));
                        bundle.putInt("g",jsonObject2.getInt("img7"));
                        bundle.putString("mode", "multi");
                        bundle.putString("etat", "sender");
                        bundle.putString("myemail", myemail);
                        bundle.putInt("idcat",categ);
                        bundle.putInt("id",idgame);
                        Intent intent1 = new Intent(Lobby.this, Main3ActivityImage.class);
                        intent1.putExtras(bundle);
                           startActivity(intent1);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Lobby.this,"Errorcnx",Toast.LENGTH_LONG).show();
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

    public void IfImInvited(){
        GetRequestSender();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"isInvited.php?email="+myemail, new Response.Listener<String>() {
            @Override

            public void onResponse(String response)
            {



                try {

                    JSONArray jsonArray =new JSONArray(response);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String invited = jsonObject1.getString("invited");
                    if (invited.equals("1")){
                        int categ=jsonObject1.getInt("categ");
                        int avat=jsonObject1.getInt("avatar");
                        String myysender= jsonObject1.getString("sender");

                        RequestIN(myysender,categ,avat);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Lobby.this,"Errorcnx",Toast.LENGTH_LONG).show();
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


    public void Annuler(String receiver){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url+"invitationannul.php?rec="+receiver, new Response.Listener<String>() {
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
                Toast.makeText(Lobby.this,"Errorcnx",Toast.LENGTH_LONG).show();
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SetOffline(myemail);
    }




    class ListAdapter extends BaseAdapter {
        ArrayList<Users> list = new ArrayList<>();

        ListAdapter(ArrayList<Users> list2) {
            this.list = list2;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position).Email;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            final View view = layoutInflater.inflate(R.layout.user_item, null);
            final Button invite =(Button)view.findViewById(R.id.invite);
            TextView Email = (TextView) view.findViewById(R.id.email);
            ImageView im=(ImageView)view.findViewById(R.id.onlineimg);
            im.setImageResource(list.get(i).avatar);
            Button checkprof = (Button)view.findViewById(R.id.checkprof);
            checkprof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Dialog dialog = new Dialog(Lobby.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.profile);
                    dialog.setCancelable(true);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    // lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                    // set the custom dialog components - text, image and button

                    Button close = (Button) dialog.findViewById(R.id.closeprof);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
            TextView email=(TextView)dialog.findViewById(R.id.emailprof);
            email.setText("Email : " +list.get(i).Email);

                    TextView gameplayed=(TextView)dialog.findViewById(R.id.gameplayed);
                    gameplayed.setText("G/played : "+list.get(i).gp);

                    TextView gwon=(TextView)dialog.findViewById(R.id.gamewon);
                    gwon.setText("G/won : " + list.get(i).gw);
                    ImageView im = (ImageView)dialog.findViewById(R.id.userimg);
                    im.setImageResource(list.get(i).avatar);
TextView moy=(TextView)dialog.findViewById(R.id.moyenne);
                    int x = list.get(i).gw;
                    int y=list.get(i).gp;
                    double M=(double)x/y;
                    M=M*100;
                    if (y==0)
                        M=0;
                    int m=(int)M;
                    moy.setText("Win Accuracy= "+m+"%");

                    dialog.show();
                    dialog.getWindow().setAttributes(lp);









                }
            });
            //  TextView txtid = (TextView) view.findViewById(R.id.textView_id);
            Email.setText(list.get(i).Email);
            if (list.get(i).invited == 0) {
                invite.setBackgroundResource(R.drawable.invitee);
            }
            if (list.get(i).invited == 1) {
                if (list.get(i).sender.equals(myemail)) {
                    invite.setBackgroundResource(R.drawable.cancell);}
                else {
                    invite.setText("Busy");
                    invite.setClickable(false);

                }
            }

            invite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(i).sender != myemail) {
                        if (list.get(i).invited == 0) {

                         //
                            //invite.setText("Annuler");
                      //      invite.setClickable(false);


                            final Dialog dialog = new Dialog(Lobby.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.activity_invitation);
                            dialog.setCancelable(true);
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                           // lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                            // set the custom dialog components - text, image and button
                            final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinnerchoix);
                            Button invite = (Button) dialog.findViewById(R.id.invite1);
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    // TODO Auto-generated method stub
                                    spineritem=position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // TODO Auto-generated method stub

                                }
                            });
                            invite.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                    INVITE(myemail, list.get(i).Email,spineritem);
                                }
                            });
                            dialog.show();
                            dialog.getWindow().setAttributes(lp);



                        }
                        if (list.get(i).invited == 1) {
                            Annuler(list.get(i).Email);
                            invite.setBackgroundResource(R.drawable.invitee);
                            invite.setClickable(false);


                        }

                    }
                }                });





            return view;
        }
    }


    public class SpinnerAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater mInflater;

        public SpinnerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return Spinneritems.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                v = mInflater.inflate(R.layout.row_textview, null);
                holder = new ListContent();
                holder.text =  v.findViewById(R.id.spintext);

                v.setTag(holder);
            } else {
                holder = (ListContent) v.getTag();
            }

           holder.text.setText(Spinneritems[position]);

            return v;
        }
    }

    static class ListContent {
        TextView text;
    }

}
