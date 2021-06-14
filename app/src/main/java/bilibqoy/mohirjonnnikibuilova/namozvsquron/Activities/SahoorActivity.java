package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms.SahoorAlarmSnoozeService;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms._AlarmSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations._LocationSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Times._TimesSET;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SahoorActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _LocationSET.checkCurrentLocation(this);
        _TimesSET.updateTimes(this);
        _DisplaySET.setLanguagePreferences(this);
        MainActivity.reloadMainActivityOnResume = false;
        setContentView(R.layout.activity_sahoor);
        ((AppCompatTextView) findViewById(R.id.sahoor_activity_fajr_time)).setText(getResources().getString(R.string.sahoor_activty_fajr_time_apex)
                + _TimesSET.getPrayerTimeString(this,0) + " " + _TimesSET.getPrayerPhaseString(this,0));
        ((Button) findViewById(R.id.sahoor_activity_middle_ball)).setText((new SimpleDateFormat("hh:mm aa")).format(new Date()));
        initializeDateViews();
        startRingtonePlayer();
        keepScreenOn();
        findViewById(R.id.sahoor_activity_snooze_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snooze();
            }
        });
        findViewById(R.id.sahoor_activity_stop_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
    }


    private void keepScreenOn() {
        final Window win= getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        if(_AlarmSET.isSahoorKeepScreenOn(this)){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }



    private void stop() {
        player.stop();
        player = null;
        SahoorAlarmSnoozeService.isStopped = true;
        finish();
    }

    private void snooze() {
        player.stop();
        player = null;
        SahoorAlarmSnoozeService.isSnoozed = true;
        finish();
    }

    private void startRingtonePlayer() {
        if(checkSilence()) return;
        if(player != null) return;
        player = new MediaPlayer();
        player.setOnPreparedListener(this);
        player.setLooping(true);
        Uri uri = Uri.parse(getSharedPreferences(_AlarmSET.sahoorFile,MODE_PRIVATE).getString("uri",
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString()));
        setAudioAttributes();
        try {
            player.setDataSource(this,uri);
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkSilence() {
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if(am.getRingerMode() == AudioManager.RINGER_MODE_SILENT ||am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE)
            return !_AlarmSET.isSahoorWorkOnSilent(this);
        return false;
    }

    private void setAudioAttributes() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            player.setAudioAttributes(attributes);
        }else{
            player.setAudioStreamType(AudioManager.STREAM_ALARM);
        }
    }

    public void initializeDateViews() {
        UmmalquraCalendar hijCal = _TimesSET.getUmmalquraCalendar(this);
        Calendar cal = Calendar.getInstance();
        NumberFormat nf = _DisplaySET.getNumberFormat(this);
        ((TextView) findViewById(R.id.sahoor_activity_hijri_month_number)).setText(nf.format(hijCal.get(UmmalquraCalendar.DAY_OF_MONTH)));
        ((TextView) findViewById(R.id.sahoor_activity_hijri_month_name)).setText(getResources().getStringArray(R.array.hijri_month)[hijCal.get(UmmalquraCalendar.MONTH)]);
        ((TextView) findViewById(R.id.sahoor_activity_hijri_month_name)).setTypeface(_DisplaySET.getTypeFace(this));
        ((TextView) findViewById(R.id.sahoor_activity_gregorian_month_number)).setText(nf.format(cal.get(Calendar.DAY_OF_MONTH)));
        ((TextView) findViewById(R.id.sahoor_activity_gregorian_month_name)).setText(getResources().getStringArray(R.array.gregorian_month)[cal.get(Calendar.MONTH)]);
        ((TextView) findViewById(R.id.sahoor_activity_gregorian_month_name)).setTypeface(_DisplaySET.getTypeFace(this));
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        player.start();
    }
}
