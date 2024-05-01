package com.example.fady.BILAMI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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

public class Register extends AppCompatActivity {
    public int getAvat() {
        return avat;
    }
    String Url="http://192.168.1.50/karam/";


    public void setAvat(int avat) {
        this.avat = avat;
    }

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

    int avat=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
Button avatarpick = (Button)findViewById(R.id.avatarpick);
avatarpick.setBackgroundResource(R.drawable.avatar1);
        setAvat(R.drawable.avatar1);
        final EditText EM = (EditText)findViewById(R.id.ema);
        final EditText PASS = (EditText)findViewById(R.id.pass);
final EditText CNFPASS = (EditText)findViewById(R.id.passcnf);
        Button INSC = (Button) findViewById(R.id.insc);

        INSC.setOnClickListener(
                new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        if (CNFPASS.getText().toString().equals(PASS.getText().toString())) {
                       String e = String.valueOf(EM.getText());

                       String p = md5(PASS.getText().toString());

                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url+"registre.php?Email="+e+"&password="+p+"&avatar="+getAvat(), new Response.Listener<String>() {
                            @Override

                            public void onResponse(String response)
                            {


                                try {

                                    JSONArray jsonArray =new JSONArray(response);


                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                                    String code = jsonObject1.getString("code");

                                    if (code.equals("OK")){
                                        Toast.makeText(Register.this, "SUBSCRIBED !!!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(Register.this, login.class);
                                        startActivity(i);

                                    }

                                    if (code.equals("NO")){
                                        Toast.makeText(Register.this, "EMAIL ALREADY EXISTS !!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(Register.this, login.class);
                                        startActivity(i);

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(Register.this,"Errorcnx",Toast.LENGTH_LONG).show();
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
                    else {
                            PASS.setBackgroundResource(R.drawable.passwordfalse);
                            CNFPASS.setBackgroundResource(R.drawable.passwordfalse);
                            Toast.makeText(Register.this, "Verify Your Password", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    public void avatarpick(View view) {
        final Dialog dialog = new Dialog(Register.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_avatar);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        GridView GV = (GridView)dialog.findViewById(R.id.gridview);
        final ArrayList<Integer> avatarlist=new ArrayList<>();
        avatarlist.add(R.drawable.avatar1);
        avatarlist.add(R.drawable.avatar2);
        avatarlist.add(R.drawable.avatar3);
        avatarlist.add(R.drawable.avatar4);
        avatarlist.add(R.drawable.avatar5);
        avatarlist.add(R.drawable.avatar6);
        avatarlist.add(R.drawable.avatar7);
        avatarlist.add(R.drawable.avatar8);
        avatarlist.add(R.drawable.avatar9);
        avatarlist.add(R.drawable.avatar10);
        avatarlist.add(R.drawable.avatar11);
        Gridadapter adapter = new Gridadapter(this,avatarlist);
        GV.setAdapter(adapter);
        final Button pickavatar = (Button)findViewById(R.id.avatarpick);
        final Button pick = (Button) dialog.findViewById(R.id.pickeravat);
        GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setAvat(avatarlist.get(position));
                pickavatar.setBackgroundResource(getAvat());
                dialog.cancel();
            }
        });
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}

class Gridadapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Integer> list;

    // 1
    public Gridadapter(Context context, ArrayList<Integer> list2) {
        this.mContext = context;
        this.list = list2;
    }

    // 2
    @Override
    public int getCount() {
        return list.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }



    // 4
    @Override
    public Integer getItem(int position) {
        return list.get(position);
    }

    // 5

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.avatarimage, null);
        }

        ImageView IM1=(ImageView) convertView.findViewById(R.id.avatarchs) ;

        IM1.setImageResource(list.get(position));

        return convertView;
    }

}