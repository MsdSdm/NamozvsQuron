package bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

public class TimePrayerService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener {
    private MediaPlayer player;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!checkDestroy(intent)) {
            player = new MediaPlayer();
            player.setOnPreparedListener(this);
            player.setOnCompletionListener(this);
            Uri uri = getUriFor(intent.getExtras().getInt("type"),intent.getExtras().getInt("index"));
            if(uri!=null) { // if phone's state is silent, uri will be null for tone's type 1 & 2
                try {
                    player.setDataSource(this, uri);
                    player.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(_AlarmSET.isOnAlarmVibrateActivated(this))
                _AlarmSET.handleVibration(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }



    private boolean checkDestroy(Intent intent) {
        if (intent.getExtras().getBoolean("mode")) return false;
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(_AlarmSET.APP_NOTIFICATION_ID);
        onCompletion(player);
        return true;
    }

    @Override
    public void onDestroy() {
        onCompletion(null);
        super.onDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if(player !=null && player.isPlaying()) player.stop();
        stopSelf();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        player.start();
    }

    private Uri getUriFor(int type, int index) {
        Uri uri = null;
        switch (type==_AlarmSET.AZAN_REQUEST_CODE?
                getSharedPreferences(_AlarmSET.azanFile,MODE_PRIVATE).getInt("notification_type",1):
                getSharedPreferences(_AlarmSET.iqamaFile,MODE_PRIVATE).getInt("reminder_type",1)){
            case 0:
                if(((AudioManager) getSystemService(Context.AUDIO_SERVICE)).getRingerMode()== AudioManager.RINGER_MODE_NORMAL)
                uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                break;
            case 1:
                if(((AudioManager) getSystemService(Context.AUDIO_SERVICE)).getRingerMode()== AudioManager.RINGER_MODE_NORMAL)
                uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                break;
            case 2:
                uri = getMultimediaUriFor(type,index);
                break;
            case 3:
                uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                break;
            default:
                uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                break;
        }
        return uri;
    }

    private Uri getMultimediaUriFor(int type, int index) {
        Uri uri = null;
        if(type==_AlarmSET.AZAN_REQUEST_CODE && getSharedPreferences(_AlarmSET.azanFile,MODE_PRIVATE).getBoolean("use_different",false)){
            uri = Uri.parse(getSharedPreferences(_AlarmSET.azanFile,MODE_PRIVATE).getString("uri"+(index>0?index-1:0),getDefaultMultimediaString())); //index 1 for sunrise
        }else if(type==_AlarmSET.AZAN_REQUEST_CODE){
            uri = Uri.parse(getSharedPreferences(_AlarmSET.azanFile,MODE_PRIVATE).getString("uri",getDefaultMultimediaString()));
        }else{
            uri = Uri.parse(getSharedPreferences(_AlarmSET.iqamaFile,MODE_PRIVATE).getString("uri",getDefaultMultimediaString()));
        }
        return uri;
    }
    private String getDefaultMultimediaString(/*int type*/){
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString();
        /* "android.resource://"+getPackageName()+"/raw/" + (type==_AlarmSET.AZAN_REQUEST_CODE?"azan":"iqama"); */
    }

}
