package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations._LocationSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Times._TimesSET;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LocationsActivity extends AppCompatActivity {

    public static boolean reloadLocationsActivityOnResume = false;
    private final int MAXIMUM_SAVED_LOCATIONS = 5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_locations);
        Toolbar toolbar = findViewById(R.id.toolbar_locations_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.saved_locations));
        ((ImageView) findViewById(R.id.locations_activity_image)).setImageDrawable(getResources().getDrawable(
                _DisplaySET.getAppTheme(this) == _DisplaySET.WHITE ?R.drawable.backgroundui:R.drawable.background_black));
        locationsWidgets();
        ((TextView) findViewById(R.id.locations_activity_current_location_text)).setText(
                getSharedPreferences(_LocationSET.currentLocation,MODE_PRIVATE).getString("location_name","")
        );
        ((View) findViewById(R.id.locations_activity_add_location_floating_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getSharedPreferences(_LocationSET.locationsFile,MODE_PRIVATE).getInt("locationsnumber",0) >= MAXIMUM_SAVED_LOCATIONS){
                    Toast.makeText(LocationsActivity.this,getResources().getString(R.string.toast_maximum_saved_locations),Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(LocationsActivity.this,InternetLocationSearchActivity.class);
                intent.putExtra("filename","newlocation");
                startActivity(intent);
            }
        });
        findViewById(R.id.locations_activity_update_current_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LocationsActivity.this, UpdateCurrentLocationActivity.class));
            }
        });
        if(_LocationSET.isLocationAssigned(LocationsActivity.this))
            findViewById(R.id.locations_activity_adjust_current_location).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent intent = new Intent(LocationsActivity.this,AdjustLocationActivity.class);
                   intent.putExtra("location_file",_LocationSET.currentLocation);
                   startActivity(intent);
                }
            });
        else ((TextView)findViewById(R.id.locations_activity_adjust_current_location)).setTextColor(Color.DKGRAY);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {

        SharedPreferences preferences = getSharedPreferences("newlocation",MODE_PRIVATE);
        if(preferences.getBoolean("islocationassigned",false)) {
            _LocationSET.addLocationToSavedLocations(LocationsActivity.this,"newlocation");
            reloadLocationsActivityOnResume = true;
            _LocationSET.clearTempLocationFile(this,"newlocation");
        }
        if(reloadLocationsActivityOnResume){
            reloadLocationsActivityOnResume = false;
            recreate();
        }
        super.onResume();
    }

    private void locationsWidgets(){
        SharedPreferences locations = getSharedPreferences(_LocationSET.locationsFile,MODE_PRIVATE);
        SharedPreferences location_i;
        ViewGroup list = findViewById(R.id.locations_activity_locations_list_layout);
        for(int i=1; i<6;i++){
            final int k = i;
            final String locationID = locations.getString("location"+i,"");
            final ViewGroup widget = (ViewGroup)list.getChildAt(i-1);
            if(locationID.equals("")) break;
            location_i = getSharedPreferences(locationID,MODE_PRIVATE);
            _LocationSET.updateDstToCurrent(this,locationID,new GregorianCalendar());
            String[] prayertimes = _TimesSET.initializeTimesFor(LocationsActivity.this,locationID, Calendar.getInstance());
            widget.setVisibility(
                    location_i.getBoolean("islocationassigned",false)?View.VISIBLE:View.INVISIBLE
            );
            ((TextView)(widget).getChildAt(0)).setText(
                    location_i.getString("location_name","")
            );
            ((TextView)(widget).getChildAt(2)).setText(
                    (new SimpleDateFormat("EEE dd/MM/yyyy hh:mm aa")).format(new Date()) + "  "
                     + _LocationSET.getTimeRawOffset(location_i.getString("timezone","")) + " GMT"
            );

            widget.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LocationsActivity.this);
                    builder.setTitle(getResources().getString(R.string.choose_an_option));
                    builder.setItems(getResources().getStringArray(R.array.location_widget_options), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == 0) {
                                _LocationSET.clearTempLocationFile(LocationsActivity.this,locationID);
                                _LocationSET.removeFromLocationsFile(LocationsActivity.this,k);
                                widget.setVisibility(View.GONE);
                            } else if(i==1){
                                _LocationSET.assignLocation(LocationsActivity.this,locationID, _LocationSET.currentLocation);
                                _TimesSET.setDstDefault(LocationsActivity.this);
                                MainActivity.reloadMainActivityOnResume = true;
                                ((TextView) findViewById(R.id.locations_activity_current_location_text)).setText(((TextView)widget.getChildAt(0)).getText());
                            }
                        }
                    });
                    builder.show();
                }
            });
            ((TextView)(((ViewGroup)list.getChildAt(i-1)).getChildAt(3))).setText(getResources().getStringArray(R.array.prayer_time)[0] + "\n" + prayertimes[0]);
            ((TextView)(((ViewGroup)list.getChildAt(i-1)).getChildAt(4))).setText(getResources().getStringArray(R.array.prayer_time)[1] + "\n" + prayertimes[1]);
            ((TextView)(((ViewGroup)list.getChildAt(i-1)).getChildAt(5))).setText(getResources().getStringArray(R.array.prayer_time)[2] + "\n" + prayertimes[2]);
            ((TextView)(((ViewGroup)list.getChildAt(i-1)).getChildAt(6))).setText(getResources().getStringArray(R.array.prayer_time)[3] + "\n" + prayertimes[3]);
            ((TextView)(((ViewGroup)list.getChildAt(i-1)).getChildAt(7))).setText(getResources().getStringArray(R.array.prayer_time)[4] + "\n" + prayertimes[4]);
            ((TextView)(((ViewGroup)list.getChildAt(i-1)).getChildAt(8))).setText(getResources().getStringArray(R.array.prayer_time)[5] + "\n" + prayertimes[5]);
        }
    }


}
