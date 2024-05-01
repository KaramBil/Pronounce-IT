package com.example.fady.BILAMI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class login extends AppCompatActivity {

    SharedPreferences prefs;

    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            // error action
        }
        return null;
    }

    public static String md5(String txt) {
        return getHash(txt, "MD5");
    }

    public static String sha1(String txt) {
        return getHash(txt, "SHA1");
    }


    EditText email,password;
    Button auth;
    String e,p;
    String Url="http://192.168.1.50/karam/";


    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("table",MODE_PRIVATE);


        email=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        auth=(Button)findViewById(R.id.authentifier);
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        auth.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e = String.valueOf(email.getText());

                p =md5(password.getText().toString());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"login.php?Email="+e+"&password="+p, new Response.Listener<String>() {
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
                                    Toast.makeText(login.this,code ,Toast.LENGTH_LONG).show();
                                    if (code.equals("OK")){
                                        Bundle bundle = new Bundle();
                                        bundle.putString("email",e);
                                        textToSpeech.speak("Welcome" + e, TextToSpeech.QUEUE_FLUSH, null);

                                        Intent intent1 = new Intent(login.this,Lobby.class);
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
                        Toast.makeText(login.this,"Errorcnx",Toast.LENGTH_LONG).show();
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

    });
}


    public void GO(View view) {
        Intent i = new Intent(login.this, Register.class);
        Toast.makeText(login.this,"code",Toast.LENGTH_LONG).show();

        startActivity(i);

    }


}