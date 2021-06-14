package bilibqoy.mohirjonnnikibuilova.namozvsquron.Times;


import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.IslamicCalendar;
import android.os.Build;

import androidx.annotation.RequiresApi;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations._LocationSET;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.content.Context.MODE_PRIVATE;

public class _TimesSET {

    public final static String prayersFile = "prayer_times";
    public final static String adjustTimesFile = "adjust_times";

    public static String[] times = {"04:20","05:41","03:35","15:35","18:63","20:10"};
    public static int[] iqamaTimes = {30,0,20,20,5,5};
    public static void initializeTimesForCurrent(Context context){
        times = initializeTimesFor(context, _LocationSET.currentLocation,Calendar.getInstance());
    }
    public static String[] initializeTimesFor(Context context,String locationFile, Calendar time){
        firstTime(context);
        String[] str = {"","","","","",""};
        SharedPreferences pref = context.getSharedPreferences(locationFile,MODE_PRIVATE);
        float latitude = pref.getFloat("latitude",0);
        float longitude = pref.getFloat("longitude",0);
        float offset = pref.getFloat("offset",0) + (locationFile.equals(_LocationSET.currentLocation) ?
                getDstOffset(context):_LocationSET.getTimeDstSaving(pref.getString("timezone",""),time));
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(prayers.Time24);
        prayers.setCalcMethod(getCalcMethod(context,prayers));
        prayers.setAsrJuristic(getAsrJuristic(context));
        prayers.setAdjustHighLats(getAdjustHighLats(context,prayers));
        int[] offsets = {getAdjust(context,0) ,
                            getAdjust(context,1) ,
                            getAdjust(context,2) ,
                            getAdjust(context,3) ,
                            0, //sunset
                            getAdjust(context,4) ,
                            getAdjust(context,5)  + (adjustIshaRamadan(context,prayers)?30:0)
        }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        ArrayList<String> prayerTimes = prayers.getPrayerTimes(time,
                latitude, longitude, offset);
        ArrayList<String> prayerNames = prayers.getTimeNames();

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < prayerTimes.size(); i++) {
            System.out.println(prayerNames.get(i) + "" + prayerTimes.get(i));// " - "
            res.append(prayerNames.get(i) + "" + prayerTimes.get(i)+"\n");// " - "
        }
        str[0]=prayerTimes.get(0);
        str[1]=prayerTimes.get(1);
        str[2]=(    isJumuahTimeDiff(context)
                    &&time.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY
                    && locationFile.equals(_LocationSET.currentLocation))?
                getJumuahTime(context):prayerTimes.get(2);
        str[3]=prayerTimes.get(3);
        /* prayerTimes.get(4) is for sunset*/
        str[4]=prayerTimes.get(5);
        str[5]=prayerTimes.get(6);
        return str;
    }

    private static float getDstOffset(Context context) {
        return context.getSharedPreferences(prayersFile,MODE_PRIVATE).getInt("dst",0);
    }

    public static String getJumuahTime(Context context) {
        return context.getSharedPreferences(prayersFile,MODE_PRIVATE).getString("jumuah_time","11:00");
    }
    public static void setJumuahTime(Context context,int i, int i1) {
        SharedPreferences preferences = context.getSharedPreferences(prayersFile,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String hours = i<10? ("0" + i): String.valueOf(i);
        String minutes = i1<10? ("0" + i1):String.valueOf(i1);
        editor.putString("jumuah_time",hours+":"+minutes);
        editor.commit();
    }
    public static boolean isJumuahTimeDiff(Context context) {
        return context.getSharedPreferences(prayersFile,MODE_PRIVATE).getBoolean("jumuah_different",false);
    }
    public static void setJumuahDifferent(Context context, boolean b) {
        SharedPreferences preferences = context.getSharedPreferences(prayersFile,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("jumuah_different",b);
        editor.commit();
    }


    private static boolean adjustIshaRamadan(Context context, PrayTime prayers) {
        UmmalquraCalendar calendar = getUmmalquraCalendar(context);
        return context.getSharedPreferences(prayersFile, MODE_PRIVATE).getInt("method", prayers.MWL) == prayers.Makkah
                && context.getSharedPreferences(prayersFile, MODE_PRIVATE).getBoolean("adjust_isha_in_ramadan", false)
                && calendar.get(Calendar.MONTH) == UmmalquraCalendar.RAMADHAN;
    }

    private static int getAdjust(Context context, int i) {
        return context.getSharedPreferences(adjustTimesFile,MODE_PRIVATE).getInt("adjust_"+i,0);
    }

    private static int getCalcMethod(Context context, PrayTime time){
        return context.getSharedPreferences(prayersFile,MODE_PRIVATE).getInt("method",time.MWL);
    }
    private static int getAdjustHighLats(Context context, PrayTime prayers) {
        if(context.getSharedPreferences(prayersFile,MODE_PRIVATE).getInt("method",prayers.MWL) == 7)
            return prayers.OneSeventh;
        else if(context.getSharedPreferences(prayersFile,MODE_PRIVATE).getInt("method",prayers.MWL) == 4)
            return prayers.None;
        return context.getSharedPreferences(prayersFile,MODE_PRIVATE).getBoolean("angle_based_method",false)?
                prayers.AngleBased:prayers.None;
    }
    private static int getAsrJuristic(Context context) {
        return context.getSharedPreferences(prayersFile,MODE_PRIVATE).getInt("asr_method",0);
    }
    private static void firstTime(Context context){
        SharedPreferences preferences = context.getSharedPreferences(prayersFile,MODE_PRIVATE);
        SharedPreferences adjust = context.getSharedPreferences(adjustTimesFile,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        SharedPreferences.Editor adjustEditor = adjust.edit();
        if(!preferences.getBoolean("firsttime",true)) return;
        editor.putBoolean("firsttime",false);
        editor.putInt("method",3);
        editor.putInt("asr_method",0);
        for (int i=0;i<6;i++) adjustEditor.putInt("adjust_"+i,0);
        editor.putInt("dst",0);
        editor.putString("jumuah_time","11:00");
        adjustEditor.putInt("hijri_adjust",0);
        editor.commit();
        adjustEditor.commit();
    }


    public static void updateTimes(Context context){
        if(!_LocationSET.isLocationAssigned(context)) return;
        _LocationSET.updateDstToCurrent(context);
        initializeTimesForCurrent(context);
    }

    public static int comingTimePointIndex(){
        int i = 0;
        while(System.currentTimeMillis() > getPrayerTimeMillis(i,false)) if(++i == 6) break;
        return i<6?i:0;
    }

    public static String getPrayerTimeString(Context context, int i){
        NumberFormat nf = _DisplaySET.getNumberFormat(context);
        int h = Integer.parseInt(times[i].substring(0, 2));
        int m = Integer.parseInt(times[i].substring(3, 5));
        String H = nf.format(!_DisplaySET.isTime24(context)&&h>12?h-12:h);
        String M = nf.format(m);
        H = H.length()>1?H:nf.format(0)+H;
        M = M.length()>1?M:nf.format(0)+M;
        return H + ":" + M;
    }

    public static String getPrayerPhaseString(Context context, int i){
        if(_DisplaySET.isTime24(context)) return "";
        else return Integer.parseInt(times[i].substring(0, 2))>12?"PM":"AM";
    }

    public static long getPrayerTimeMillis(int i,boolean upComing) {
       Calendar cal = new GregorianCalendar();
       long nowMillis = cal.getTimeInMillis();
       cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(times[i].substring(0, 2)));
       cal.set(Calendar.MINUTE,Integer.parseInt(times[i].substring(3, 5)));
       cal.set(Calendar.SECOND,0);
        if(nowMillis > cal.getTimeInMillis() && upComing) cal.add(Calendar.DATE,1);// For upcoming Fajr after Isha
       return cal.getTimeInMillis();
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(prayersFile,MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        editor = context.getSharedPreferences(adjustTimesFile,MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        firstTime(context);
    }
    public static UmmalquraCalendar getUmmalquraCalendar(Context context){
        UmmalquraCalendar calendar = new UmmalquraCalendar();
        calendar.add(Calendar.DATE,context.getSharedPreferences(adjustTimesFile,MODE_PRIVATE).getInt("hijri_adjust",0));
        return calendar;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static IslamicCalendar getGoogleCalendar(Context context){
            IslamicCalendar calendar = new IslamicCalendar();
            calendar.add(Calendar.DATE,context.getSharedPreferences(adjustTimesFile,MODE_PRIVATE).getInt("hijri_adjust",0));
            return calendar;
    }
    public static boolean isItGoogleCalendar(Context context){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N &&
                context.getSharedPreferences(adjustTimesFile,MODE_PRIVATE).getBoolean("is_google_cal",true);
    }

    public static void setDstDefault(Context context) {
        SharedPreferences pref = context.getSharedPreferences(prayersFile,MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("dst", (int) context.getSharedPreferences(_LocationSET.currentLocation,MODE_PRIVATE).getFloat("auto_dst",0));
        editor.commit();
    }
}