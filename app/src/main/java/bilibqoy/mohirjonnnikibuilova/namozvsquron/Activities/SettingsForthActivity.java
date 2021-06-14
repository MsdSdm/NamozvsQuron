package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms._AlarmSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings.SettingsRecyclerViewAdapter;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings._SET;

public class SettingsForthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_settings_forth);
        Toolbar toolbar = findViewById(R.id.toolbar_settings_forth_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setLayout();
    }

    private void setLayout() {
        RecyclerView recyclerView = findViewById(R.id.settings_forth_activity_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewCacheSize(0);
        recyclerView.setAdapter(new SettingsRecyclerViewAdapter(this,SettingsRecyclerViewAdapter.PRAYER_NOTIFICATIONS_TONE_EACH_LAYOUT_NUM));
        getSupportActionBar().setTitle(getIntent().getExtras().getString("title"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SharedPreferences pref = null;
        SharedPreferences.Editor editor = null;
        Uri uri = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 40415: case 40416: case 40417: case 40418: case 40419:
                    pref = getSharedPreferences(_AlarmSET.azanFile,MODE_PRIVATE);
                    editor = pref.edit();
                    uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if(uri!=null) {
                        editor.putString("uri"+((requestCode%10)-5), uri.toString());
                        editor.commit();
                    } else {
                        editor.remove("uri"+((requestCode%10)-5));
                        editor.commit();
                    }
                    break;
                case 40435: case 40436: case 40437: case 40438: case 40439:
                    pref = getSharedPreferences(_AlarmSET.azanFile,MODE_PRIVATE);
                    editor = pref.edit();
                    uri = data.getData();
                    if(uri!=null) {
                        editor.putString("uri"+((requestCode%10)-5), uri.toString());
                        editor.commit();
                        String name= Uri.decode(uri.toString());
                        _SET.setDescription((ViewGroup) findViewById(requestCode)," "+ name.substring(name.lastIndexOf('/')+1));
                        Toast toast = Toast.makeText(this,getResources().getString(R.string.toast_tone_saved),Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();

                    } else {
                        editor.remove("uri"+((requestCode%10)-5));
                        editor.commit();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
