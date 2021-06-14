package bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations._LocationSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Times._TimesSET;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class _AlarmSET {
    public static final String azanFile = "notifications";
    public static final String iqamaFile = "reminders";
    public static final String sahoorFile = "sahoor";
    public static final String silentFile = "silent";
    public static final int AZAN_REQUEST_CODE = 150;
    public static final int IQAMA_REQUEST_CODE = 160;
    public static final int SAHOOR_REQUEST_CODE = 140;
    public static final int SILENT_REQUEST_CODE = 190;
    public static final int SAHOOR_ALARM_TIME = -20;
    public static final int APP_NOTIFICATION_ID = 119;

    public static boolean notifyActivated(Context context) {
        return context.getSharedPreferences(azanFile, MODE_PRIVATE).getBoolean("notify",true);
    }
    public static boolean notifyActivatedFor(Context context,int i){
        if(isJumuah(Calendar.getInstance()) && i==2) return context.getSharedPreferences(azanFile, MODE_PRIVATE).getBoolean("notify_5",true);
        return context.getSharedPreferences(azanFile, MODE_PRIVATE).getBoolean("notify_"+(i>0?i-1:0),true);
    }

    public static boolean remindActivated(Context context) {
        return context.getSharedPreferences(iqamaFile, MODE_PRIVATE).getBoolean("remind",true);
    }
    public static boolean remindActivatedFor(Context context,int i){
        if(isJumuah(Calendar.getInstance()) && i==2) return context.getSharedPreferences(iqamaFile, MODE_PRIVATE).getBoolean("remind_5",true);
        return context.getSharedPreferences(iqamaFile, MODE_PRIVATE).getBoolean("remind_"+(i>0?i-1:0),true);
    }
    public static boolean silentActivated(Context context) {
        return context.getSharedPreferences(silentFile, MODE_PRIVATE).getBoolean("active",false);
    }
    public static boolean silentActivatedFor(Context context,int i){
        if(isJumuah(Calendar.getInstance()) && i==2) return context.getSharedPreferences(silentFile, MODE_PRIVATE).getBoolean("active_5",true);
        return context.getSharedPreferences(silentFile, MODE_PRIVATE).getBoolean("active_"+(i>0?i-1:0),true);
    }
    public static boolean sahoorActivated(Context context) {
        return context.getSharedPreferences(sahoorFile, MODE_PRIVATE).getBoolean("sahooralarm",false);
    }
    public static boolean isJumuah(Calendar cal) {
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }
    public static Calendar getNotifyTimeFor(Context context, int i) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(_TimesSET.times[i].substring(0,2)));
        cal.set(Calendar.MINUTE,Integer.parseInt(_TimesSET.times[i].substring(3,5)));
        cal.set(Calendar.SECOND,0);
        if(isJumuah(cal) && i==2) cal.add(Calendar.MINUTE,(-1)*context.getSharedPreferences(azanFile, MODE_PRIVATE).getInt("notification_time_5",0));
        else cal.add(Calendar.MINUTE,(-1)*context.getSharedPreferences(azanFile, MODE_PRIVATE).getInt("notification_time_"+(i>0?i-1:0),0));
        return cal;
    }
    public static Calendar getRemindTimeFor(Context context,int i) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(_TimesSET.times[i].substring(0,2)));
        cal.set(Calendar.MINUTE,Integer.parseInt(_TimesSET.times[i].substring(3,5)));
        cal.set(Calendar.SECOND,0);
        if(isJumuah(cal) && i==2) cal.add(Calendar.MINUTE,context.getSharedPreferences(iqamaFile, MODE_PRIVATE).getInt("remind_time_5", _TimesSET.iqamaTimes[i]));
        else cal.add(Calendar.MINUTE,context.getSharedPreferences(iqamaFile, MODE_PRIVATE).getInt("remind_time_"+(i>0?i-1:0), _TimesSET.iqamaTimes[i]));
        return cal;
    }
    public static Calendar getSilentTimeFor(Context context,int i) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(_TimesSET.times[i].substring(0,2)));
        cal.set(Calendar.MINUTE,Integer.parseInt(_TimesSET.times[i].substring(3,5)));
        cal.set(Calendar.SECOND,0);
        if(isJumuah(cal) && i==2) cal.add(Calendar.MINUTE,context.getSharedPreferences(silentFile, MODE_PRIVATE).getInt("silent_time_5", _TimesSET.iqamaTimes[i]));
        else cal.add(Calendar.MINUTE,context.getSharedPreferences(silentFile, MODE_PRIVATE).getInt("silent_time_"+(i>0?i-1:0), _TimesSET.iqamaTimes[i]));
        //Toast.makeText(context,cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE),Toast.LENGTH_SHORT).show();
        return cal;
    }
    public static Calendar getSilentWakeFor(Context context,int i) {
        Calendar cal = getSilentTimeFor(context,i);
        if(isJumuah(cal) && i==2) cal.add(Calendar.MINUTE,context.getSharedPreferences(silentFile, MODE_PRIVATE).getInt("silent_period_5",10));
        else cal.add(Calendar.MINUTE,context.getSharedPreferences(silentFile, MODE_PRIVATE).getInt("silent_period_"+(i>0?i-1:0),10));
        //Toast.makeText(context,cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE),Toast.LENGTH_SHORT).show();
        return cal;
    }
    public static Calendar getSahoorTime(Context context) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(_TimesSET.times[0].substring(0,2)));
        cal.set(Calendar.MINUTE,Integer.parseInt(_TimesSET.times[0].substring(3,5)));
        cal.set(Calendar.SECOND,0);
        cal.add(Calendar.MINUTE,context.getSharedPreferences(sahoorFile, MODE_PRIVATE).getInt("time",-20));
        return cal;
    }
    public static boolean clearNotify(Context context, int type) {
        return context.getSharedPreferences(type==AZAN_REQUEST_CODE?azanFile:iqamaFile, MODE_PRIVATE)
                .getBoolean(type==AZAN_REQUEST_CODE?"clear_notification":"clear_reminder",false);
    }
    public static long getClearTimeInMillis(Context context,int type) {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MINUTE,context.getSharedPreferences(type==AZAN_REQUEST_CODE?azanFile:iqamaFile, MODE_PRIVATE)
                .getInt("clear_time",10));
        return cal.getTimeInMillis();
    }
    public static void firstTime(Context context){
        SharedPreferences azan = context.getSharedPreferences(azanFile, MODE_PRIVATE);
        SharedPreferences.Editor azanEditor = azan.edit();
        SharedPreferences iqama = context.getSharedPreferences(iqamaFile, MODE_PRIVATE);
        SharedPreferences.Editor iqamaEditor = iqama.edit();
        SharedPreferences sahoor = context.getSharedPreferences(sahoorFile, MODE_PRIVATE);
        SharedPreferences.Editor sahoorEditor = sahoor.edit();
        SharedPreferences silent = context.getSharedPreferences(silentFile,MODE_PRIVATE);
        SharedPreferences.Editor silentEditor = silent.edit();
        azanEditor.putBoolean("firsttime",false);
        iqamaEditor.putBoolean("firsttime",false);
        azanEditor.putInt("notification_type",1);
        iqamaEditor.putInt("reminder_type",1);
        for(int i=0;i<5;i++) iqamaEditor.putInt("remind_time_"+i, _TimesSET.iqamaTimes[i>0?i+1:0]);
        for(int i=0;i<5;i++) silentEditor.putInt("silent_time_"+i, _TimesSET.iqamaTimes[i>0?i+1:0]);
        for(int i=0;i<6;i++) silentEditor.putInt("silent_period_"+i,10);
        iqamaEditor.putInt("remind_time_5", _TimesSET.iqamaTimes[2]);
        silentEditor.putInt("silent_time_5", _TimesSET.iqamaTimes[2]);
        sahoorEditor.putInt("time",SAHOOR_ALARM_TIME);
        sahoorEditor.putInt("stop_time",20);
        sahoorEditor.putInt("snooze_time",5);
        azanEditor.commit();
        iqamaEditor.commit();
        sahoorEditor.commit();
        silentEditor.commit();
    }
    public static boolean isFirstTime(Context context){
        return context.getSharedPreferences(azanFile, MODE_PRIVATE).getBoolean("firsttime",true);
    }

    public static boolean isOnAlarmVibrateActivated(Context context) {
        return context.getSharedPreferences(azanFile, MODE_PRIVATE).getBoolean("vibrate",false);
    }

    public static boolean isLEDActivated(Context context) {
        return context.getSharedPreferences(azanFile, MODE_PRIVATE).getBoolean("led",false);
    }

    public static boolean isSahoorVibrateActivated(Context context) {
        return context.getSharedPreferences(sahoorFile, MODE_PRIVATE).getBoolean("vibrate",true);
    }

    public static boolean isSilentVibrateActivated(Context context) {
        return context.getSharedPreferences(silentFile, MODE_PRIVATE).getBoolean("vibrate",true);
    }
    public static boolean isOnSilentVibrateActivated(Context context) {
        return context.getSharedPreferences(silentFile, MODE_PRIVATE).getBoolean("vibrate_on_set",true);
    }
    public static boolean isOnSilentMsgActivated(Context context) {
        return context.getSharedPreferences(silentFile, MODE_PRIVATE).getBoolean("msg",true);
    }
    public static boolean isSahoorKeepScreenOn(Context context) {
        return context.getSharedPreferences(sahoorFile, MODE_PRIVATE).getBoolean("screen_on",true);
    }

    public static boolean isSahoorWorkOnSilent(Context context) {
        return context.getSharedPreferences(sahoorFile, MODE_PRIVATE).getBoolean("work_on_silent",true);
    }

    public static boolean isSahoorTimeUp(Context context) {
        if(context.getSharedPreferences(sahoorFile, MODE_PRIVATE).getBoolean("stop_automatically",false))
            return false;
        Calendar cal = Calendar.getInstance();
        Calendar fajr = _AlarmSET.getNotifyTimeFor(context,0);
        return (cal.getTimeInMillis() - fajr.getTimeInMillis()) > getSahoorStopTimeInMillis(context);
    }

    public static long getSahoorStopTimeInMillis(Context context) {
        return context.getSharedPreferences(sahoorFile, MODE_PRIVATE).getInt("stop_time",20)*60000;
    }

    public static long getSahoorSnoozeTimeInMillis(Context context) {
        return context.getSharedPreferences(_AlarmSET.sahoorFile,MODE_PRIVATE).getInt("snooze_time",5)*60000;
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(azanFile,MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        editor = context.getSharedPreferences(iqamaFile,MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        editor = context.getSharedPreferences(sahoorFile,MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        editor = context.getSharedPreferences(silentFile,MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        firstTime(context);
    }

    public static Uri getRingtoneUri(Context context,int alarmType) {
        SharedPreferences preferences = context.getSharedPreferences(alarmType==AZAN_REQUEST_CODE?azanFile:iqamaFile,MODE_PRIVATE);
        return Uri.parse(preferences.getString("uri",RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString()));
    }

    public static Uri getRingtoneUri(Context context, int alarmType, int id) {
        SharedPreferences preferences = context.getSharedPreferences(azanFile,MODE_PRIVATE);
        return Uri.parse(preferences.getString("uri" + id,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString()));
    }

    public static void handleVibration(final Context context) {
        final Handler handlerVibration = new Handler();
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(200);
        }
        handlerVibration.postDelayed(new Runnable() {
            @Override
            public void run() {
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    v.vibrate(500);
                }
            }
        },800);
    }

    public static boolean isFireTimeGone(long fireTimeInMillis) {
        /* this method prevent alarms to run at time set */
        Calendar calendar = Calendar.getInstance();
        return (calendar.getTimeInMillis() - fireTimeInMillis) > 30000;
    }


    public static void setPowerConceptions(final Activity activity) {
        if(activity.getSharedPreferences(azanFile,MODE_PRIVATE).getBoolean("is_power_conceptions_shown",false)
        || !_LocationSET.isLocationAssigned(activity)) return;
        SharedPreferences.Editor editor = activity.getSharedPreferences(azanFile,MODE_PRIVATE).edit();
        editor.putBoolean("is_power_conceptions_shown",true).commit();
        PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        final Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                    != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS},
                        AZAN_REQUEST_CODE);
                editor.putBoolean("is_power_conceptions_shown",false).commit();
                return;
            }
            if (!pm.isIgnoringBatteryOptimizations(activity.getPackageName())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(activity.getResources().getString(R.string.alert_ignore_battery_opt_msg));
                builder.setPositiveButton(activity.getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        activity.startActivity(intent);
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        }
    }

    public static void setAlarmPermissions(final Activity activity){
        if(!_LocationSET.isLocationAssigned(activity) /* Due to not conflicts with first location's updating!! */
                || activity.getSharedPreferences(azanFile,MODE_PRIVATE).getBoolean("alarm_permissions",false)) return;
        SharedPreferences.Editor editor = activity.getSharedPreferences(azanFile,MODE_PRIVATE).edit();
        editor.putBoolean("alarm_permissions",true).commit();
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WAKE_LOCK},
                    AZAN_REQUEST_CODE);

        }
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_BOOT_COMPLETED)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},
                    AZAN_REQUEST_CODE);

        }
    }
}
