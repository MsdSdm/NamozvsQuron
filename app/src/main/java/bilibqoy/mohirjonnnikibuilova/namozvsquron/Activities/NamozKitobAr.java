package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.crash.FirebaseCrash;

import java.io.File;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.DownloadTask;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

public class NamozKitobAr extends Activity implements View.OnClickListener{

    FloatingActionButton fabMain, fabOne, fabTwo, day, night;
    Float translationY = 100f;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    private static final String TAG = "NamozKitobUz";
    Boolean isMenuOpen = false;
    ProgressBar pb;
    Dialog dialog;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView download, daytext, nighttext, share;
    String dwnload_file_path =
            "https://raw.githubusercontent.com/MsdSdm/kitoblar/main/1E07BB6882576B74B745CAEE437AA68E_%D8%A8%D8%A7%D8%A8_%D8%B5%D9%84%D8%A7%D8%A9_%D8%A7%D9%84%D8%AC%D9%85%D8%A7%D8%B9%D8%A9_%D9%88%D8%A7%D9%84%D8%A5%D9%85%D8%A7%D9%85%D8%A9.pdf";
    PDFView pdfView;
    File file;
    String string;
    String stringname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_namoz_kitob_uz);

        pdfView = findViewById(R.id.pdfView);
//1E07BB6882576B74B745CAEE437AA68E_%D8%A8%D8%A7%D8%A8_%D8%B5%D9%84%D8%A7%D8%A9_%D8%A7%D9%84%D8%AC%D9%85%D8%A7%D8%B9%D8%A9_%D9%88%D8%A7%D9%84%D8%A5%D9%85%D8%A7%D9%85%D8%A9.pdf
        string = Environment.getExternalStorageDirectory().getAbsolutePath();
        stringname = "NamozvaQur_on/" + "1E07BB6882576B74B745CAEE437AA68E_%D8%A8%D8%A7%D8%A8_%D8%B5%D9%84%D8%A7%D8%A9_%D8%A7%D9%84%D8%AC%D9%85%D8%A7%D8%B9%D8%A9_%D9%88%D8%A7%D9%84%D8%A5%D9%85%D8%A7%D9%85%D8%A9.pdf";

        file = new File(string + File.separator + stringname);


        String string2 = Environment.getExternalStorageDirectory().getAbsolutePath();
        String stringname2 = "NamozvaQur_on/" + "1E07BB6882576B74B745CAEE437AA68E_%D8%A8%D8%A7%D8%A8_%D8%B5%D9%84%D8%A7%D8%A9_%D8%A7%D9%84%D8%AC%D9%85%D8%A7%D8%B9%D8%A9_%D9%88%D8%A7%D9%84%D8%A5%D9%85%D8%A7%D9%85%D8%A9.pdf";


        File file2 = new File(string2 + File.separator + stringname2);
        if(file2.exists()){

        }else{
            ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.Theme_AppCompat_Dialog_Alert);
            final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ctw);
            alertDialogBuilder.setTitle(R.string.Ducument);
            alertDialogBuilder.setMessage(R.string.keraklidoclaryo);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String dwnload_file_path2 = "https://raw.githubusercontent.com/MsdSdm/kitoblar/main/1E07BB6882576B74B745CAEE437AA68E_%D8%A8%D8%A7%D8%A8_%D8%B5%D9%84%D8%A7%D8%A9_%D8%A7%D9%84%D8%AC%D9%85%D8%A7%D8%B9%D8%A9_%D9%88%D8%A7%D9%84%D8%A5%D9%85%D8%A7%D9%85%D8%A9.pdf";
                    new DownloadTask(NamozKitobAr.this, dwnload_file_path2);
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
            Toast.makeText(NamozKitobAr.this, "Kerakli documentlar yo'q", Toast.LENGTH_LONG).show();
            FirebaseCrash.report(e); // Generate report
        }
        try{
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
                 //   Toast.makeText(getApplicationContext(), "Ad is loaded!",
                 //           Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdClosed() {
                 //   Toast.makeText(getApplicationContext(), "Ad is closed!",
                   //         Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                   // Toast.makeText(getApplicationContext(), "Error" + i, Toast.LENGTH_SHORT).show();
                   // Log.d(TAG, "ErrorAd" + i);
                }

                @Override
                public void onAdImpression() {
                  //  Toast.makeText(getApplicationContext(), "Ad left application!",
                  //          Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdOpened() {
                  //  Toast.makeText(getApplicationContext(), "Ad is opened!",
                   //         Toast.LENGTH_SHORT).show();
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
            FirebaseCrash.report(e); // Generate report
        }catch (ExceptionInInitializerError error){
            Toast.makeText(this, "Error2 e" + error , Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ErrorAd2~" + error);
            FirebaseCrash.report(error); // Generate report
        }catch (Error error){
            Toast.makeText(this, "Error3 e" + error , Toast.LENGTH_SHORT).show();
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
