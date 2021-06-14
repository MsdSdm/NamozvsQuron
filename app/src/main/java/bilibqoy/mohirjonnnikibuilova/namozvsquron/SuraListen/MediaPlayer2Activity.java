package bilibqoy.mohirjonnnikibuilova.namozvsquron.SuraListen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.io.SyncFailedException;
import java.util.concurrent.TimeUnit;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.DownloadTaskMedia;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

public class MediaPlayer2Activity extends AppCompatActivity {
    private static final String TAG = "MyLog";
    // DownloadManager downloadManager;
// DownloadManager downloadManager;

    private boolean isConnectedInternet;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private SeekBar seekbar;
    private TextView txtStartTime;
    private TextView txtEndTime;
    private Button btnPlay;
    private Button btnPause;
    private ImageView imageView1, imageView2, imageView3;
    private ImageButton imageButton;


    public static int oneTimeOnly = 0;
    String url = "http://server8.mp3quran.net/afs/";
    String number;
    String name;
    String url2;
    String buttonText;
    DatabaseSure databaseSure;
    boolean checkInternet = true;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _DisplaySET.setLanguagePreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player2);
        seekbar = findViewById(R.id.seekBar);
        SeekBar volumeBar = findViewById(R.id.volumeBar);
        seekbar.setClickable(false);

        txtStartTime = findViewById(R.id.startTime);
        txtEndTime = findViewById(R.id.endTime);
        TextView nameSurah = findViewById(R.id.nameSurah);
        imageView1 = findViewById(R.id.background);
        imageView2 = findViewById(R.id.sound2);
        imageView3 = findViewById(R.id.imageQuran);
        btnPlay = findViewById(R.id.button);
        btnPause = findViewById(R.id.button2);
        databaseSure = new DatabaseSure(getApplicationContext());
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setVolume(0.5f, 0.5f);
        number = getIntent().getStringExtra("number");
        name = getIntent().getStringExtra("name");
        nameSurah.setText(name);
        Button download = findViewById(R.id.dowload);
        buttonText = btnPlay.getText().toString();

        ((ImageView) findViewById(R.id.background)).setImageDrawable(getResources().getDrawable(
                _DisplaySET.getAppTheme(this) == _DisplaySET.WHITE ?R.drawable.backgroundui:R.drawable.background_black
        ));
        ((TextView) findViewById(R.id.startTime)).setTextColor(
                _DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ? Color.BLACK:Color.WHITE);
        ((TextView) findViewById(R.id.endTime)).setTextColor(
                _DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ? Color.BLACK:Color.WHITE);
        ((TextView) findViewById(R.id.nameSurah)).setTextColor(
                _DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ? Color.BLACK:Color.WHITE);

        try {
            if (Integer.parseInt(number) / 10 < 1) {
                url2 = url.concat("00").concat(number).concat(".mp3");
            } else if (Integer.parseInt(number) / 10 >= 1 && Integer.parseInt(number) / 10 < 10) {
                url2 = url.concat("0").concat(number).concat(".mp3");

            } else {
                url2 = url.concat(number).concat(".mp3");
            }
            mediaPlayer.setDataSource(url2);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (!mediaPlayer.isPlaying() && checkInternet) {
                        try {


                            Log.e("log", "play cliked");
                            //Toast.makeText(getApplicationContext(), "Sura", Toast.LENGTH_SHORT).show();
                            oneTimeOnly = 0;
                            btnPlay.setVisibility(View.GONE);
                            btnPause.setVisibility(View.VISIBLE);
                            mediaPlayer.start();
                            finalTime = mediaPlayer.getDuration();
                            startTime = mediaPlayer.getCurrentPosition();
                            if (oneTimeOnly == 0) {
                                seekbar.setMax((int) finalTime);
                                oneTimeOnly = 1;
                            }


                            txtStartTime.setText(String.format("%d M, %d S",
                                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                            );
                            txtEndTime.setText(String.format("%d M, %d S",
                                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            finalTime)))
                            );


                            seekbar.setProgress((int) startTime);
                            myHandler.postDelayed(UpdateSongTime, 100);

                        }catch (Exception e){
                            Log.e("TAG", "ERROR" + e);
                        }
                    }
                }
            });
            Log.e("log","Url path==" + url2);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("log", "Error " + e);
        } catch (Exception e){
            Log.e("log", "Error exc" + e);
        }catch (Error e){ Log.e("log", "Error er" + e);}




        btnPlay.setOnClickListener(v -> {
            isConnectedInternet = isNetworkAvailable();
            if (!isConnectedInternet) {
                Toast.makeText(getApplicationContext(), "Internet(Error)", Toast.LENGTH_SHORT).show();
                checkInternet = false;
            } else {
                checkInternet = true;

            }


        });

        btnPause.setOnClickListener(v -> {
            mediaPlayer.pause();
            btnPause.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);


        });


        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumeNum = progress / 100f;
                mediaPlayer.setVolume(volumeNum, volumeNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        download.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"Dowloading...",Toast.LENGTH_SHORT).show();
           // new DownloadTask(MediaPlayer2Activity.this, url2);
            new DownloadTaskMedia(MediaPlayer2Activity.this, url2);
        });


        try{
            MobileAds.initialize(this, "ca-app-pub-3311803693724764~9440687858");
            AdView adView = findViewById(R.id.adView);
            //adView.setAdSize(AdSize.BANNER);
            //adView.setAdUnitId("ca-app-pub-3311803693724764/5256417379");
            AdRequest adRequest1 = new AdRequest.Builder().build();
            //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            // Check the LogCat to get your test device ID
            //.addTestDevice("5E834B5036EAD368F2C0548034D35DEC")
            // .build();
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




    private Runnable UpdateSongTime = new Runnable() {
        @SuppressLint("DefaultLocale")
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            txtStartTime.setText(String.format("%d M, %d S",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
            if ((txtStartTime.getText()).equals(txtEndTime.getText())) {
                btnPause.setVisibility(View.GONE);
                btnPlay.setVisibility(View.VISIBLE);
            }
        }
    };

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();


    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }
}


