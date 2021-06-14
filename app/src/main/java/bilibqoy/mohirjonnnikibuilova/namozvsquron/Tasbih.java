package bilibqoy.mohirjonnnikibuilova.namozvsquron;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities.MainActivity;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;

public class Tasbih extends AppCompatActivity implements View.OnClickListener {
    private Button reset, pluspne, btn_save;
    private TextView displaytasbix;
    ImageView back;
    private int counter;
    ImageButton close;

    String savedText;
    private SharedPreferences preferences;
    final String string = "saved_text";
    MediaPlayer mediaPlayer ;//= MediaPlayer.create(this,R.raw.tasbix_ov);
    String s;

    FloatingActionButton fabMain, fabOne, fabTwo,  share_, save;
    Float translationY = 100f;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    private static final String TAG = "Tasbih";
    Boolean isMenuOpen = false;
    Format formatter;
    ArrayList<ExampleItem> mExampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ConstraintLayout constraintLayout;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.plusone:
                    plusone();
                    play();
                    break;


            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_tasbih);

        constraintLayout = findViewById(R.id.loayout);
        constraintLayout.setVisibility(View.INVISIBLE);

        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(Tasbih.this)
                        .setTitle(getString(R.string.remove))
                        .setTitle(getString(R.string.remove_tasbix))
                        .setIcon(R.drawable.ic_delete_black_24dp)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Tasbih.this, getString(R.string.removed_tasbix),Toast.LENGTH_LONG).show();
                                initCount();

                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                alertDialog.show();
            }
        });

        pluspne = findViewById(R.id.plusone);
        pluspne.setOnClickListener(clickListener);
       // more = findViewById(R.id.more);
        displaytasbix = findViewById(R.id.digitalwindow);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savatext();
                startActivityForResult(new Intent(Tasbih.this, MainActivity.class), 1000 );

            }
        });
        btn_save = findViewById(R.id.button_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                constraintLayout.setVisibility(View.INVISIBLE);
                pluspne.setVisibility(View.VISIBLE);

            }
        });
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                constraintLayout.setVisibility(View.INVISIBLE);
                pluspne.setVisibility(View.VISIBLE);
            }
        });



        initCount();
        loadText();
        initFabMenu();

        loadData();
        buildRecyclerView();
        setInsertButton();
try{
    MobileAds.initialize(this, "ca-app-pub-3311803693724764~9440687858");
    AdView adView = (AdView) findViewById(R.id.adView);

    AdRequest adRequest1 = new AdRequest.Builder().build();

    adView.loadAd(adRequest1);
    adView.setAdListener(new AdListener() {
        @Override
        public void onAdLoaded() {
            Toast.makeText(getApplicationContext(), "Ad is loaded!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdClosed() {
            Toast.makeText(getApplicationContext(), "Ad is closed!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdFailedToLoad(int i) {
            Toast.makeText(getApplicationContext(), "Error" + i, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ErrorAd" + i);
        }

        @Override
        public void onAdImpression() {
            Toast.makeText(getApplicationContext(), "Ad left application!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdOpened() {
            Toast.makeText(getApplicationContext(), "Ad is opened!",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }

        @Override
        public void onAdClicked() {
            super.onAdClicked();
        }

    });
    adView.loadAd(adRequest1);

}catch (Exception e){
    Toast.makeText(this, "Error1 e" + e , Toast.LENGTH_SHORT).show();
    Log.d(TAG, "ErrorAd1~" + e);
}catch (ExceptionInInitializerError error){
    Toast.makeText(this, "Error2 e" + error , Toast.LENGTH_SHORT).show();
    Log.d(TAG, "ErrorAd2~" + error);
}catch (Error error){
    Toast.makeText(this, "Error3 e" + error , Toast.LENGTH_SHORT).show();
    Log.d(TAG, "ErrorAd3~" + error);
}


    }





    private void loadText() {
        preferences = getPreferences(MODE_PRIVATE);
        savedText = preferences.getString(string, "");
        displaytasbix.setText(savedText);
    }

    private void initCount() {
        counter = 0;
        displaytasbix.setText( counter  + "");

    }

    private void savatext() {
        preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString(string, displaytasbix.getText().toString());
        ed.apply();

    }


    private void play() {
        mediaPlayer = MediaPlayer.create(this, R.raw.tasbix_ov);
        mediaPlayer.start();
    }

    private void plusone() {
        counter ++ ;
        displaytasbix.setText(counter + "");

    }




    @Override
    public void onBackPressed() {
            savatext();
            super.onBackPressed();

    }

    private void initFabMenu() {
        fabMain = findViewById(R.id.fabMain);
        fabOne = findViewById(R.id.fabOne);
        fabTwo = findViewById(R.id.fabTwo);
        share_ = findViewById(R.id.share);
        save = findViewById(R.id.save);



        fabOne.setAlpha(0f);
        fabTwo.setAlpha(0f);
        share_.setAlpha(0f);
        save.setAlpha(0f);

        fabOne.setTranslationY(translationY);
        fabTwo.setTranslationY(translationY);
        share_.setTranslationY(translationY);
        save.setTranslationY(translationY);

        fabMain.setOnClickListener(this);
        fabOne.setOnClickListener(this);
        fabTwo.setOnClickListener(this);
        share_.setOnClickListener(this);
        save.setOnClickListener(this);

    } private void openMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

        fabOne.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        share_.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        save.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();



    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        share_.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        save.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();


    }

    private void handleFabOne() {
        Log.i(TAG, "handleFabOne: ");
    }


    @Override
    public void onClick(View view) {



        switch (view.getId()) {
            case R.id.fabMain:
                Log.i("MyLog", "onClick: fab main_menu");
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.fabOne:
                //Log.i(TAG, "onClick: fab one");
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 100);
                handleFabOne();
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.fabTwo:
                //Log.i(TAG, "onClick: fab two");
                AudioManager audioManager1 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager1.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.share:
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                String string1 = "market://details?id=" + appPackageName;
                String string2 =  "https://play.google.com/store/apps/details?id=" + appPackageName;
                String string3 = getString(R.string.send__________send) +"\n" + string1 + "\n\nGoogle Play => " + string2;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, string3);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;

            case R.id.save:
                constraintLayout.setVisibility(View.VISIBLE);
                pluspne.setVisibility(View.INVISIBLE);
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;

        }

    }
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("tasbex", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mExampleList);
        editor.putString("tasbexlar", json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("tasbex", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("tasbexlar", null);
        Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
        mExampleList = gson.fromJson(json, type);
        if (mExampleList == null) {
            mExampleList = new ArrayList<>();
        }
    }
    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void setInsertButton() {
        //msgStr.append(formatter.format(new Date()));
        formatter = new SimpleDateFormat("yyyy:mm:dd:hh:mm a");
        Button buttonInsert = findViewById(R.id.button_save);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText line1 = findViewById(R.id.edittext_line_1);
                EditText line2 = findViewById(R.id.edittext_line_2);
                String s = line1.getText().toString() + "   ("+formatter.format(new Date())+")";
                insertItem(s, line2.getText().toString());
            }
        });
    }
    private void insertItem(String line1, String line2) {
        mExampleList.add(new ExampleItem(line1, line2));
        mAdapter.notifyItemInserted(mExampleList.size());
    }
}
/////////*/
