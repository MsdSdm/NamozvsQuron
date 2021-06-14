package bilibqoy.mohirjonnnikibuilova.namozvsquron.Masjid;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "MyLog";
    boolean lastTime=false;
    GoogleMap googleMap;
    private Button start;
    FrameLayout frameLayout;
    //location manager
    private LocationManager locMan;

    static WifiReceiver wifiReceiver;
    //user marker
    private Marker userMarker;


    private boolean updateFinished = true;


    //markerOptions of interest
    private Marker[] markers;
    //max
    private final int MAX_PLACES = 20;//most returned from google
    //marker options
    private MarkerOptions[] markerOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_main2);

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





        frameLayout= (FrameLayout) findViewById(R.id.frameLayout);

        frameLayout.setEnabled(false);
        locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // enableGPS();
        wifiReceiver=new WifiReceiver();
        IntentFilter intentFilter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

        registerReceiver(wifiReceiver, intentFilter);

        if (googleMap != null) {
            //ok - proceed
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            //create marker array
            markers = new Marker[MAX_PLACES];

        }
        //find out if we already have it
        if (googleMap == null) {
            //get the map
            MapFragment mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {


                    MainActivity.this.googleMap = googleMap;
                    //ok - proceed
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    //create marker array
                    markers = new Marker[MAX_PLACES];

                    enableGPS();

                }
            });

        }
    }

    void enableGPS() {
        //check in case map/ Google Play services not available

        //update location
        //   locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            } else
                checkIfGPSIsOn(locMan, this);
        } else {
            locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 10, this);
            updatePlaces(getLastKnownLocation());
        }


    }

    @SuppressWarnings({"MissingPermission"})
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted


                    locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 10, this);
                    updatePlaces(getLastKnownLocation());

                } else {

                    // permission denied
                    Toast.makeText(this, getString(R.string.MainActivtyMasjid_Toast), Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    protected void checkIfGPSIsOn(LocationManager manager, Context context) {
        if (!manager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.MainActivtyMasjid_builder));
            builder.setMessage(getString(R.string.MainActivityMasjid_setMessage));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.create().show();
        } else {

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Ask the user to enable GPS
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Location Manager");
                builder.setMessage("Masjid Locator would like to enable your device's GPS?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Launch settings, allowing user to make a change
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //No location service, no Activity
                        finish();
                    }
                });
                builder.create().show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (updateFinished)
            updatePlaces(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    void getPermissionLastTime()
    {
        if(!lastTime) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE}, 1);

                } else
                    checkIfGPSIsOn(locMan, this);
            } else {
                locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 10, this);
                updatePlaces(getLastKnownLocation());
            }
        }

        lastTime=true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @SuppressWarnings({"MissingPermission"})
    private Location getLastKnownLocation() {
        locMan = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locMan.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locMan.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @SuppressWarnings({"MissingPermission"})
    private void updatePlaces(Location lastLoc) {
        //get location manager
        //get last location
        double lat=0 ;
        double lng=0;
        if(lastLoc!=null) {
            lat = lastLoc.getLatitude();
            lng = lastLoc.getLongitude();
        }
        else
        {
            locMan= (LocationManager) MainActivity.this.getSystemService(LOCATION_SERVICE);
            lastLoc=locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(lastLoc!=null) {
                lat = lastLoc.getLatitude();
                lng = lastLoc.getLongitude();
            }
            else
            {

                getPermissionLastTime();

                Toast.makeText(MainActivity.this, getString(R.string.MainActivityMasjid_Toast1), Toast.LENGTH_LONG).show();
                MainActivity.this.finish();
            }
        }


        if(lastLoc !=null ) {
            frameLayout.setEnabled(true);

            //create LatLng
            LatLng lastLatLng = new LatLng(lat, lng);

            //remove any existing marker
            if (userMarker != null)
                userMarker.remove();
            //create and set marker properties
            userMarker = googleMap.addMarker(new MarkerOptions()
                    .position(lastLatLng)
                    .title(getString(R.string.MainActivityMasjid_title394))
                    .snippet(getString(R.string.MainActivityMasjid_395)));
            //move to location
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 15));

            //build markerOptions query string
            String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                    "json?location=" + lat + "," + lng +
                    "&radius=1000&sensor=true" +
                    "&types=mosque" +
                    "&key=AIzaSyDqS93xljF5bz_bYEZXgG7hZXprV10EIDA";//ADD KEY

            if(isNetworkAvailable()) {
                //execute query
                new GetPlaces().execute(placesSearchStr);
            }
            else {

                Toast.makeText(MainActivity.this, getString(R.string.MainActivityMasjid_Toast2),Toast.LENGTH_LONG).show();
            }
        }
        else
        {

            Toast.makeText(MainActivity.this, getString(R.string.MainActivityMasjid_Toast4), Toast.LENGTH_LONG).show();
        }
    }

    private class GetPlaces extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... placesURL) {
            //fetch markerOptions
            updateFinished = false;
            StringBuilder placesBuilder = new StringBuilder();
            for (String placeSearchURL : placesURL) {
                try {

                    URL requestUrl = new URL(placeSearchURL);
                    HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        BufferedReader reader = null;

                        InputStream inputStream = connection.getInputStream();
                        if (inputStream == null) {
                            return "";
                        }
                        reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        while ((line = reader.readLine()) != null) {

                            placesBuilder.append(line + "\n");
                        }

                        if (placesBuilder.length() == 0) {
                            return "";
                        }

                        Log.d("test", placesBuilder.toString());
                    } else {
                        Log.i("test", "Unsuccessful HTTP Response Code: " + responseCode);
                        return "";
                    }
                } catch (MalformedURLException e) {
                    Log.e("test", "Error processing Places API URL", e);
                    return "";
                } catch (IOException e) {
                    Log.e("test", "Error connecting to Places API", e);
                    return "";
                }
            }
            return placesBuilder.toString();
        }

        //process data retrieved from doInBackground
        protected void onPostExecute(String result) {
            //parse place data returned from Google Places
            //remove existing markers
            if (markers != null) {
                for (int pm = 0; pm < markers.length; pm++) {
                    if (markers[pm] != null)
                        markers[pm].remove();
                }
            }
            try {
                //parse JSON

                //create JSONObject, pass stinrg returned from doInBackground
                JSONObject resultObject = new JSONObject(result);
                //get "results" array
                JSONArray placesArray = resultObject.getJSONArray("results");
                //marker options for each place returned
                markerOptions = new MarkerOptions[placesArray.length()];
                //loop through markerOptions



                for (int p = 0; p < placesArray.length(); p++) {
                    //parse each place
                    //if any values are missing we won't show the marker
                    boolean missingValue = false;
                    LatLng placeLL = null;
                    String placeName = "";
                    String vicinity = "";

                    try {
                        //attempt to retrieve place data values
                        missingValue = false;
                        //get place at this index
                        JSONObject placeObject = placesArray.getJSONObject(p);
                        //get location section
                        JSONObject loc = placeObject.getJSONObject("geometry")
                                .getJSONObject("location");
                        //read lat lng
                        placeLL = new LatLng(Double.valueOf(loc.getString("lat")),
                                Double.valueOf(loc.getString("lng")));
                        //get types
                        JSONArray types = placeObject.getJSONArray("types");
                        //loop through types
                        for (int t = 0; t < types.length(); t++) {
                            //what type is it
                            String thisType = types.get(t).toString();
                            //check for particular types - set icons
                            if (thisType.contains("mosque")) {
                                //			currIcon = masjidIcon;
                                break;
                            } else if (thisType.contains("health")) {
                                //	currIcon = drinkIcon;
                                break;
                            } else if (thisType.contains("doctor")) {
                                //	currIcon = shopIcon;
                                break;
                            }
                        }
                        //vicinity
                        vicinity = placeObject.getString("vicinity");
                        //name
                        placeName = placeObject.getString("name");
                    } catch (JSONException jse) {
                        Log.v("PLACES", "missing value");
                        Toast.makeText(MainActivity.this, getString(R.string.MainActivityMasjid_Toast6), Toast.LENGTH_LONG).show();
                        missingValue = true;
                        jse.printStackTrace();
                    }
                    //if values missing we don't display
                    if (missingValue) markerOptions[p] = null;
                    else
                        markerOptions[p] = new MarkerOptions()
                                .position(placeLL)
                                .title(placeName)
                                .snippet(vicinity).icon(BitmapDescriptorFactory.fromResource(R.drawable.logo));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, getString(R.string.MainActivityMasjid_Toast7), Toast.LENGTH_LONG).show();
            }
            if (markerOptions != null && markers != null) {


                for (int p = 0; p < markerOptions.length && p < markers.length; p++) {
                    //will be null if a value was missing

                    if (markerOptions[p] != null) {

                        markers[p] = googleMap.addMarker(markerOptions[p]);

                    }
                }


            }

            updateFinished=true;
        }


    }

    @SuppressWarnings({"MissingPermission"})
    @Override
    protected void onResume() {
        super.onResume();
        if (googleMap != null) {
            locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 100, this);
        }
    }

    @SuppressWarnings({"MissingPermission"})
    @Override
    protected void onPause() {
        super.onPause();
        if (googleMap != null) {
            locMan.removeUpdates(this);
        }
    }



    public class WifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMan.getActiveNetworkInfo();
            if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI)
                if(googleMap!=null && markers==null)
                updatePlaces(null);
            else
                if(googleMap==null && markers==null)
                Toast.makeText(MainActivity.this, getString(R.string.MainActivityMasjid_Toast8), Toast.LENGTH_LONG).show();
        }
    }


    @SuppressWarnings({"MissingPermission"})
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);
        locMan.removeUpdates(this);

    }
}
