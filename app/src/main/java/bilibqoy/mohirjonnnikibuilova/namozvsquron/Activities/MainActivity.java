package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.IslamicCalendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms.AlarmsScheduler;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms.Notification;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms._AlarmSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations._LocationSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Qibla.CompassActivity;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Qibla.GPSTracker;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.SuraListen.RecyclerViewActivity;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Tasbih;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Times._TimesSET;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;

import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

import com.google.android.play.core.tasks.Task;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;
import static com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String WRITE_EXTERNAL_STORAGE ="android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String READ_EXTERNAL_STORAGE ="android.permission.READ_EXTERNAL_STORAGE" ;
    private static final String TAG = "MyLog";
    public static boolean reloadMainActivityOnResume = false;
    private int upComingTimePoint;
    private Handler handler;
    private Runnable runnable;
    private DrawerLayout drawer;
    public InterstitialAd interstitialAd;
    private AppUpdateManager appUpdateManager;
    private static final int MY_REQUEST_CODE = 17326;
    JSONObject data = null;
    JSONObject main;
    JSONObject weather;
    TextView celcius, countryn;
    String s;
    String weatherci;
    String imgUrl,currentLoc;
    double latitude, longitude;
    ImageView weathercond;
    URL url;
    GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*      Initialization Methods      */
        _LocationSET.checkCurrentLocation(this);
        _AlarmSET.setPowerConceptions(this);
        _AlarmSET.setAlarmPermissions(this);
        _TimesSET.updateTimes(this);
        _DisplaySET.setLanguagePreferences(this);
        reloadMainActivityOnResume = false;
        /*              ********            */

        setContentView(R.layout.activity_main);
        //NotificationEventReceiver.setupAlarm(getApplicationContext());
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setBackgroundColor(getResources().getColor(_DisplaySET.getAppTheme(this)== _DisplaySET.WHITE ?
             //   R.color.colorPrimaryWhite:R.color.colorPrimaryBlack));
       // setSupportActionBar(toolbar);
        Button button = (Button)findViewById(R.id.toggle);
        drawer = findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //        this, drawer, button, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view);
       // navigationView.getHeaderView(0).setBackground(getResources().getDrawable(_DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ?
       //         R.drawable.side_nav_bar:R.drawable.side_nav_bar_black));
       // navigationView.setBackgroundColor(getResources().getColor(_DisplaySET.getAppTheme(this)== _DisplaySET.WHITE ?
        //        R.color.item_bg_white:R.color.item_bg_black
      //          ));
      //  navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(_DisplaySET.getAppTheme(this)== _DisplaySET.WHITE ?
       //         R.color.widgetBlack:R.color.widgetWhite
       //         )));
      //  navigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(_DisplaySET.getAppTheme(this)== _DisplaySET.WHITE ?
      //          R.color.widgetBlack:R.color.widgetWhite
       // )));
        navigationView.setNavigationItemSelectedListener(this);


        upComingTimePoint = _TimesSET.comingTimePointIndex();
        if(_TimesSET.isItGoogleCalendar(this)) initializeDateViewsWithGoogleApi();
        else initializeDateViews();
        initializeTimePoints();
        initializeTheme();
        startTimer();
        AlarmsScheduler.fire(this,Calendar.getInstance()); //set Alarms


       // TextView textView = findViewById(R.id.textView);
       // textView.setText(FirebaseMessageReceiver.getValue());
       // if (FirebaseMessageReceiver.getValue() == null){
        //    textView.setVisibility(View.INVISIBLE);
        //}

        FirebaseCrash.report(new Exception("exception"));



        //FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
         //   String newToken = instanceIdResult.getToken();
         //   Log.e("newToken", newToken);
        //});

        checkUpdate();
        NotificationAlaram();

       //09-30 14:33:09.305 6656-6656/bilibqoy.mohirjonnnikibuilova.namozvsquron I/device id=: 5E834B5036EAD368F2C0548034D35DEC
try {
    @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
    String deviceId = md5(android_id).toUpperCase();
    Log.i("device id=",deviceId);
    MobileAds.initialize(this, "ca-app-pub-3311803693724764~9440687858");//in ad==ca-app-pub-3311803693724764/8181315184
    interstitialAd = new InterstitialAd(this);
    interstitialAd.setAdUnitId("ca-app-pub-3311803693724764/8181315184");
    AdRequest adRequest = new AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .addTestDevice("5E834B5036EAD368F2C0548034D35DEC")
            .build();
    interstitialAd.loadAd(adRequest);
    if (interstitialAd.isLoaded()){
        interstitialAd.show();
    }else {

    }


    AdView adView = (AdView) findViewById(R.id.adView);
    //adView.setAdSize(AdSize.BANNER);
    //adView.setAdUnitId("ca-app-pub-3311803693724764/5256417379");
    AdRequest adRequest1 = new AdRequest.Builder()
           //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            // Check the LogCat to get your test device ID
           //.addTestDevice("5E834B5036EAD368F2C0548034D35DEC")
            .build();

    adView.loadAd(adRequest1);
    adView.setAdListener(new AdListener() {
        @Override
        public void onAdLoaded() {
            //Toast.makeText(getApplicationContext(), "Ad is loaded!",
            //        Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdClosed() {
           // Toast.makeText(getApplicationContext(), "Ad is closed!",
           //         Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdFailedToLoad(int i) {
           // Toast.makeText(getApplicationContext(), "Error" + i, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ErrorAd" + i);
        }

        @Override
        public void onAdImpression() {
           // Toast.makeText(getApplicationContext(), "Ad left application!",
           //         Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdOpened() {
          //  Toast.makeText(getApplicationContext(), "Ad is opened!",
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






        /*weather*/
        imgUrl = "http://openweathermap.org/img/wn/";
        currentLoc = getSharedPreferences(_LocationSET.currentLocation,MODE_PRIVATE).getString("location_name","");
        celcius = findViewById(R.id.Celsius);
        countryn = findViewById(R.id.CountryN);
        weathercond = findViewById(R.id.weather_state);
        countryn.setText(currentLoc);

        getJSON(currentLoc);
        //lo = getSharedPreferences(tempLocationFile, MODE_PRIVATE).getFloat("longitude", -99999);
       // la = getSharedPreferences(tempLocationFile, MODE_PRIVATE).getFloat("latitude", -99999);
       //// lo = lo.length()>11?lo.substring(0,11):lo;
        //la = la.length()>11?la.substring(0,11):la;
        gps = new GPSTracker(this);
        if(gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else {
            gps.showSettingsAlert();
        }
        Log.d("LatidudeAnd Long",latitude+ " "+ longitude);
        if(!countryn.getText().toString().matches("")) {
        }else {
            countryn.setText("Lat: " + latitude + " Long: " + longitude);
        }
    }

    public void getJSON(final String city) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=c23788b3d84ecc27e88830b79c164622&units=metric");
                    Log.d("URLW", String.valueOf(url));

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());
                    main = data.getJSONObject("main");
                    weather = data.getJSONArray("weather").getJSONObject(0);
                    weatherci = weather.getString("icon");
                    s = main.getString("temp");
                    imgUrl = imgUrl + weatherci + ".png";
                    celcius.setText(s + "°C");
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(imgUrl).getContent());
                        weathercond.setImageBitmap(bitmap);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   // s = data.getString("temp");
                    if(data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }


                } catch (Exception e) {

                    System.out.println("Exception "+ e.getMessage());
                    return null;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                    celcius.setText(s + "°C");
            }
        }.execute();

    }

    private void initializeTheme() {
      //  ((ImageView) findViewById(R.id.background)).setImageDrawable(getResources().getDrawable(
            //    _DisplaySET.getAppTheme(this) == _DisplaySET.WHITE ?R.drawable.backgroundui:R.drawable.background_black
      //  ));
        //((TextView) findViewById(R.id.main_activity_day_of_week)).setTextColor(
          //  _DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ? Color.WHITE:Color.WHITE);
       // ((TextView) findViewById(R.id.main_activity_hijri_month_number)).setTextColor(
        //        _DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ? Color.BLACK:Color.WHITE);
      //  ((TextView) findViewById(R.id.main_activity_hijri_month_name)).setTextColor(
        //        _DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ? Color.BLACK:Color.WHITE);
      //  ((TextView) findViewById(R.id.main_activity_gregorian_month_number)).setTextColor(
        //        _DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ? Color.BLACK:Color.WHITE);
        //((TextView) findViewById(R.id.main_activity_gregorian_month_name)).setTextColor(
        //        _DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ? Color.BLACK:Color.WHITE);
        //if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
         //   ((ProgressBar)findViewById(R.id.progress_bar_landscape)).setProgressDrawable(getResources().getDrawable(
           //         _DisplaySET.getAppTheme(this) == _DisplaySET.WHITE ?R.drawable.circle:R.drawable.circle_black
           // ));
        //    ViewGroup group = findViewById(R.id.main_activity_timer);
         //   for(int i=0;i<group.getChildCount();i++){
          //      if(group.getChildAt(i) instanceof TextView) ((TextView) group.getChildAt(i)).setVisibility(View.VISIBLE);//pastdagi kod edi
                //.setTextColor(
                        //_DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ?getResources().getColor(R.color.widgetColor):Color.WHITE
               // );
            }
        //}
    //}


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            startActivityForResult(new Intent(this,SettingsActivity.class),10000);
        } else if (id == R.id.nav_convert_date) {
            try {
                if (_TimesSET.isItGoogleCalendar(this))
                    startActivityForResult(new Intent(this, ConvertDateGoogleApiActivity.class), 10000);
                else
                    startActivityForResult(new Intent(this, ConvertDateActivity.class), 10000);
            }catch (Exception e){
                Intent intent = new Intent(Intent.ACTION_APP_ERROR);
                startActivity(intent);
                FirebaseCrash.report(e); // Generate report
            }

        }  else if (id == R.id.nav_location) {
            startActivityForResult(new Intent(this, LocationsActivity.class),10000);
        } else if (id == R.id.nab_remembrance) {
            startActivityForResult(new Intent(this, AzkarActivity.class),10000);
        }else if (id == R.id.share){
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            String string1 = "market://details?id=" + appPackageName;
            String string2 =  "https://play.google.com/store/apps/details?id=" + appPackageName;
            String string3 = getString(R.string.send__________send) +"\n" + string1 + "\n\nGoogle Play => " + string2;
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, string3);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        else if (id == R.id.tasbix){
                startActivityForResult(new Intent(this, Tasbih.class), 10000);


        }
        else if (id == R.id.quran){

            if (!checkPermission()) {
                openActivity();
            } else {
                if (checkPermission()) {
                    requestPermissionAndContinue();
                } else {
                    openActivity();
                }
            }

        }
        else if (id == R.id.quranuzb){
            if (!checkPermission()) {
                openActivityUz();
            } else {
                if (checkPermission()) {
                    requestPermissionAndContinue();
                } else {
                    openActivityUz();
                }
            }
        }
        else if (id == R.id.quranmp3){
            if (!checkPermission()) {
                openMediaP3();
            } else {
                if (checkPermission()) {
                    requestPermissionAndContinue();
                } else {
                    openMediaP3();
                }
            }

            } else if (id == R.id.qibla) {
            startActivityForResult(new Intent(this, CompassActivity.class),10000);
        }
        else if (id == R.id.namoz){
            if (!checkPermission()) {
                openActivitynamoz();
            } else {
                if (checkPermission()) {
                    requestPermissionAndContinue();
                } else {
                    openActivitynamoz();
                }
            }
        }else if (id == R.id.namozar){
            if (!checkPermission()) {
                openActivitynamozar();
            } else {
                if (checkPermission()) {
                    requestPermissionAndContinue();
                } else {
                    openActivitynamozar();
                }
            }

        }

        else if (id == R.id.masjid) {
            startActivityForResult(new Intent(this, bilibqoy.mohirjonnnikibuilova.namozvsquron.Mosques.MosquesAct.class),10000);
        } else if (id == R.id.ismlar) {
            try {
                startActivityForResult(new Intent(this, NamesActivity.class), 10000);
            }catch (Exception e){
                Toast.makeText(this, "Internet Exception", Toast.LENGTH_SHORT).show();
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void initializeDateViews() {
        UmmalquraCalendar hijCal = _TimesSET.getUmmalquraCalendar(this);
        Calendar cal = Calendar.getInstance();
        NumberFormat nf = _DisplaySET.getNumberFormat(this);
        ((TextView) findViewById(R.id.main_activity_day_of_week)).setText(getResources().getStringArray(R.array.day_of_week)[cal.get(Calendar.DAY_OF_WEEK) - 1]);
        ((TextView) findViewById(R.id.main_activity_day_of_week)).setTypeface(_DisplaySET.getTypeFace(this));
        ((TextView) findViewById(R.id.main_activity_hijri_month_number)).setText(nf.format(hijCal.get(UmmalquraCalendar.DAY_OF_MONTH)));
        ((TextView) findViewById(R.id.main_activity_hijri_month_name)).setText(getResources().getStringArray(R.array.hijri_month)[hijCal.get(UmmalquraCalendar.MONTH)]);
        ((TextView) findViewById(R.id.main_activity_hijri_month_name)).setTypeface(_DisplaySET.getTypeFace(this));
        ((TextView) findViewById(R.id.main_activity_gregorian_month_number)).setText(nf.format(cal.get(Calendar.DAY_OF_MONTH)));
        ((TextView) findViewById(R.id.main_activity_gregorian_month_name)).setText(getResources().getStringArray(R.array.gregorian_month)[cal.get(Calendar.MONTH)]);
        ((TextView) findViewById(R.id.main_activity_gregorian_month_name)).setTypeface(_DisplaySET.getTypeFace(this));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initializeDateViewsWithGoogleApi() {
        IslamicCalendar hijCal = _TimesSET.getGoogleCalendar(this);
        Calendar cal = Calendar.getInstance();
        NumberFormat nf = _DisplaySET.getNumberFormat(this);
        ((TextView) findViewById(R.id.main_activity_day_of_week)).setText(getResources().getStringArray(R.array.day_of_week)[cal.get(Calendar.DAY_OF_WEEK) - 1]);
        ((TextView) findViewById(R.id.main_activity_day_of_week)).setTypeface(_DisplaySET.getTypeFace(this));
        ((TextView) findViewById(R.id.main_activity_hijri_month_number)).setText(nf.format(hijCal.get(IslamicCalendar.DAY_OF_MONTH)));
        ((TextView) findViewById(R.id.main_activity_hijri_month_name)).setText(getResources().getStringArray(R.array.hijri_month)[hijCal.get(IslamicCalendar.MONTH)]);
        ((TextView) findViewById(R.id.main_activity_hijri_month_name)).setTypeface(_DisplaySET.getTypeFace(this));
        ((TextView) findViewById(R.id.main_activity_gregorian_month_number)).setText(nf.format(cal.get(Calendar.DAY_OF_MONTH)));
        ((TextView) findViewById(R.id.main_activity_gregorian_month_name)).setText(getResources().getStringArray(R.array.gregorian_month)[cal.get(Calendar.MONTH)]);
        ((TextView) findViewById(R.id.main_activity_gregorian_month_name)).setTypeface(_DisplaySET.getTypeFace(this));
    }

    public void initializeTimePoints() {
        if(!_LocationSET.isLocationAssigned(this)) return;
        ViewGroup timePointLayout = findViewById(R.id.time_point_layout);
        ViewGroup timepoint;
        final Toast toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        for (int i = 0; i < 6; i++) {
            final int s = i;
            timepoint = (ViewGroup) timePointLayout.getChildAt(i);
            attributingTimePoint(timepoint, i);
            timepoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toast.setText(getTimeDiffString(s));
                    toast.show();

                }
            });
        }
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            _DisplaySET.tintWidget(this,(ViewGroup) timePointLayout.getChildAt(6));
            reorderTimePointForPortrait();
        }
        tintingUpComingTimePoint(true);
    }

    public void attributingTimePoint(ViewGroup timepoint, int i) {

        ((TextView) timepoint.findViewById(R.id.time_point_text)).setText(getResources().getStringArray(R.array.prayer_time)[i]);
        ((TextView) timepoint.findViewById(R.id.time_point_text)).setTypeface(_DisplaySET.getTypeFace(this));
        ((TextView) timepoint.findViewById(R.id.time_point_ampm)).setText(_TimesSET.getPrayerPhaseString(this,i));
        ((TextView) timepoint.findViewById(R.id.time_point_ampm)).setVisibility(_DisplaySET.isTime24(this)?View.GONE:View.VISIBLE);
        if(getResources().getConfiguration().getLayoutDirection() == LayoutDirection.LTR) {
            View view = timepoint.findViewById(R.id.time_point_ampm);
            ViewGroup layout = (ViewGroup) view.getParent();
            layout.removeView(view);
            layout.addView(view);
        }
        ((TextView) timepoint.findViewById(R.id.time_point_time)).setText(_TimesSET.getPrayerTimeString(this,i));
        _DisplaySET.tintWidget(this,timepoint);
    }

    public void reorderTimePointForPortrait() {
        ViewGroup timePointLayout = findViewById(R.id.time_point_layout);
        ViewGroup timepoint;
        for (int i = 0; i < 7; i++) {
            if (((ViewGroup) timePointLayout.getChildAt(i)).getChildCount() == 1) {
                timepoint = (ViewGroup) timePointLayout.getChildAt(i);
                timePointLayout.removeView(timepoint);
                timePointLayout.addView(timepoint, upComingTimePoint);
                break;
            }
        }
    }

    public void handleProgressBarForLandscape(int remainTime) {
        int goneTimePoint = upComingTimePoint - 1;
        if (goneTimePoint < 0) goneTimePoint = 5;
        ProgressBar progressBar = findViewById(R.id.progress_bar_landscape);
        progressBar.setProgress((int) (100 - (100 * remainTime / ((_TimesSET.getPrayerTimeMillis(upComingTimePoint,true)- _TimesSET.getPrayerTimeMillis(goneTimePoint,false))/1000))));
    }

    public void tintingUpComingTimePoint(boolean tint) {
        int widNum = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE?
                upComingTimePoint:(upComingTimePoint+1);
        if(tint)_DisplaySET.tintWidgetUpComing(this, (ViewGroup) ((ViewGroup) findViewById(R.id.time_point_layout)).getChildAt(widNum));
        else _DisplaySET.tintWidget(this, (ViewGroup) ((ViewGroup) findViewById(R.id.time_point_layout)).getChildAt(widNum));
    }


    public void startTimer() {
        if(!_LocationSET.isLocationAssigned(this)) return;
        final NumberFormat nf = _DisplaySET.getNumberFormat(this);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                int h, m, s;
                int remainTime = (int) ((_TimesSET.getPrayerTimeMillis(upComingTimePoint,true) - System.currentTimeMillis())/1000);
                if (remainTime < 1) {
                    tintingUpComingTimePoint(false);
                    upComingTimePoint = _TimesSET.comingTimePointIndex();
                    if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
                        reorderTimePointForPortrait();
                    tintingUpComingTimePoint(true);
                } else {
                    h = (int) remainTime / 3600;
                    m = (int) ((remainTime - (h * 3600)) / 60);
                    s = (int) remainTime - (h * 3600) - (m * 60);
                    ViewGroup countdown = findViewById(R.id.main_activity_timer);
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                        handleProgressBarForLandscape(remainTime);
                    ((TextView) (countdown.getChildAt(0))).setText(nf.format(h));
                    ((TextView) (countdown.getChildAt(2))).setText(nf.format(m));
                    ((TextView) (countdown.getChildAt(4))).setText(nf.format(s));
                }
                handler.postDelayed(this,1000);
            }
        };
        runnable.run();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(reloadMainActivityOnResume){
            reloadMainActivityOnResume = false;
            recreate();
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        if(handler != null)handler.removeCallbacks(runnable);
        reloadMainActivityOnResume = true;
    }


    private String getTimeDiffString(int i) {
        String s = "";
        NumberFormat nf = _DisplaySET.getNumberFormat(this);
        long diff = _TimesSET.getPrayerTimeMillis(i,false) - System.currentTimeMillis();
        if(diff < 0) s+=getResources().getString(R.string.toast_prayer_widget_click_was);
        diff /= 1000;
        int h = (int) (diff /3600);
        int m = (int) (diff - (h*3600))/60;
        if(h<0) h=-h;
        if(m<0) m=-m;
        if(h!=0) s+= " " + (h>2?nf.format(h) + " ":"") + getHoursWordString(h) + (m!=0?" "+ getResources().getString(R.string.toast_prayer_widget_click_and):"");
        if(m!=0) s+= " " + (m>2?nf.format(m) + " ":"") + getMinutesWordString(m);
        s+= diff<0?" "+ getResources().getString(R.string.toast_prayer_widget_click_ago):" "+ getResources().getString(R.string.toast_prayer_widget_click_remaining);
        if(h==0&&m==0) s= getResources().getString(R.string.toast_prayer_widget_click_just);
        return s;
    }

    private String getMinutesWordString(int m) {
        switch (m){
            case 1:
                return getResources().getString(R.string.toast_prayer_widget_click_one_minute);
            case 2:
                return getResources().getString(R.string.toast_prayer_widget_click_two_minutes);
            default:
                return getResources().getString(R.string.toast_prayer_widget_click_minutes);
        }
    }

    private String getHoursWordString(int h) {
        switch (h){
            case 1:
                return getResources().getString(R.string.toast_prayer_widget_click_one_hour);
            case 2:
                return getResources().getString(R.string.toast_prayer_widget_click_two_hours);
            default:
                if(h>10) return getResources().getString(R.string.toast_prayer_widget_click_hours_more_than_ten);
                return getResources().getString(R.string.toast_prayer_widget_click_hours);
        }
    }


    //*********************************************

    private static final int PERMISSION_REQUEST_CODE = 200;
    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(getString(R.string.permission_necessary));
                alertBuilder.setMessage(R.string.storage_permission_is_encessary_to_wrote_event);
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            openActivity();
            openActivityen();
            openActivityru();
            openActivitytr();
            openActivityUz();
            openMediaP3();
            openActivitynamoz();
            openActivitynamozar();
            openActivitynamoztr();
            openActivitynamozru();
            openActivitynamozen();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openActivity();
                    openActivityru();
                    openActivityen();
                    openActivityUz();
                    openActivityUz();
                    openMediaP3();
                    openActivitynamoz();
                    openActivitynamozar();
                    openActivitynamozen();
                    openActivitynamozru();
                    openActivitynamoztr();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openActivity() {
        startActivityForResult(new Intent(this, ActivityQuranAr.class), 10000);
        //add your further process after giving permission or to download images from remote server.
    }
    private void openActivityUz() {
        startActivityForResult(new Intent(this, Quran_activity.class), 10000);
        //add your further process after giving permission or to download images from remote server.
    }
    private void openActivityru() {

        //add your further process after giving permission or to download images from remote server.
    }
    private void openActivityen() {

        //add your further process after giving permission or to download images from remote server.
    }
    private void openActivitytr() {

        //add your further process after giving permission or to download images from remote server.
    }
    //******
    // ******************************

    private void openMediaP3() {
        startActivityForResult(new Intent(this, RecyclerViewActivity.class), 1000);
    }
    private void openActivitynamozar() {
        startActivityForResult(new Intent(this, NamozKitobAr.class), 1000);
    }

    private void openActivitynamoz() {
        startActivityForResult(new Intent(this, NamozKitobUz.class), 1000);
    }
    private void openActivitynamozru() {

    }
    private void openActivitynamozen() {

    }
    private void openActivitynamoztr() {

    }



    //////////////////////////////////
    @SuppressLint("WrongConstant")
    private void checkUpdate(){
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.registerListener(listener);

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            Log.d("appUpdateInfo :", "packageName :"+appUpdateInfo.packageName()+ ", "+ "availableVersionCode :"+ appUpdateInfo.availableVersionCode() +", "+"updateAvailability :"+ appUpdateInfo.updateAvailability() +", "+ "installStatus :" + appUpdateInfo.installStatus() );

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(FLEXIBLE)){
                requestUpdate(appUpdateInfo);
                Log.d("UpdateAvailable","update is there ");
            }
            else if (appUpdateInfo.updateAvailability() == 3){
                Log.d("Update","3");
                notifyUser();
            }
            else
            {
            //    Toast.makeText(MainActivity.this, "No Update Available", Toast.LENGTH_SHORT).show();
                Log.d("NoUpdateAvailable","update is not there ");
            }
        });
    }
    private void requestUpdate(AppUpdateInfo appUpdateInfo){
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, FLEXIBLE,MainActivity.this,MY_REQUEST_CODE);
            resume();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();


        if (requestCode == MY_REQUEST_CODE){
            switch (resultCode){
                case Activity.RESULT_OK:
                    if(resultCode != RESULT_OK){
                        Toast.makeText(this,"RESULT_OK" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_OK  :",""+resultCode);
                    }
                    break;
                case Activity.RESULT_CANCELED:

                    if (resultCode != RESULT_CANCELED){
                        Toast.makeText(this,"RESULT_CANCELED" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_CANCELED  :",""+resultCode);
                    }
                    break;
                case RESULT_IN_APP_UPDATE_FAILED:

                    if (resultCode != RESULT_IN_APP_UPDATE_FAILED){

                        Toast.makeText(this,"RESULT_IN_APP_UPDATE_FAILED" +resultCode, Toast.LENGTH_LONG).show();
                        Log.d("RESULT_IN_APP_FAILED:",""+resultCode);
                    }
            }
        }
    }

    InstallStateUpdatedListener listener = installState -> {
        if (installState.installStatus() == InstallStatus.DOWNLOADED){
            Log.d("InstallDownloded","InstallStatus sucsses");
            notifyUser();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // appUpdateManager.unregisterListener((InstallStateUpdatedListener) this);
    }

    private void notifyUser() {

        Snackbar snackbar =
                Snackbar.make(findViewById(R.id.message),
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(
                getResources().getColor(R.color.snackbar));
        snackbar.show();
    }

    private void resume(){
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                notifyUser();

            }

        });
    }
    ///////////////
    @Override
    public void onBackPressed() {
        if (interstitialAd.isLoaded() | drawer.isDrawerOpen(GravityCompat.START)){
            interstitialAd.show();
            drawer.closeDrawer(Gravity.LEFT);

        }else {
            super.onBackPressed();

        }
        }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    Calendar cal_alarm=Calendar.getInstance();
    void NotificationAlaram()
    {

        SharedPreferences getTime= PreferenceManager.getDefaultSharedPreferences(this);

        int timeInMnutes=getTime.getInt("zikr_alaram",0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date dat = new Date();
        //Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY,timeInMnutes/60);
        cal_alarm.set(Calendar.MINUTE,timeInMnutes%60);
        cal_alarm.set(Calendar.SECOND,00);
        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE,1);
            //Toast.makeText(this,"one hour passed",Toast.LENGTH_SHORT).show();
        }


        Intent myIntent = new Intent(this, Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        //manager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMnutes*60000,AlarmManager.INTERVAL_DAY, pendingIntent);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    }

