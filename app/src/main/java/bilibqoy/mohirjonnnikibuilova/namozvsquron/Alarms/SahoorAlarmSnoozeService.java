package bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.Nullable;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities.SahoorActivity;

public class SahoorAlarmSnoozeService extends Service {
    public static boolean isSnoozed = false;
    public static boolean isStopped = false;
    private Handler handler;
    private SahoorActivity sahoorActivity;
    private long snoozeStart;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        snoozeStart = -1;
        sahoorActivity = null;
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(_AlarmSET.isSahoorTimeUp(SahoorAlarmSnoozeService.this) || isStopped){
                    if(sahoorActivity!=null && !isStopped) sahoorActivity.finish();
                    stopSelf();
                }else{
                    if(isSnoozed){
                        if(snoozeStart == -1){
                            snoozeStart = System.currentTimeMillis();
                            sahoorActivity = null;
                        }else if((System.currentTimeMillis()- snoozeStart) > _AlarmSET.getSahoorSnoozeTimeInMillis(SahoorAlarmSnoozeService.this)){
                            isSnoozed = false;
                            snoozeStart = -1;
                        }
                    }
                    else if(sahoorActivity!=null) vibrate();
                    else{
                        sahoorActivity = new SahoorActivity();
                        Intent intent = new Intent(SahoorAlarmSnoozeService.this,sahoorActivity.getClass());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        SahoorAlarmSnoozeService.this.startActivity(intent);
                    }
                    handler.postDelayed(this,1000);
                }
            }
        },1000);
        return super.onStartCommand(intent,flags,startId);
    }

    private void vibrate() {
        if (_AlarmSET.isSahoorVibrateActivated(this)) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(500);
            }
        }
    }
}
