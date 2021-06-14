package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.DownloadTask;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

public class Gid extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton fabMain, fabOne, fabTwo, day, night;
    Float translationY = 100f;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    private static final String TAG = "Gid";
    Boolean isMenuOpen = false;
    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView download, daytext, nighttext, share;
    String dwnload_file_path =
            "https://gofile.io/d/lvkNWa";
    PDFView pdfView;
    File file;
    String string;
    String stringname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_gid);

        pdfView = findViewById(R.id.pdfView);
       string = Environment.getExternalStorageDirectory().getAbsolutePath();
        stringname = "NamozvaQur_on/" + "prewdialog.pdf";

        file = new File(string + File.separator + stringname);


        initFabMenu();

        try {

            pdfView.fromAsset("prewdialog.pdf")
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(false)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true)
                    .spacing(0)
                    .load();
        } catch (Exception e) {
            Toast.makeText(Gid.this, "Kerakli documentlar yo'q", Toast.LENGTH_LONG).show();

        }
    }

    private void initFabMenu() {
        fabMain = findViewById(R.id.fabMain);
        fabOne = findViewById(R.id.share);
        fabTwo = findViewById(R.id.dowload);
        day = findViewById(R.id.themeday);
        night = findViewById(R.id.themenight);
        share = findViewById(R.id.sharetext);
        download = findViewById(R.id.dowloadtext);
        daytext = findViewById(R.id.themedaytext);
        nighttext = findViewById(R.id.themenighttext);


        fabOne.setAlpha(0f);
        fabTwo.setAlpha(0f);
        day.setAlpha(0f);
        night.setAlpha(0f);
        share.setAlpha(0f);
        daytext.setAlpha(0f);
        nighttext.setAlpha(0f);
        download.setAlpha(0f);


        fabOne.setTranslationY(translationY);
        fabTwo.setTranslationY(translationY);
        day.setTranslationY(translationY);
        night.setTranslationY(translationY);
        daytext.setTranslationY(translationY);
        nighttext.setTranslationY(translationY);
        share.setTranslationY(translationY);
        download.setTranslationY(translationY);

        fabMain.setOnClickListener(this);
        fabOne.setOnClickListener(this);
        fabTwo.setOnClickListener(this);
        day.setOnClickListener(this);
        night.setOnClickListener(this);
        daytext.setOnClickListener(this);
        nighttext.setOnClickListener(this);
        share.setOnClickListener(this);
        download.setOnClickListener(this);



    }

    private void openMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

        fabOne.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        day.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        night.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        daytext.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        nighttext.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        share.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        download.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();


    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        day.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        night.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        daytext.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        nighttext.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        download.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        share.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

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
                handleFabOne();
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.dowload:
                new DownloadTask(this, dwnload_file_path);
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.themeday:
                pdfView.setNightMode(false);

                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.themenight:
                pdfView.setNightMode(true);
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;

        }
    }
}