package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;


import java.io.File;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.crash.FirebaseCrash;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.DownloadTask;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.SuraListen.RecyclerViewActivity;

public class ActivityQuranUz extends Activity implements View.OnClickListener {

    FloatingActionButton fabMain, fabOne, fabTwo, day, night, pageone, pagetwo;
    Float translationY = 100f;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    private static final String TAG = "Tasbih";
    Boolean isMenuOpen = false;
    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;
    String dwnload_file_path;
    PDFView pdfView;
    File file;
    String string;
    String stringname;
    public static boolean reloadQOnResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        reloadQOnResume = false;
        setContentView(R.layout.activity_quran_ar);
        BottomNavigationView bottomNav = findViewById(R.id.bottom);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        pdfView = findViewById(R.id.pdfView);
        dwnload_file_path =
                getString(R.string.url_kitob_uchun);

        string = Environment.getExternalStorageDirectory().getAbsolutePath();
        stringname = "NamozvaQur_on/" + getString(R.string.url_kitob_nomi);

        file = new File(string + File.separator + stringname);


        String string1 = Environment.getExternalStorageDirectory().getAbsolutePath();
        String stringname1 = "NamozvaQur_on/" + getString(R.string.url_kitob_nomi);

        File file1 = new File(string1 + File.separator + stringname1);
        if (file1.exists()) {

        } else {
            String dwnload_file_path =
                    getString(R.string.url_kitob_uchun);
            ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.Theme_AppCompat_Dialog_Alert);
            final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ctw);
            alertDialogBuilder.setTitle(R.string.Ducument);
            alertDialogBuilder.setMessage(R.string.keraklidoclaryo);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new DownloadTask(ActivityQuranUz.this, dwnload_file_path);
                }
            });
            alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alertDialogBuilder.show();

        }
        initFabMenu();

        try {

            pdfView.fromFile(file)
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
            Toast.makeText(ActivityQuranUz.this, "Kerakli documentlar yo'q", Toast.LENGTH_LONG).show();
            FirebaseCrash.report(e); // Generate report
        }
        try {
            MobileAds.initialize(this, "ca-app-pub-3311803693724764~9440687858");
            AdView adView = (AdView) findViewById(R.id.adView);
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
                //    Toast.makeText(getApplicationContext(), "Ad is loaded!",
                //            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdClosed() {
                  //  Toast.makeText(getApplicationContext(), "Ad is closed!",
                  //          Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                  //  Toast.makeText(getApplicationContext(), "Error" + i, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "ErrorAd" + i);
                }

                @Override
                public void onAdImpression() {
                 //   Toast.makeText(getApplicationContext(), "Ad left application!",
                    //        Toast.LENGTH_SHORT).show();
                }//

                @Override
                public void onAdOpened() {
                 //   Toast.makeText(getApplicationContext(), "Ad is opened!",
                    //        Toast.LENGTH_SHORT).show();
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

        } catch (Exception e) {
            Toast.makeText(this, "Error1 e" + e, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ErrorAd1~" + e);
            FirebaseCrash.report(e); // Generate report
        } catch (ExceptionInInitializerError error) {
            Toast.makeText(this, "Error2 e" + error, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ErrorAd2~" + error);
            FirebaseCrash.report(error); // Generate report
        } catch (Error error) {
            Toast.makeText(this, "Error3 e" + error, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ErrorAd3~" + error);
            FirebaseCrash.report(error); // Generate report
        }


    }


    private void initFabMenu() {
        fabMain = findViewById(R.id.fabMain);
        fabOne = findViewById(R.id.share);
        fabTwo = findViewById(R.id.dowload);
        day = findViewById(R.id.themeday);
        night = findViewById(R.id.themenight);
        pageone = findViewById(R.id.page1);
        pagetwo = findViewById(R.id.page2);


        fabOne.setAlpha(0f);
        fabTwo.setAlpha(0f);
        day.setAlpha(0f);
        night.setAlpha(0f);
        pageone.setAlpha(0f);
        pagetwo.setAlpha(0f);


        fabOne.setTranslationY(translationY);
        fabTwo.setTranslationY(translationY);
        day.setTranslationY(translationY);
        night.setTranslationY(translationY);
        pageone.setTranslationY(translationY);
        pagetwo.setTranslationY(translationY);

        fabMain.setOnClickListener(this);
        fabOne.setOnClickListener(this);
        fabTwo.setOnClickListener(this);
        day.setOnClickListener(this);
        night.setOnClickListener(this);
        pageone.setOnClickListener(this);
        pagetwo.setOnClickListener(this);

    }

    private void openMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

        fabOne.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        day.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        night.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        pageone.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        pagetwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();


    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        day.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        night.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        pageone.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        pagetwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();


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
                String string2 = "https://play.google.com/store/apps/details?id=" + appPackageName;
                String string3 = getString(R.string.send__________send) + "\n" + string1 + "\n\nGoogle Play => " + string2;
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
                new DownloadTask(ActivityQuranUz.this, dwnload_file_path);
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.themeday:
                pdfView.setNightMode(false);
                reloadQOnResume = true;
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.themenight:
                reloadQOnResume = true;
                pdfView.setNightMode(true);
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.page1:
                Log.i("MyLog", "click1");
                break;
            case R.id.page2:
                Log.i("MyLog", "click2");
                break;
        }
    }

    public void next(View v){

        pdfView.jumpTo(pdfView.getCurrentPage()+1);//getCurrentPage method will
        //return current page in int
        reloadQOnResume = true;

    }

    public void backpage(View v){
        pdfView.jumpTo(pdfView.getCurrentPage()-1);
        reloadQOnResume = true;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        startActivity(new Intent(ActivityQuranUz.this, ActivityQuranAr.class));
                        break;
                    case R.id.navigation_dashboard:
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(ActivityQuranUz.this, RecyclerViewActivity.class));
                        break;
                }
                return true;
            };

    @Override
    protected void onResume() {
        if (reloadQOnResume){
            reloadQOnResume = false;
            recreate();
        }
        super.onResume();
    }
}



