package bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms._AlarmSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Times._TimesSET;

import static android.content.Context.MODE_PRIVATE;

public class _SET {
    public final static String settingsFile = "settings";
    private static SharedPreferences settingsPref;
    private static SharedPreferences.Editor settingsEditor;
    public static void startSettings(Context settingsAc){
        settingsPref = settingsAc.getSharedPreferences(settingsFile,MODE_PRIVATE);
        settingsEditor = settingsPref.edit();
        if(settingsPref.getBoolean("firsttime",true)) firstTime();
    }



    public static boolean isActivated(View view){
        return settingsPref.getBoolean("status" + view.getId(),true);
    }
    public static void setStatus(View view){
        view.setClickable(isActivated(view));
        view.setAlpha(isActivated(view)?1.0f:0.2f);
    }
    public static void setStatus(Activity activity, int id, boolean bool) {
        settingsEditor.putBoolean("status"+id,bool);
        settingsEditor.commit();
        if(activity.findViewById(id) != null){
            activity.findViewById(id).setClickable(false);
            activity.findViewById(id).setAlpha(bool?1.0f:0.2f);
        }
    }
    public static boolean isChecked(View view) {
        return settingsPref.getBoolean("checked"+view.getId(),false);
    }
    public static boolean isChecked(int id) {
        return settingsPref.getBoolean("checked"+id,false);
    }
    public static void setCheckBox(ViewGroup view) {
        CheckBox box = (CheckBox) view.getChildAt(2);
        box.setChecked(isChecked(view));
    }
    public static void setCheckBox(ViewGroup view,boolean bool) {
        CheckBox box = (CheckBox) view.getChildAt(2);
        box.setChecked(bool);
        settingsEditor.putBoolean("checked"+view.getId(),bool);
        settingsEditor.commit();
    }
    public static void setCheckBox(Activity activity, int id, boolean bool) {
        settingsEditor.putBoolean("checked"+id,bool);
        settingsEditor.commit();
        if(activity.findViewById(id)!=null){
            CheckBox box = (CheckBox) ((ViewGroup)activity.findViewById(id)).getChildAt(2);
            box.setChecked(bool);
        }
    }
    @SuppressLint("ResourceType")
    public static void setDescription(ViewGroup group) {
        ((TextView) group.getChildAt(1)).setText(((TextView) group.getChildAt(1)).getText().toString() +settingsPref.getString("description"+group.getId(),""));
        if(group.getId() == 40403 || (group.getId()/10) == 4043 || group.getId() == 41003){
            if(settingsPref.getString("description"+group.getId(),"").equals(""))
                ((TextView) group.getChildAt(1)).setText(group.getContext().getResources().getString(R.string.no_tone_set));
        }
    }
    public static void setDescription(ViewGroup group,String description) {
        String lastDesc = settingsPref.getString("description"+group.getId(),"");
        String apex = ((TextView) group.getChildAt(1)).getText().toString().replace(lastDesc,"");
        settingsEditor.putString("description"+group.getId(),description);
        settingsEditor.commit();
        ((TextView) group.getChildAt(1)).setText(apex + description);
    }
    public static void setDescription(Activity activity,int id,String description) {
        ViewGroup group = activity.findViewById(id);
        if(group != null) {
            String lastDesc = settingsPref.getString("description" + group.getId(), "");
            String apex = ((TextView) group.getChildAt(1)).getText().toString().replace(lastDesc, "");
            ((TextView) group.getChildAt(1)).setText(apex + description);
        }
        settingsEditor.putString("description"+id,description);
        settingsEditor.commit();
    }

    private static void firstTime() {
        settingsEditor.putBoolean("firsttime",false);
        settingsEditor.putBoolean("checked40200",true);
        settingsEditor.putBoolean("checked40500",false);
        settingsEditor.putBoolean("status40600",false);
        settingsEditor.putString("description40600","10");
        settingsEditor.putBoolean("checked40800",true);
        settingsEditor.putBoolean("checked41100",false);
        settingsEditor.putBoolean("status41200",false);
        settingsEditor.putString("description41200","10");
        settingsEditor.putBoolean("checked41300",false);
        settingsEditor.putBoolean("checked41400",false);
        settingsEditor.putBoolean("checked40102",true);
        settingsEditor.putBoolean("checked40104",true);
        settingsEditor.putBoolean("checked40106",true);
        settingsEditor.putBoolean("checked40108",true);
        settingsEditor.putBoolean("checked40110",true);
        settingsEditor.putBoolean("checked40112",true);
        settingsEditor.putString("description40101","0");
        settingsEditor.putString("description40103","0");
        settingsEditor.putString("description40105","0");
        settingsEditor.putString("description40107","0");
        settingsEditor.putString("description40109","0");
        settingsEditor.putString("description40111","0");
        settingsEditor.putBoolean("checked40702",true);
        settingsEditor.putBoolean("checked40704",true);
        settingsEditor.putBoolean("checked40706",true);
        settingsEditor.putBoolean("checked40708",true);
        settingsEditor.putBoolean("checked40710",true);
        settingsEditor.putBoolean("checked40712",true);
        settingsEditor.putString("description40701", Integer.toString(_TimesSET.iqamaTimes[0]));
        settingsEditor.putString("description40703",Integer.toString(_TimesSET.iqamaTimes[2]));
        settingsEditor.putString("description40705",Integer.toString(_TimesSET.iqamaTimes[3]));
        settingsEditor.putString("description40707",Integer.toString(_TimesSET.iqamaTimes[4]));
        settingsEditor.putString("description40709",Integer.toString(_TimesSET.iqamaTimes[5]));
        settingsEditor.putString("description40711",Integer.toString(_TimesSET.iqamaTimes[2]));
        settingsEditor.putBoolean("checked40402",false);
        settingsEditor.putBoolean("status40403",false);
        settingsEditor.putBoolean("checked40404",false);
        settingsEditor.putBoolean("status40405",false);
        settingsEditor.putBoolean("status40406",false);
        settingsEditor.putBoolean("status40407",false);
        settingsEditor.putBoolean("status40408",false);
        settingsEditor.putBoolean("status40409",false);
        settingsEditor.putBoolean("checked41002",false);
        settingsEditor.putBoolean("status41003",false);
        settingsEditor.putBoolean("checked40425",false);
        settingsEditor.putBoolean("status40435",false);
        settingsEditor.putBoolean("checked40426",false);
        settingsEditor.putBoolean("status40436",false);
        settingsEditor.putBoolean("checked40427",false);
        settingsEditor.putBoolean("status40437",false);
        settingsEditor.putBoolean("checked40428",false);
        settingsEditor.putBoolean("status40438",false);
        settingsEditor.putBoolean("checked40429",false);
        settingsEditor.putBoolean("status40439",false);
        settingsEditor.putBoolean("checked50200",false);
        settingsEditor.putBoolean("status50300",false);
        settingsEditor.putBoolean("status50400",false);
        settingsEditor.putBoolean("status50500",false);
        settingsEditor.putBoolean("status50600",false);
        settingsEditor.putBoolean("status50700",false);
        settingsEditor.putBoolean("status50800",false);
        settingsEditor.putBoolean("status50900",false);
        settingsEditor.putBoolean("status51000",false);
        settingsEditor.putString("description50300", Integer.toString(_AlarmSET.SAHOOR_ALARM_TIME));
        settingsEditor.putString("description50900", "20");
        settingsEditor.putBoolean("checked50800",false);
        settingsEditor.putBoolean("checked50700",true);
        settingsEditor.putBoolean("checked50600",true);
        settingsEditor.putBoolean("checked50500",true);
        settingsEditor.putBoolean("checked60100",false);
        settingsEditor.putBoolean("status60200",false);
        settingsEditor.putBoolean("status60300",false);
        settingsEditor.putBoolean("status60400",false);
        settingsEditor.putBoolean("status60500",false);
        settingsEditor.putBoolean("checked60300",true);
        settingsEditor.putBoolean("checked60400",true);
        settingsEditor.putBoolean("checked60500",true);
        settingsEditor.putString("description60201", Integer.toString(_TimesSET.iqamaTimes[0]));
        settingsEditor.putString("description60204",Integer.toString(_TimesSET.iqamaTimes[2]));
        settingsEditor.putString("description60207",Integer.toString(_TimesSET.iqamaTimes[3]));
        settingsEditor.putString("description60210",Integer.toString(_TimesSET.iqamaTimes[4]));
        settingsEditor.putString("description60213",Integer.toString(_TimesSET.iqamaTimes[5]));
        settingsEditor.putString("description60216",Integer.toString(_TimesSET.iqamaTimes[2]));
        settingsEditor.putString("description60202", "10");
        settingsEditor.putString("description60205", "10");
        settingsEditor.putString("description60208", "10");
        settingsEditor.putString("description60211", "10");
        settingsEditor.putString("description60214", "10");
        settingsEditor.putString("description60217", "10");
        settingsEditor.putBoolean("checked60203",true);
        settingsEditor.putBoolean("checked60206",true);
        settingsEditor.putBoolean("checked60209",true);
        settingsEditor.putBoolean("checked60212",true);
        settingsEditor.putBoolean("checked60215",true);
        settingsEditor.putBoolean("checked60218",true);
        settingsEditor.putBoolean("status10300",false);
        settingsEditor.putString("description10702", "0");
        settingsEditor.putString("description10703", "0");
        settingsEditor.putString("description10704", "0");
        settingsEditor.putString("description10705", "0");
        settingsEditor.putString("description10706", "0");
        settingsEditor.putString("description10707", "0");
        settingsEditor.putBoolean("status20100",android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N);
        settingsEditor.putBoolean("checked20100",android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N);
        settingsEditor.commit();
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(settingsFile,MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        startSettings(context);
    }
}