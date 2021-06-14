package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms.AlarmsScheduler;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations._LocationSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Times._TimesSET;

import java.util.Calendar;

public class UpdateCurrentLocationActivity extends AppCompatActivity {

    public static final String tempLocationFile = "updatecurrenttemplocationfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_update_current_location);

        Toolbar toolbar = findViewById(R.id.toolbar_update_current_location_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.update_current_location));
        setClickers();
        _LocationSET.getRequests(this);
        findViewById(R.id.update_current_location_activity_last_location_button).performClick();
        setTitleLocation();
    }

    private void setClickers() {
        findViewById(R.id.update_current_location_activity_internet_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateCurrentLocationActivity.this, InternetLocationSearchActivity.class);
                intent.putExtra("filename", "temp");
                startActivity(intent);
            }
        });
        findViewById(R.id.update_current_location_activity_manually_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateCurrentLocationActivity.this, ManuallyLocationActivity.class);
                intent.putExtra("filename", "temp");
                startActivity(intent);

            }
        });
        findViewById(R.id.update_current_location_activity_gps_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!_LocationSET.checkGpsAvailability(UpdateCurrentLocationActivity.this))
                    Toast.makeText(UpdateCurrentLocationActivity.this, getResources().getString(R.string.this_device_has_no_gps_equipment), Toast.LENGTH_SHORT).show();
                else if (!_LocationSET.checkGpsEnabled(UpdateCurrentLocationActivity.this))
                    Toast.makeText(UpdateCurrentLocationActivity.this, getResources().getString(R.string.please_enable_gps_first), Toast.LENGTH_SHORT).show();
                else _LocationSET.getLocation(UpdateCurrentLocationActivity.this, tempLocationFile, new _LocationSET.locationUpdateResult() {
                        @Override
                        public void onSuccess() {
                            setTitleLocation();
                        }

                        @Override
                        public void onFail() {
                            Toast.makeText(UpdateCurrentLocationActivity.this,getResources().getString(R.string.toast_failed_to_update_location),Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
        findViewById(R.id.update_current_location_activity_last_location_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _LocationSET.getLastLocation(UpdateCurrentLocationActivity.this, tempLocationFile, new _LocationSET.locationUpdateResult() {
                    @Override
                    public void onSuccess() {
                        setTitleLocation();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(UpdateCurrentLocationActivity.this,getResources().getString(R.string.update_current_activity_last_known_location_not_found),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        findViewById(R.id.update_current_location_activity_network_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!_LocationSET.checkNetworkAvailability(UpdateCurrentLocationActivity.this))
                    Toast.makeText(UpdateCurrentLocationActivity.this, getResources().getString(R.string.please_check_for_internet_connection), Toast.LENGTH_SHORT).show();
                else _LocationSET.getLocation(UpdateCurrentLocationActivity.this, tempLocationFile, new _LocationSET.locationUpdateResult() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(UpdateCurrentLocationActivity.this,getResources().getString(R.string.toast_location_updated_successfully),Toast.LENGTH_SHORT).show();
                        setTitleLocation();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(UpdateCurrentLocationActivity.this,getResources().getString(R.string.toast_failed_to_update_location),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        findViewById(R.id.update_current_location_activity_save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!_LocationSET.assignLocation(UpdateCurrentLocationActivity.this, tempLocationFile, _LocationSET.currentLocation)) {
                    Toast.makeText(UpdateCurrentLocationActivity.this,getResources().getString(R.string.toast_no_location_data),Toast.LENGTH_SHORT).show();
                    UpdateCurrentLocationActivity.this.finish();
                    return;
                }
                _TimesSET.setDstDefault(UpdateCurrentLocationActivity.this);
                _LocationSET.setLocationAssigned(UpdateCurrentLocationActivity.this);
                _TimesSET.updateTimes(UpdateCurrentLocationActivity.this);
                MainActivity.reloadMainActivityOnResume = true;
                LocationsActivity.reloadLocationsActivityOnResume = true;
                AlarmsScheduler.fire(UpdateCurrentLocationActivity.this, Calendar.getInstance());
                UpdateCurrentLocationActivity.this.finish();
            }
        });
        findViewById(R.id.update_current_location_activity_location_edit_button_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getSharedPreferences(tempLocationFile,MODE_PRIVATE).getBoolean("islocationassigned",false)) {
                    Intent intent = new Intent(UpdateCurrentLocationActivity.this, AdjustLocationActivity.class);
                    intent.putExtra("location_file", tempLocationFile);
                    startActivity(intent);
                }else {
                    Toast.makeText(UpdateCurrentLocationActivity.this,getResources().getString(R.string.toast_choose_one_of_the_update_methods_first),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void setTitleLocation() {
        String lo = "Lng: " + getSharedPreferences(tempLocationFile, MODE_PRIVATE).getFloat("longitude", -99999);
        String la = "Lat: " + getSharedPreferences(tempLocationFile, MODE_PRIVATE).getFloat("latitude", -99999);
        lo = lo.length()>11?lo.substring(0,11):lo;
        la = la.length()>11?la.substring(0,11):la;
        float offset = getSharedPreferences(tempLocationFile, MODE_PRIVATE).getFloat("offset", -99999) +
                getSharedPreferences(tempLocationFile, MODE_PRIVATE).getFloat("auto_dst", -99999);
        String tz = offset + " GMT";
        if(getSharedPreferences(tempLocationFile, MODE_PRIVATE).getBoolean("islocationassigned",false)) {
            ((TextView) findViewById(R.id.update_current_location_activity_location_title)).setText(la + "  " + lo + "\n" + tz);
            ((TextView) findViewById(R.id.update_current_location_activity_location_title)).setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            Log.d("Latidu: ", la + lo);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if(getSharedPreferences(tempLocationFile,MODE_PRIVATE).getBoolean("islocationassigned",false)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.alert_location_information_will_lost_title));
            builder.setMessage(getResources().getString(R.string.alert_location_information_will_lost_msg));
            builder.setPositiveButton(getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    UpdateCurrentLocationActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.alert_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        } else super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        _LocationSET.clearTempLocationFile(this,tempLocationFile);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if(getSharedPreferences("temp",MODE_PRIVATE).getBoolean("islocationassigned",false)){
            _LocationSET.assignLocation(UpdateCurrentLocationActivity.this,"temp",tempLocationFile);
            _LocationSET.clearTempLocationFile(UpdateCurrentLocationActivity.this,"temp");
            Toast.makeText(UpdateCurrentLocationActivity.this,getResources().getString(R.string.toast_location_updated_successfully),Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}
