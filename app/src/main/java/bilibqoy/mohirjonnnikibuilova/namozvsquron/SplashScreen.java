package bilibqoy.mohirjonnnikibuilova.namozvsquron;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities.MainActivity;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;

public class SplashScreen extends AppCompatActivity {

    private TextView txtProgress;
    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_splash_screen2);

        ((ImageView) findViewById(R.id.background)).setImageDrawable(getResources().getDrawable(
                _DisplaySET.getAppTheme(this) == _DisplaySET.WHITE ?R.color.widgetWhite:R.color.widgetBlack
        ));

        txtProgress =  findViewById(R.id.txtProgress);
        progressBar =  findViewById(R.id.progressBar);

        new Thread(() -> {
            while (pStatus <= 100) {
                handler.post(() -> {
                    progressBar.setProgress(pStatus);
                    txtProgress.setText(pStatus + " %");
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pStatus++;
            }
        }).start();



        Thread logoTimer = new Thread()
        {
            public void run()
            {
                try
                {
                    int logoTimer = 0;
                    while(logoTimer < 5000)
                    {
                        sleep(100);
                        logoTimer = logoTimer +100;
                    }
                   // if (!checkPermission()) {
                        openActivity();
                }
                catch (InterruptedException e)
                {
                    // TODO: автоматически сгенерированный блок catch.
                    e.printStackTrace();
                }
                finally
                {
                    finish();
                }
            }
        };
        logoTimer.start();
}
    //*********************************************

    private void openActivity() {
        startActivityForResult(new Intent(this, MainActivity.class), 10000);
        //add your further process after giving permission or to download images from remote server.
    }
}
