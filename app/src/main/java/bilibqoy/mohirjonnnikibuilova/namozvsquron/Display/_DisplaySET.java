package bilibqoy.mohirjonnnikibuilova.namozvsquron.Display;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

import java.text.NumberFormat;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class _DisplaySET {
    public final static String displayFile = "display";
    public static final int WHITE = 0, BLACK =1;
    public final static String lang = Locale.getDefault().getDisplayLanguage();

    public static void setLanguagePreferences(Context context) {
        firstTime(context);
        SharedPreferences pref = context.getSharedPreferences(displayFile,Context.MODE_PRIVATE);
        Configuration configuration = context.getResources().getConfiguration();
        Locale locale = new Locale(pref.getString("language",  lang));//Locale locale = new Locale(pref.getString("language","uz"));
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    public static Typeface getTypeFace(Context context) {
        SharedPreferences pref = context.getSharedPreferences(displayFile,Context.MODE_PRIVATE);
        switch (pref.getInt("font",0)){
            case 0: return pref.getString("language","uz").equals("ar")?
                    ResourcesCompat.getFont(context, R.font.general_arabic): ResourcesCompat.getFont(context, R.font.general);
            case 1: return Typeface.create("sans-serif",Typeface.NORMAL);
            case 2: return Typeface.create("sans-serif",Typeface.BOLD);
            default: return ResourcesCompat.getFont(context, R.font.general);
        }
    }
    public static NumberFormat getNumberFormat(Context context){
        SharedPreferences pref = context.getSharedPreferences(displayFile,Context.MODE_PRIVATE);
        switch (pref.getInt("numbers_language",0)){
            case 0: return NumberFormat.getInstance(new Locale("uz"));
            case 1: return NumberFormat.getInstance(new Locale("ar","EG"));
            default: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return NumberFormat.getInstance(context.getResources().getConfiguration().getLocales().get(0));
                } else return NumberFormat.getInstance(context.getResources().getConfiguration().locale);
        }
    }

    public static void firstTime(Context context){
        SharedPreferences pref = context.getSharedPreferences(displayFile,Context.MODE_PRIVATE);
        if(!pref.getBoolean("firsttime",true)) return;
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("firsttime",false);
        editor.putString("language","uz");
        editor.putInt("numbers_language",0);
        editor.putInt("theme",0);
        editor.putInt("font",0);
        for(int i=0;i<5;i++) editor.putInt("widget_theme_"+i,2);
        editor.commit();
    }

    public static boolean isTime24(Context context) {
        return context.getSharedPreferences(displayFile,MODE_PRIVATE).getBoolean("time24",false);
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(displayFile,MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        firstTime(context);
    }

    public static int getAppTheme(Context context) {
        return context.getSharedPreferences(displayFile,MODE_PRIVATE).getInt("theme", WHITE);
    }

    public static int getPrimaryColor(Context context) {
         return getAppTheme(context) == WHITE ?R.color.colorPrimaryWhite:R.color.colorPrimaryBlack;
    }

    public static String formatStringNumbers(Context context,String str) {
        NumberFormat nf = getNumberFormat(context);
        String s = "";
        for(int i=0;i<str.length();i++){
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9') s+= nf.format(Integer.parseInt(str.substring(i,i+1)));
            else s+= str.charAt(i);
        }
        return s;
    }
    public static void colorTextChildes(Context context, ViewGroup group, int color){
        for(int i=0;i<group.getChildCount();i++){
            if(group.getChildAt(i) instanceof TextView) ((TextView) group.getChildAt(i)).setTextColor(
                    context.getResources().getColor(color==WHITE?R.color.widgetBlack:R.color.widgetWhite)
            );
            else if(group.getChildAt(i) instanceof  ViewGroup) colorTextChildes(context, (ViewGroup) group.getChildAt(i),color);
        }
    }

    public static void tintWidgetUpComing(Context context,ViewGroup group) {
        switch (context.getSharedPreferences(displayFile,MODE_PRIVATE).getInt("widget_theme_1",2)){
            case 0:
              //  group.setBackgroundColor(context.getResources().getColor(R.color.widgetWhite));
               // group.setBackground(context.getResources().getDrawable(R.drawable.itembackground));
              //  colorTextChildes(context,group,WHITE);
                break;
            case 1:
              //  group.setBackgroundColor(context.getResources().getColor(R.color.widgetBlack));
              //  group.setBackground(context.getResources().getDrawable(R.drawable.itembackground));
              //  colorTextChildes(context,group,BLACK);
                break;
            default:
               // group.setBackgroundColor(context.getResources().getColor(getPrimaryColor(context)));
                break;
        }
    }

    public static void tintWidget(Context context,ViewGroup group) {
        switch (context.getSharedPreferences(displayFile,MODE_PRIVATE).getInt("widget_theme_0",2)){
            case 0:
               // group.setBackgroundColor(context.getResources().getColor(R.color.widgetWhite));
               // group.setBackground(context.getResources().getDrawable(R.drawable.itembackground));
              //  colorTextChildes(context,group,WHITE);
                break;
            case 1:
               // group.setBackgroundColor(context.getResources().getColor(R.color.widgetBlack));
               // group.setBackground(context.getResources().getDrawable(R.drawable.itembackground));
              //  colorTextChildes(context,group,BLACK);
                break;
            default:
               // group.setBackgroundColor(context.getResources().getColor(R.color.widgetColorSettingsBox));
               // group.setBackground(context.getResources().getDrawable(R.drawable.itembackground));
                break;
        }
    }

}
