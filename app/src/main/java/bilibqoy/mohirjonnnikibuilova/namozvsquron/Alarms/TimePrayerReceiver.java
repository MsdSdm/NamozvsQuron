package bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;

import androidx.core.app.AlarmManagerCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities.MainActivity;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Constants;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

import java.util.Calendar;


public class TimePrayerReceiver extends BroadcastReceiver {


    private String id;
    @Override
    public void onReceive(Context context, Intent intent) {
        _DisplaySET.setLanguagePreferences(context);
        int type = intent.getExtras().getInt("type");
        int index = intent.getExtras().getInt("index");
        if(_AlarmSET.isFireTimeGone(intent.getExtras().getLong("fire_time_in_millis"))) return;
        /* if(type == _AlarmSET.SAHOOR_REQUEST_CODE) { startSahoor(context); return; } */

        startSoundService(context, type, index);
        String[] content = type == _AlarmSET.AZAN_REQUEST_CODE ?
                context.getResources().getStringArray(R.array.prayer_time_notifications) :
                context.getResources().getStringArray(R.array.iqama_time_notifications);
        id = (type == _AlarmSET.AZAN_REQUEST_CODE) ? "azan" : "iqama";
        sendNotification(context, content[index == 0 ? 0 : (_AlarmSET.isJumuah(Calendar.getInstance()) && index == 2) ? 5 : index - 1]);
        setCancellation(context, type);

    }

    /*private void startSahoor(Context context) {
        Intent intent = new Intent(context,SahoorAlarmSnoozeService.class);
        context.startService(intent);
    }*/

    private void startSoundService(Context context, int type, int index) {
        Intent audioServiceIntent = new Intent(context,TimePrayerService.class);
        audioServiceIntent.putExtra("type",type);
        audioServiceIntent.putExtra("index",index);
        audioServiceIntent.putExtra("mode",true);
        context.startService(audioServiceIntent);
    }

    private void sendNotification(Context context,String msg) {
        _DisplaySET.setLanguagePreferences(context);
        NotificationManager alarmNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {

            NotificationChannel mChannel = alarmNotificationManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, msg, NotificationManager.IMPORTANCE_HIGH);
                mChannel.enableVibration(true);
                mChannel.setSound(null,null);
                alarmNotificationManager.createNotificationChannel(mChannel);
            }
        }



        NotificationCompat.Builder  builder = new NotificationCompat.Builder(context, id);
        Bitmap bitmap;
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.logo);
        bitmap = ((BitmapDrawable)drawable).getBitmap();
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle(Constants.APP_NAME)
                .setSmallIcon(R.drawable.logo_notifications)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(cancelIntent(context))
                .setTicker(Constants.APP_NAME)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(bitmap)
                .setContentIntent(resultPendingIntent);;







        if(_AlarmSET.isLEDActivated(context)) builder.setLights(0xff00ff00, 300, 100);
        alarmNotificationManager.notify(_AlarmSET.APP_NOTIFICATION_ID, builder.build());
    }

    private void setCancellation(Context context,int type){
        if(_AlarmSET.clearNotify(context,type)){
            AlarmManagerCompat.setExactAndAllowWhileIdle((AlarmManager) context.getSystemService(Context.ALARM_SERVICE),AlarmManager.RTC_WAKEUP,
                    _AlarmSET.getClearTimeInMillis(context,type),cancelIntent(context));
        }
    }
    private PendingIntent cancelIntent(Context context){
        Intent intent = new Intent(context,TimePrayerService.class);
        intent.putExtra("mode",false);
        intent.setAction(Long.toString(System.currentTimeMillis()));
        PendingIntent cIntent = PendingIntent.getService(
                context,0,intent,PendingIntent.FLAG_ONE_SHOT
        );
        return  cIntent;
    }

}

