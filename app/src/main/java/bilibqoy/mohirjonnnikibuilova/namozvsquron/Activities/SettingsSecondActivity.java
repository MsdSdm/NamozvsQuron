package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms._AlarmSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Backup._BackupSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings.SettingsRecyclerViewAdapter;

public class SettingsSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_settings_second);
        Toolbar toolbar = findViewById(R.id.toolbar_settings_second_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setLayout();
        checkForPermissions();
    }

    private void checkForPermissions() {
        if(getIntent().getExtras().getInt("index") == SettingsRecyclerViewAdapter.BACKUP_AND_RESTORE_LAYOUT_NUM)
            _BackupSET.checkForPermissions(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void setLayout(){
        getSupportActionBar().setTitle(getIntent().getExtras().getString("title"));
        RecyclerView recyclerView = findViewById(R.id.settings_second_activity_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewCacheSize(0);
        recyclerView.setAdapter(new SettingsRecyclerViewAdapter(this,getIntent().getExtras().getInt("index")));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SharedPreferences pref = null;
        SharedPreferences.Editor editor = null;
        Uri uri = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 50400:
                    pref = getSharedPreferences(_AlarmSET.sahoorFile,MODE_PRIVATE);
                    editor = pref.edit();
                    uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if(uri!=null) {
                        editor.putString("uri", uri.toString());
                        editor.commit();
                    } else {
                        editor.remove("uri");
                        editor.commit();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
