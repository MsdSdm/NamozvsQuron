package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings._SET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Times._TimesSET;

import java.text.NumberFormat;

import static bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings.SettingsRecyclerViewAdapter.MANUAL_TIMES_ADJUSTMENTS_LAYOUT_NAM;

public class TimePointSettingsActivity extends AppCompatActivity {

    private int index;
    private Handler handler;
    private Runnable runnable;

    public static void setAlarmActivated(Context context, String type, int index, boolean isActivated) {
        SharedPreferences pref = context.getSharedPreferences(type + ".txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Integer.toString(index),isActivated);
        editor.commit();
    }

    public static boolean isAlarmActivated(Context context, String type, int index){
        return context.getSharedPreferences(type + ".txt", MODE_PRIVATE)
                .getBoolean(Integer.toString(index),true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_time_point_settings);
        Toolbar toolbar = findViewById(R.id.toolbar_time_point_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        index=getIntent().getExtras().getInt("TimePointIndex");
        getSupportActionBar().setTitle( getResources().getStringArray(R.array.prayer_time)[index] +" "+ getResources().getString(R.string.prayer_time_settings));

        _SET.startSettings(this);
        widgetsAdder();
        startTimer();
    }

    public void widgetsAdder(){
        /* Azan widget configuration*/
        final LinearLayout azanBox = findViewById(R.id.time_point_settings_azan_switch_box);

        ((TextView) azanBox.findViewById(R.id.switch_box_text)).setText(getResources().getString(R.string.activate_azan_notifications));
        ((TextView) azanBox.findViewById(R.id.configuration_text)).setText(getResources().getString(R.string.choose_alarm_tone));
        ((SwitchCompat) azanBox.findViewById(R.id.switch_box_trigger)).setChecked(isAlarmActivated(this,"azan",index));
        ((LinearLayout) azanBox.findViewById(R.id.switch_box_configuration_box)).setVisibility(
                ((SwitchCompat) azanBox.findViewById(R.id.switch_box_trigger)).isChecked()? View.VISIBLE:View.GONE);
        ((SwitchCompat) azanBox.findViewById(R.id.switch_box_trigger)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                setAlarmActivated(getApplicationContext(),"azan",index,((SwitchCompat) azanBox.findViewById(R.id.switch_box_trigger)).isChecked());
                ((LinearLayout) azanBox.findViewById(R.id.switch_box_configuration_box)).setVisibility(
                        ((SwitchCompat) azanBox.findViewById(R.id.switch_box_trigger)).isChecked()? View.VISIBLE:View.GONE);
            }
        });
        ((ImageButton) azanBox.findViewById(R.id.configuration_button)).setImageResource(R.drawable.ic_library_music_black_24dp);

        /* Iqama widget configuration*/
        final LinearLayout iqamaBox = findViewById(R.id.time_point_settings_iqama_switch_box);


        ((TextView) iqamaBox.findViewById(R.id.switch_box_text)).setText(getResources().getString(R.string.activate_iqama_notifications));
        ((TextView) iqamaBox.findViewById(R.id.configuration_text)).setText(getResources().getString(R.string.choose_alarm_tone));
        ((SwitchCompat) iqamaBox.findViewById(R.id.switch_box_trigger)).setChecked(isAlarmActivated(this,"iqama",index));
        ((LinearLayout) iqamaBox.findViewById(R.id.switch_box_configuration_box)).setVisibility(
                ((SwitchCompat) iqamaBox.findViewById(R.id.switch_box_trigger)).isChecked()? View.VISIBLE:View.GONE);
        ((SwitchCompat) iqamaBox.findViewById(R.id.switch_box_trigger)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                setAlarmActivated(getApplicationContext(),"iqama",index,((SwitchCompat) iqamaBox.findViewById(R.id.switch_box_trigger)).isChecked());
                ((LinearLayout) iqamaBox.findViewById(R.id.switch_box_configuration_box)).setVisibility(
                        ((SwitchCompat) iqamaBox.findViewById(R.id.switch_box_trigger)).isChecked()? View.VISIBLE:View.GONE);
            }
        });
        ((ImageButton) iqamaBox.findViewById(R.id.configuration_button)).setImageResource(R.drawable.ic_library_music_black_24dp);


        /* Adjust times widget*/
        final LinearLayout adjustTimesBox = findViewById(R.id.time_point_settings_adjust_times);
        ((ImageButton) adjustTimesBox.findViewById(R.id.configuration_button)).setImageResource(R.drawable.ic_edit_black_24dp);
        ((TextView) adjustTimesBox.findViewById(R.id.configuration_text)).setText(getResources().getString(R.string.adjust_time_manually));
        adjustTimesBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimePointSettingsActivity.this, SettingsThirdActivity.class);
                intent.putExtra("title",((TextView) adjustTimesBox.findViewById(R.id.configuration_text)).getText().toString());
                intent.putExtra("index",MANUAL_TIMES_ADJUSTMENTS_LAYOUT_NAM);
                TimePointSettingsActivity.this.startActivity(intent);
            }
        });

    }



    public void startTimer(){
        handler = new Handler();
        final NumberFormat nf = _DisplaySET.getNumberFormat(this);
        runnable = new Runnable() {
            @Override
            public void run() {
                int h,m,s;
                int remainTime = (int) ((_TimesSET.getPrayerTimeMillis(index,false) - System.currentTimeMillis())/1000);
                if(remainTime < 1)
                    findViewById(R.id.countdown_layout_time_point_settings).setVisibility(View.GONE);
                else {
                    h = (int)remainTime /3600;
                    m = (int)((remainTime-(h*3600))/60);
                    s = (int)remainTime - (h*3600) - (m*60);
                    ViewGroup countdown = findViewById(R.id.countdown_timer_time_point_settings);
                    ((TextView)(countdown.getChildAt(0))).setText(nf.format(h));
                    ((TextView)(countdown.getChildAt(2))).setText(nf.format(m));
                    ((TextView)(countdown.getChildAt(4))).setText(nf.format(s));
                    handler.postDelayed(this,1000);
                }
            }
        };
        runnable.run();
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
}
