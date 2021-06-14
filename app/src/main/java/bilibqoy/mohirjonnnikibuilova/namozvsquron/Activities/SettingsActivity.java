package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations._LocationSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings.SettingsRecyclerViewAdapter;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings._SET;

public class SettingsActivity extends AppCompatActivity {

    public static boolean reloadSettingsActivityOnResume = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar_settings_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.settings));
        _SET.startSettings(this);
        final ViewGroup group = findViewById(R.id.settings_activity_layout);
        final Intent intent = new Intent(this,SettingsSecondActivity.class);
        for(int i=0;i<group.getChildCount();i++){
            final int k =i;
            ((ImageButton) group.getChildAt(i).findViewById(R.id.settings_box_image)).setImageResource(getResources().obtainTypedArray(R.array.settings_image).getResourceId(i,-1));
            ((TextView) group.getChildAt(i).findViewById(R.id.settings_box_header)).setText(getResources().getStringArray(R.array.settings_menu)[i]);
            ((TextView) group.getChildAt(i).findViewById(R.id.settings_box_header)).setTypeface(_DisplaySET.getTypeFace(this));
            ((TextView) group.getChildAt(i).findViewById(R.id.settings_box_details)).setText(getResources().getStringArray(R.array.settings_submenu)[i]);
            ((TextView) group.getChildAt(i).findViewById(R.id.settings_box_details)).setTypeface(_DisplaySET.getTypeFace(this));
            group.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int z;
                    switch (k){
                        case 0: z= SettingsRecyclerViewAdapter.PRAYER_TIMES_LAYOUT_NUM; break;
                        case 1: z= SettingsRecyclerViewAdapter.HIJRI_LAYOUT_NUM; break;
                        case 2: z= SettingsRecyclerViewAdapter.DISPLAY_OPTIONS_LAYOUT_NUM; break;
                        case 3: z= SettingsRecyclerViewAdapter.NOTIFICATIONS_LAYOUT_NUM; break;
                        //case 4: z= SettingsRecyclerViewAdapter.FAJR_AND_SAHOOR_LAYOUT_NUM; return; // Fajer and Sahoor is disabled!
                        case 4: z= SettingsRecyclerViewAdapter.SILENT_LAYOUT_NUM; break;
                        case 5: z= SettingsRecyclerViewAdapter.BACKUP_AND_RESTORE_LAYOUT_NUM; break;
                        default: z= SettingsRecyclerViewAdapter.PRAYER_TIMES_LAYOUT_NUM; break;
                    }
                    intent.putExtra("index",z);
                    intent.putExtra("title",((TextView) group.getChildAt(k).findViewById(R.id.settings_box_header)).getText().toString());
                    if((k==0||k==3||k==4||k==5) && !_LocationSET.isLocationAssigned(SettingsActivity.this))
                        Toast.makeText(SettingsActivity.this,getResources().getString(R.string.please_set_location_first),Toast.LENGTH_SHORT).show();
                    else startActivity(intent);
                }
            });

        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        if(reloadSettingsActivityOnResume){
            reloadSettingsActivityOnResume = false;
            recreate();
        }
        super.onResume();
    }

}
