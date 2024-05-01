package com.example.fady.BILAMI;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Main3ActivityNiv extends AppCompatActivity {
    MyHelper db= new MyHelper(this);
    int id_cat;
    int imgNiv[] = {
            R.drawable.level1,
            R.drawable.level2,
            R.drawable.level3,
            R.drawable.level4,
            R.drawable.level5,
            R.drawable.level6,
            R.drawable.level7,
            R.drawable.level8,
            R.drawable.level9,};
KenBurnsView cat_im;
    ArrayList<ListItems> listA = new ArrayList<ListItems>();
    private static final String PREF_JETON = "JETON";
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2,sharedPreferences3;
    String LANGUE;

    MediaPlayer mp;

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3_niv);
        ListView ls = (ListView) findViewById(R.id.listView3);
        sharedPreferences2 = getBaseContext().getSharedPreferences("SOUND", MODE_PRIVATE);

        sharedPreferences3 = getBaseContext().getSharedPreferences("LANG", MODE_PRIVATE);
        LANGUE=sharedPreferences3.getString("LN","");
        mp = MediaPlayer.create(getApplicationContext(), R.raw.jungle);
        mp.setLooping(true);
        mp.start();
        if(sharedPreferences2.getInt("SOUNDS",0)==2){
            mp.setVolume(0,0);
        }

        sharedPreferences = getBaseContext().getSharedPreferences(PREF_JETON, MODE_PRIVATE);
        Button Pause = (Button)findViewById(R.id.NivSetting);
        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Main3ActivityNiv.this);
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
                        Intent intent = new Intent(Main3ActivityNiv.this,MainActivity.class);
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

        Bundle bundle = getIntent().getExtras();
        id_cat=bundle.getInt("idcat");
        int categ=0;
        if (id_cat==0){             categ = R.drawable.historyim;        }
        if (id_cat==1){            categ = R.drawable.scienceim;        }
        if (id_cat==2){categ = R.drawable.sportim; }
        if (id_cat==3){categ = R.drawable.technoim;  }
        if (id_cat==4){           categ = R.drawable.musicim;        }
        if (id_cat==5){            categ = R.drawable.animalim;        }
        if (id_cat==6){            categ = R.drawable.artim;        }
        if (id_cat==7){            categ = R.drawable.geoim;        }
        if (id_cat==8){            categ = R.drawable.foodim;        }
        if (id_cat==9){            categ = R.drawable.shadowim;        }



cat_im=(KenBurnsView)findViewById(R.id.categorie_im);
        Picasso.with(getBaseContext()).load(categ).into(cat_im);

///////////-------------------------------------------------------------

//        String[]names = getResources().getStringArray(R.array.Niveau);
//        List<Level> listLVL = new ArrayList<>();
//
//        int id=0;
//        int im=0;
//        if (db.getAllLevels().size()==0){
//            for ( int C=0 ; C<5 ; C++) {
//                for(String i : names) {
//
//                    Level L = new Level (id,i,5,null, C , imgNiv[im],0);
//                    listLVL.add(L);
//                    db.createLevel(listLVL.get(id));
//
//                    im++;                id++;
//                }
//                im=0;}}
openlevels();
//        updateleveletat();


    }
//public void updateleveletat(){
//        int i=0;
//        while (i<db.getAllLevels().size()-1){
//            int c=0;
//            if (db.getsn(c,i)>17){
//               db.unlocklvl(i+1);
//            }
//            c++;
//                c=c%5;
//                i++;
//        }
//}
    public void openlevels(){
       int i=0;
       while (i<db.getAllLevels().size()){
           db.unlocklvl(i,"EN");
           db.unlocklvl(i,"FR");
           i=i+5;
       }
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView jetonnbr = (TextView)findViewById(R.id.jetonnbrnv);
        jetonnbr.setText("" + sharedPreferences.getInt("JETON", 0));
//        updateleveletat();

        ListView ls = (ListView) findViewById(R.id.listView3);

        int i = 0;
        listA.clear();
        while (i<db.getAllLevels().size()){
            Level L = db.getAllLevels().get(i);
            if (L.getCat()== id_cat){
                listA.add(new ListItems(L.idLevel,L.icone,L.getNomLevel(),L.etat,L.etatAR));

            }
            i++;}
////////////////------------------------------------------------------//////////////////

        Main3ActivityNiv.ListAdapter listAdapter = new Main3ActivityNiv.ListAdapter(listA);
        ls.setAdapter(listAdapter);
    }

    class ListAdapter extends BaseAdapter {
        ArrayList<ListItems> list = new ArrayList<ListItems>();

        ListAdapter(ArrayList<ListItems> list2) {
            this.list = list2;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position).name;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.niv_items, null);
            TextView LVLTitle = (TextView) view.findViewById(R.id.Nivname);
            //  TextView txtid = (TextView) view.findViewById(R.id.textView_id);
            ImageView img = (ImageView) view.findViewById(R.id.Nivicone);
         ProgressBar PG = (ProgressBar)view.findViewById(R.id.Nivprogressbar);
            PG.setProgress((int) db.getsn(id_cat,list.get(i).id,LANGUE));
            TextView CATTITLE = (TextView)findViewById(R.id.cat_title);
            CATTITLE.setText(db.getAllCategories().get(id_cat).getNomCat());
            LVLTitle.setText(list.get(i).name);
         img.setImageResource(list.get(i).img);
         ImageView etat=(ImageView)view.findViewById(R.id.etat);
         if (LANGUE.equals("EN")) {
             if (list.get(i).etat == 0) {
                 etat.setImageResource(R.drawable.lock);
             }
             if (list.get(i).etat == 1) {
                 view.setClickable(true);
                 etat.setImageDrawable(null);
             }
         }
         if(LANGUE.equals("FR")){
             if (list.get(i).etatAR == 0) {
                 etat.setImageResource(R.drawable.lock);
             }
             if (list.get(i).etatAR == 1) {
                 view.setClickable(true);
                 etat.setImageDrawable(null);
             }
         }

final int n =i;


view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (list.get(n).etat==1){
        Bundle bundle = new Bundle();
        bundle.putInt("idcat", id_cat);
        bundle.putInt("idlvl" ,  list.get(n).id);
        bundle.putString("mode","solo");

        Intent intent = new Intent(Main3ActivityNiv.this,Main3ActivityImage.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    }
});
            return view;
        }
    }


}
















//package com.example.fady.appcorp;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Main3ActivityNiv extends AppCompatActivity {
//    MyHelper db= new MyHelper(this);
//    int id_cat;
//    int imgNiv[] = {
//            R.drawable.level1,
//            R.drawable.level2,
//            R.drawable.level3,
//            R.drawable.level5,
//            R.drawable.level4,
//            R.drawable.level6,
//            R.drawable.level7,
//            R.drawable.level8,
//            R.drawable.level9,};
//
//
//    @Override
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3_niv);
//        ListView ls = (ListView) findViewById(R.id.listView3);
//        EditText nom=(EditText) findViewById(R.id.nom);
//        Bundle bundle = getIntent().getExtras();
//        id_cat=bundle.getInt("idcat");
//
//        Button BT =(Button) findViewById(R.id.AddDB);
//        BT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText nom=(EditText) findViewById(R.id.nom);
//                EditText nbrq=(EditText) findViewById(R.id.nbrq);
//                EditText categorie=(EditText) findViewById(R.id.categorie);
//                EditText icone=(EditText) findViewById(R.id.icon);
//                Level C;
//
//                C = new Level(db.getAllLevels().size() , nom.getText().toString() , Integer.parseInt(nbrq.getText().toString()) ,null , Integer.parseInt(categorie.getText().toString()), imgNiv[db.getAllLevels().size()]);
//                db.createLevel(C);
//
//                nom.setText("");
//                nbrq.setText("");
//                categorie.setText("last lvl is "  );
//            }
//        });
/////////////-------------------------------------------------------------
//        ArrayList<ListItems> listA = new ArrayList<ListItems>();
//
//        String[]names = getResources().getStringArray(R.array.Niveau);
//        List<Level> listLVL = new ArrayList<>();
//
//        int id=0;
//        int im=0;
//        if (db.getAllLevels().size()==0){
//            for ( int C=0 ; C<5 ; C++) {
//                for(String i : names) {
//
//                    Level L = new Level (id,i,5,null, C , imgNiv[im]);
//                    listLVL.add(L);
//                    db.createLevel(listLVL.get(id));
//
//                    im++;                id++;
//                }
//                im=0;}}
//
//
//
////////////////////////////////////////////////////////////////////
//
//
//
//
//        int i = 0;
//        while (i<db.getAllLevels().size()){
//            Level L = db.getAllLevels().get(i);
//            if (L.getCat()== id_cat){
//                listA.add(new ListItems(L.idLevel,L.icone,L.getNomLevel()+" catÃ©gorie " + L.getCat()));
//
//            }
//            i++;}
//////////////////------------------------------------------------------//////////////////
//
//        Main3ActivityNiv.ListAdapter listAdapter = new Main3ActivityNiv.ListAdapter(listA);
//        ls.setAdapter(listAdapter);
//
//
//    }
//
//    class ListAdapter extends BaseAdapter {
//        ArrayList<ListItems> list = new ArrayList<ListItems>();
//
//        ListAdapter(ArrayList<ListItems> list2) {
//            this.list = list2;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return list.get(position).name;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int i, View convertView, ViewGroup parent) {
//            LayoutInflater layoutInflater = getLayoutInflater();
//            View view = layoutInflater.inflate(R.layout.niv_items, null);
//            TextView Title = (TextView) view.findViewById(R.id.textView2);
//            //  TextView txtid = (TextView) view.findViewById(R.id.textView_id);
//            ImageView img = (ImageView) view.findViewById(R.id.imageView2);
//            TextView sn=(TextView) view.findViewById(R.id.sn);
//            db.updatesn(id_cat,list.get(i).id);
//            //db.setsn(list.get(i).img,list.get(i).id,db.getsn(id_cat,list.get(i).id));
//            sn.setText("Score lvl "+db.getsn(id_cat,list.get(i).id));
//
//            Title.setText(list.get(i).name);
//            // txtid.setText(String.valueOf(list.get(i).id));
//            img.setImageResource(list.get(i).img);
//            final int n = i;
//            /*Button btn = (Button) view.findViewById(R.id.button2);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(), n + " - btn", Toast.LENGTH_SHORT).show();
//                }
//            });*/
//
//
//            img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("idcat", id_cat);
//                    bundle.putInt("idlvl" , list.get(n).id);
//                    Intent intent = new Intent(Main3ActivityNiv.this,Main3ActivityImg.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
//
//            Title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("idcat", id_cat);
//                    bundle.putInt("idlvl" ,  list.get(n).id);
//
//                    Intent intent = new Intent(Main3ActivityNiv.this,Main3ActivityImg.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
//
//
//            return view;
//        }
//    }
//
//
//}
