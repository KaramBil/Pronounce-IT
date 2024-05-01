package com.example.fady.BILAMI;
import android.app.Dialog;
import android.content.Context;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

import android.widget.ListView;

import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {
    private FeatureCoverFlow coverFlow;
    private ListCatAdapter listAdapter;
    private TextSwitcher mTitle;
ProgressBar PG;
    MyHelper db= new MyHelper(this);

    private static final String PREF_JETON = "JETON";
    SharedPreferences sharedPreferences2,sharedPreferences3;
String LANGUE;
    MediaPlayer mp;
    SharedPreferences sharedPreferences;

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coverflow);

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
        Button Pause = (Button)findViewById(R.id.CatSetting);
        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Main2Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.pause);
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
                        Intent intent = new Intent(Main2Activity.this,MainActivity.class);
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
        sharedPreferences = getBaseContext().getSharedPreferences(PREF_JETON, MODE_PRIVATE);
        if (!sharedPreferences.contains("JETON")) {
            sharedPreferences
                    .edit()
                    .putInt("JETON",85)
                    .apply();
        }
        ///////// progress bar

        ////////////

        Button jetonbutton=(Button) findViewById(R.id.jetonimgct);
        ListView ls = (ListView) findViewById(R.id.listView2);
        String[]names = getResources().getStringArray(R.array.Names);
//        int id=0;
//        if(db.getAllCategories().size()==0) {
//            for (String i : names) {
//                Category C = new Category(id, imgCat[id], i, 8, null);
//                db.createCategory(C);
//                id++;
//            }
//        }
        final ArrayList<ListItems> listA = new ArrayList<ListItems>();
        int i = 0;
        while (i<db.getAllCategories().size()){
            Category C =db.getAllCategories().get(i);
            listA.add(new ListItems(C.idCat ,C.icone , C.nomCat));
            i++;
        }



        //   ls.setAdapter(movieAdapter);



        listAdapter = new ListCatAdapter(listA,this);
        coverFlow=(FeatureCoverFlow)findViewById(R.id.coverFlow);
        coverFlow.setShouldRepeat(false);
        coverFlow.setAdapter(listAdapter);

        coverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("idcat", db.getAllCategories().get(position).getIdCat());
                Intent intent1 = new Intent(Main2Activity.this,Main3ActivityNiv.class);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
        coverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(listA.get(position).name);
            }

            @Override
            public void onScrolling() {

            }
        });

        mTitle = (TextSwitcher)findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater=LayoutInflater.from(Main2Activity.this);
                TextView txt= (TextView)inflater.inflate(R.layout.layout_title,null);
                return  txt;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this,R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this,R.anim.slide_in_bottom);

        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);
}

    @Override
    protected void onResume() {
        TextView jetonnbr = (TextView)findViewById(R.id.jetonnbrct);

        super.onResume();
        jetonnbr.setText("" + sharedPreferences.getInt("JETON", 0));
    }


    public class ListCatAdapter extends BaseAdapter {
        private List<ListItems> movieList;
        private Context mContext;

        public ListCatAdapter(List<ListItems> movieList, Context mContext) {
            this.movieList = movieList;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return movieList.size();
        }

        @Override
        public Object getItem(int position) {
            return movieList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            View rowView = view;
            if(rowView== null){
                coverFlow.setShouldRepeat(false);
                rowView = LayoutInflater.from(mContext).inflate(R.layout.layout_item,null);
                TextView name = (TextView)rowView.findViewById(R.id.label);
                ImageView image =(ImageView) rowView.findViewById(R.id.image);
                ProgressBar PG = (ProgressBar)rowView.findViewById(R.id.progressBar);
                PG.setProgress((int) db.getsc(movieList.get(position).id,LANGUE));

                Picasso.with(mContext).load(movieList.get(position).img).into(image);
                name.setText(movieList.get(position).name);


            }
            return rowView;
        }
    }


    /*  coverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener(){
          public  void onScrolledToPosition(int position){
              mTitle.setText(movieList.get(position).getName());
          }
      })};
  */




//////////////////////////////////////////////////////////////////////////////////

/*
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
            View view = layoutInflater.inflate(R.layout.cat_items, null);
            TextView Title = (TextView) view.findViewById(R.id.textView2);
            //TextView txtid = (TextView) view.findViewById(R.id.textView_id);
            ImageView img = (ImageView) view.findViewById(R.id.imageView2);
TextView sc = (TextView) view.findViewById(R.id.sc);
            db.setsc(list.get(i).img,db.getsc(list.get(i).id));
            Title.setText(list.get(i).name);
            sc.setText("le score" +db.getsc(list.get(i).id));
            //txtid.setText(String.valueOf(list.get(i).id));
            img.setImageResource(list.get(i).img);
            final int n = i;
            /*Button btn = (Button) view.findViewById(R.id.button2);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), n + " - btn", Toast.LENGTH_SHORT).show();
                }
            });


            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idcat", db.getAllCategories().get(n).getIdCat());
                    Intent intent = new Intent(Main2Activity.this,Main3ActivityNiv.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            Title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idcat", db.getAllCategories().get(n).getIdCat());
                    Intent intent = new Intent(Main2Activity.this,Main3ActivityNiv.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

           /* ImageButton btn1 = (ImageButton) view.findViewById(R.id.imgButton);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent CH1 = new Intent(Main2Activity.this,Main3ActivityNiv.class);
                    startActivity(CH1);
                }
            });


            return view;
        }
    }
*/
//public void onBackPressed(){
//   finish();
//}

}
