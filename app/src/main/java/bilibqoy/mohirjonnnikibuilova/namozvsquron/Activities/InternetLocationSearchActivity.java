package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations._LocationSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;


public class InternetLocationSearchActivity extends AppCompatActivity {

    private String tempLocationFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_internet_location_search);
        tempLocationFile = getIntent().getExtras().getString("filename");
        findViewById(R.id.internet_location_search_activity_manually_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InternetLocationSearchActivity.this,ManuallyLocationActivity.class);
                intent.putExtra("filename",tempLocationFile);
                startActivity(intent);
            }
        });

        ((EditText)findViewById(R.id.internet_location_search_activity_internet_search)).setImeActionLabel(getResources().getString(R.string.search), KeyEvent.KEYCODE_ENTER);
        ((EditText)findViewById(R.id.internet_location_search_activity_internet_search)).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    return true;
                }
                return false;
            }
        });
        if(!tempLocationFile.equals("newlocation")) findViewById(R.id.internet_location_search_activity_current_button).setVisibility(View.GONE);
        findViewById(R.id.internet_location_search_activity_current_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getSharedPreferences(_LocationSET.currentLocation,MODE_PRIVATE).getString("location_name","").replaceAll("[^a-zA-Z0-9\\.\\-]", "").equals("")) {
                    Toast.makeText(InternetLocationSearchActivity.this, getResources().getString(R.string.toast_set_location_name_first), Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(InternetLocationSearchActivity.this);
                builder.setTitle(getResources().getString(R.string.add_current_location));
                builder.setMessage(getResources().getString(R.string.alert_add_current_location_msg));
                builder.setPositiveButton(getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        _LocationSET.assignLocation(InternetLocationSearchActivity.this,_LocationSET.currentLocation,tempLocationFile);
                        dialogInterface.dismiss();
                        InternetLocationSearchActivity.this.finish();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.alert_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        if(getIntent().getExtras().getBoolean("new")) findViewById(R.id.internet_location_search_activity_current_button).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        SharedPreferences preferences = getSharedPreferences(tempLocationFile,MODE_PRIVATE);
        if(preferences.getBoolean("islocationassigned",false)) finish();
        super.onResume();
    }
}
