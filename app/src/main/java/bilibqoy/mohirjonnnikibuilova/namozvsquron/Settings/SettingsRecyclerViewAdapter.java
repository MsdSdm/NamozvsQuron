package bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities.MainActivity;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities.SettingsActivity;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities.SettingsForthActivity;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities.SettingsThirdActivity;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms.AlarmsScheduler;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Alarms._AlarmSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Backup._BackupSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Times._TimesSET;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SettingsRecyclerViewAdapter extends RecyclerView.Adapter<SettingsRecyclerViewAdapter.SettingsRecyclerViewHolder> {

    List<String> titles,headers,details;
    Activity activity;
    int layoutNumber;

    public SettingsRecyclerViewAdapter(Activity ac,int f){
        titles = new ArrayList<>(Arrays.asList(ac.getResources().getStringArray(titlesArray[f])));
        headers = new ArrayList<>(Arrays.asList(ac.getResources().getStringArray(headersArray[f])));
        details = new ArrayList<>(Arrays.asList(ac.getResources().getStringArray(detailsArray[f])));
        activity = ac;
        layoutNumber =f;
    }
    @NonNull
    @Override
    public SettingsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(lp);
        return new SettingsRecyclerViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsRecyclerViewHolder holder, int position) {
        if(types[layoutNumber][position] == 0) holder.view.addView(titleBox(position));
        else{
            holder.view.addView(widgetBox(position));
            View view = new View(activity);
            view.setBackgroundColor(Color.LTGRAY);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
            holder.view.addView(view);
        }
    }

    @Override
    public void onViewRecycled(@NonNull SettingsRecyclerViewHolder holder) {
        super.onViewRecycled(holder);
        holder.view.removeAllViews();
    }
    private int getTitlesNumBeforeIndex(int position) {
        int k=0;
        for(int i=0;i<position;i++){
            if(types[layoutNumber][i]==0) k++;
        }
        return k;
    }


    @Override
    public int getItemCount() {
        return types[layoutNumber].length;
    }

    public class SettingsRecyclerViewHolder extends RecyclerView.ViewHolder{
        ViewGroup view;
        public SettingsRecyclerViewHolder(@NonNull ViewGroup itemView) {
            super(itemView);
            view =  itemView;
        }
    }

    @SuppressLint("ResourceType")
    private ConstraintLayout widgetBox(int i){
        ConstraintLayout son = new ConstraintLayout(activity);
        son.setId(ids[layoutNumber][i]);
        if(layoutNumber == PRAYER_NOTIFICATIONS_TONE_EACH_LAYOUT_NUM) son.setId(activity.getIntent().getExtras().getInt("id") + ids[layoutNumber][i]);
        boolean hasCheckBox = types[layoutNumber][i] == 2;
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        son.setLayoutParams(lp);
        son.setPadding(px(10),px(20),px(10),px(20));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            son.setForeground(activity.getResources().getDrawable(R.drawable.selectable_item_background));
        }
        ConstraintSet set = new ConstraintSet();

        TextView h = new TextView(activity);
        h.setLayoutParams(new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));
        h.setText(headers.get(i - getTitlesNumBeforeIndex(i)));
        h.setTextColor(Color.BLACK);
        h.setTextSize(18);
        h.setGravity(Gravity.START);
        h.setId((son.getId()*10)+1);
        h.setTypeface(_DisplaySET.getTypeFace(activity));
        TextView d = new TextView(activity);
        d.setLayoutParams(new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));
        d.setText(details.get(i - getTitlesNumBeforeIndex(i)));
        d.setTextColor(Color.GRAY);
        d.setTextSize(15);
        d.setGravity(Gravity.START);
        d.setId((son.getId()*10)+2);
        d.setTypeface(_DisplaySET.getTypeFace(activity));
        son.addView(h);
        son.addView(d);
        set.clone(son);
        if(hasCheckBox){
            CheckBox box = new CheckBox(activity);
            box.setClickable(false);
            box.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            box.setId((son.getId()*10) +3);
            son.addView(box);
            set.clone(son);
            set.connect(box.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
            set.connect(box.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            set.connect(box.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            set.applyTo(son);
        }
        set.connect(h.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START,0);
        set.connect(h.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP,0);
        set.connect(h.getId(), ConstraintSet.BOTTOM,d.getId(), ConstraintSet.TOP,0);
        set.connect(h.getId(), ConstraintSet.END,hasCheckBox?son.getChildAt(2).getId(): ConstraintSet.PARENT_ID,hasCheckBox? ConstraintSet.START: ConstraintSet.END,0);
        set.connect(d.getId(), ConstraintSet.TOP,h.getId(), ConstraintSet.BOTTOM,0);
        set.connect(d.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START,0);
        set.connect(d.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM,0);
        set.connect(d.getId(), ConstraintSet.END,hasCheckBox?son.getChildAt(2).getId(): ConstraintSet.PARENT_ID,hasCheckBox? ConstraintSet.START: ConstraintSet.END,0);
        set.applyTo(son);
        clicker(son,i- getTitlesNumBeforeIndex(i));
        if(hasCheckBox) _SET.setCheckBox(son);
        _SET.setDescription(son);
        _SET.setStatus(son);
        return son;
    }
    private TextView titleBox(int i){
        TextView t = new TextView(activity);
        t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        t.setPadding(px(10),px(10),px(10),0);
        t.setTextColor(activity.getResources().getColor(R.color.buttons));
        t.setGravity(Gravity.START);
        t.setText(titles.get(getTitlesNumBeforeIndex(i)));
        return t;
    }
    private int px(int dp) {
        return (int) (dp * activity.getResources().getDisplayMetrics().density + 0.5f);
    }

    public final static int BACKUP_AND_RESTORE_LAYOUT_NUM = 0;
    public final static int DISPLAY_OPTIONS_LAYOUT_NUM = 1;
    public final static int FAJR_AND_SAHOOR_LAYOUT_NUM = 2;
    public final static int HIJRI_LAYOUT_NUM = 3;
    public final static int IQAMA_REMINDER_TIMES_LAYOUT_NUM = 4;
    public final static int IQAMA_REMINDER_TONE_LAYOUT_NUM = 5;
    public final static int MANUAL_TIMES_ADJUSTMENTS_LAYOUT_NAM = 6;
    public final static int NOTIFICATIONS_LAYOUT_NUM = 7;
    public final static int PRAYER_NOTIFICATIONS_TIMES_LAYOUT_NUM = 8;
    public final static int PRAYER_NOTIFICATIONS_TONE_EACH_LAYOUT_NUM = 9;
    public final static int PRAYER_NOTIFICATIONS_TONE_LAYOUT_NUM = 10;
    public final static int PRAYER_TIMES_LAYOUT_NUM = 11;
    public final static int SILENT_LAYOUT_NUM = 12;
    public final static int SILENT_TIME_SETTINGS_LAYOUT_NUM = 13;
    private final int[][] ids = {
            {70100,-1,70200,70300,70400,-1,70500,70600}, //Backup & Restore
            {-1,30100,30200,30300,30400,30500,-1,30600,30700}, // Display Options
            {50100,-1,50200,50300,50400,50500,50600,50700,50800,50900,51000}, // Fajr & Sahoor Alarm
            {-1,20100,20200}, // Hijri
            {-1,40701,40702,-1,40703,40704,-1,40705,40706,-1,40707,40708,-1,40709,40710,-1,40711,40712}, // Iqama Reminder _TimesSET
            {41001,41002,41003}, // Iqama Reminder Tone
            {10701,-1,10702,10703,10704,10705,10706,10707,10708}, // Manual _TimesSET Adjustments
            {-1,40100,40200,40300,40400,40500,40600,-1,40700,40800,40900,41000,41100,41200,-1,41300,41400}, // Notifications
            {-1,40101,40102,-1,40103,40104,-1,40105,40106,-1,40107,40108,-1,40109,40110,-1,40111,40112}, // Prayer Notifications _TimesSET
            {10,20,30/*dynamic id*/}, // Prayer Notifications Tone Each
            {40401,40402,40403,40404,-1,40405,40406,40407,40408,40409}, // Prayer Notifications Tone
            {-1,10100,10200,10300,10400,10500,10600,10700}, // Prayer _TimesSET
            {-1,60100,60200,60300,60400,60500}, // Silent
            {-1,60201,60202,60203,-1,60204,60205,60206,-1,60207,60208,60209,-1,60210,60211,60212,-1,60213,60214,60215,-1,60216,60217,60218}  // Silent _TimesSET Settings
    };
    private final int types[][] = {
            {1,0,1,1,1,0,1,1}, //Backup & Restore
            {0,1,1,1,1,2,0,1,1}, // Display Options
            {1,0,2,1,1,2,2,2,2,1,1}, // Fajr & Sahoor Alarm
            {0,2,1}, // Hijri
            {0,1,2,0,1,2,0,1,2,0,1,2,0,1,2,0,1,2}, // Iqama Reminder _TimesSET
            {1,2,1}, // Iqama Reminder Tone
            {1,0,1,1,1,1,1,1,1}, // Manual _TimesSET Adjustments
            {0,1,2,1,1,2,1,0,1,2,1,1,2,1,0,2,2}, // Notifications
            {0,1,2,0,1,2,0,1,2,0,1,2,0,1,2,0,1,2}, // Prayer Notifications _TimesSET
            {1,2,1}, // Prayer Notifications Tone Each
            {1,2,1,2,0,1,1,1,1,1}, // Prayer Notifications Tone
            {0,1,2,2,1,1,1,1}, // Prayer _TimesSET
            {0,2,1,2,2,2}, // Silent
            {0,1,1,2,0,1,1,2,0,1,1,2,0,1,1,2,0,1,1,2,0,1,1,2}  // Silent _TimesSET Settings
    };
    private final int headersArray[] = {
            R.array.settings_layout_header_backup_and_restore, //Backup & Restore
            R.array.settings_layout_header_display_options, // Display Options
            R.array.settings_layout_header_fajr_and_sahoor_alarms, // Fajr & Sahoor Alarm
            R.array.settings_layout_header_hijri, // Hijri
            R.array.settings_layout_header_iqama_reminder_time, // Iqama Reminder _TimesSET
            R.array.settings_layout_header_iqama_reminder_tone, // Iqama Reminder Tone
            R.array.settings_layout_header_manual_times_adjustments, // Manual _TimesSET Adjustments
            R.array.settings_layout_header_notifications, // Notifications
            R.array.settings_layout_header_prayer_notification_time, // Prayer Notifications _TimesSET
            R.array.settings_layout_header_iqama_reminder_tone, // Prayer Notifications Tone Each
            R.array.settings_layout_header_prayer_notification_tone, // Prayer Notifications Tone
            R.array.settings_layout_header_prayer_times, // Prayer _TimesSET
            R.array.settings_layout_header_silent, // Silent
            R.array.settings_layout_header_silent_time_settings  // Silent _TimesSET Settings
    };
    private final int detailsArray[] = {
            R.array.settings_layout_details_backup_and_restore, //Backup & Restore
            R.array.settings_layout_details_display_options, // Display Options
            R.array.settings_layout_details_fajr_and_sahoor_alarms, // Fajr & Sahoor Alarm
            R.array.settings_layout_details_hijri, // Hijri
            R.array.settings_layout_details_iqama_reminder_time, // Iqama Reminder _TimesSET
            R.array.settings_layout_details_iqama_reminder_tone, // Iqama Reminder Tone
            R.array.settings_layout_details_manual_times_adjustments, // Manual _TimesSET Adjustments
            R.array.settings_layout_details_notifications, // Notifications
            R.array.settings_layout_details_prayer_notification_time, // Prayer Notifications _TimesSET
            R.array.settings_layout_details_iqama_reminder_tone, // Prayer Notifications Tone Each
            R.array.settings_layout_details_prayer_notification_tone, // Prayer Notifications Tone
            R.array.settings_layout_details_prayer_times, // Prayer _TimesSET
            R.array.settings_layout_details_silent, // Silent
            R.array.settings_layout_details_silent_time_settings  // Silent _TimesSET Settings
    };
    private final int titlesArray[] = {
            R.array.settings_layout_titles_backup_and_restore, //Backup & Restore
            R.array.settings_layout_titles_display_options, // Display Options
            R.array.settings_layout_titles_fajr_and_sahoor_alarms, // Fajr & Sahoor Alarm
            R.array.settings_layout_titles_hijri, // Hijri
            R.array.settings_layout_titles_prayer_notification_time, // Iqama Reminder _TimesSET
            R.array.settings_layout_titles_iqama_reminder_tone, // Iqama Reminder Tone
            R.array.settings_layout_titles_manual_times_adjustments, // Manual _TimesSET Adjustments
            R.array.settings_layout_titles_notifications, // Notifications
            R.array.settings_layout_titles_prayer_notification_time, // Prayer Notifications _TimesSET
            R.array.settings_layout_titles_iqama_reminder_tone, // Prayer Notifications Tone Each
            R.array.settings_layout_titles_prayer_notification_tone, // Prayer Notifications Tone
            R.array.settings_layout_titles_prayer_times, // Prayer _TimesSET
            R.array.settings_layout_titles_silent, // Silent
            R.array.settings_layout_titles_prayer_notification_time  // Silent _TimesSET Settings
    };

    @SuppressLint("ResourceType")
    private void clicker(final ViewGroup group, final int k) {
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(k==1 && layoutNumber == BACKUP_AND_RESTORE_LAYOUT_NUM){
                    final EditText text = new EditText(activity);
                    text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    text.setTextSize(17);
                    text.setText((new SimpleDateFormat("dd.MM.yy-hh.mm.ss")).format(new Date()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        text.setFocusedByDefault(true);
                    }
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setTitle(activity.getResources().getString(R.string.set_file_name));
                    dialog.setView(text);
                    dialog.setPositiveButton(activity.getResources().getString(R.string.mdtp_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            _BackupSET.backup(activity,text.getText().toString(),_BackupSET.TYPE_SETTINGS);
                            dialogInterface.dismiss();
                            Toast.makeText(activity,activity.getResources().getString(R.string.toast_backup_saved),Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton(activity.getResources().getString(R.string.mdtp_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.show();
                }
                else if(k==2 && layoutNumber == BACKUP_AND_RESTORE_LAYOUT_NUM){
                    final AlertDialog.Builder b1 = new AlertDialog.Builder(activity);
                    b1.setTitle(activity.getResources().getString(R.string.backup_list));
                    final String[] list = _BackupSET.listSaved(_BackupSET.TYPE_SETTINGS);
                    if(list == null || list.length == 0){
                        b1.setView(_BackupSET.noBackupsView(activity));
                        b1.create().show();
                    }
                    else {
                       ViewGroup viewGroup = _BackupSET.getBackups(activity,_BackupSET.TYPE_SETTINGS);
                       b1.setView(viewGroup);
                       final AlertDialog dialog1 = b1.create();
                       dialog1.show();
                       for(int i=0;i<viewGroup.getChildCount();i++){
                           final int c = i;
                           viewGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   dialog1.dismiss();
                                   final AlertDialog.Builder b2 = new AlertDialog.Builder(activity);
                                   ViewGroup viewGroup = _BackupSET.getBackupOptions(activity);
                                   b2.setView(viewGroup);
                                   final AlertDialog d2 = b2.create();
                                   d2.show();
                                   viewGroup.getChildAt(0).setOnClickListener(new View.OnClickListener() { //Restore
                                       @Override
                                       public void onClick(View view) {
                                           _BackupSET.restore(activity, list[c], _BackupSET.TYPE_SETTINGS);
                                           _TimesSET.updateTimes(activity);
                                           AlarmsScheduler.fire(activity, Calendar.getInstance());
                                           MainActivity.reloadMainActivityOnResume = true;
                                           SettingsActivity.reloadSettingsActivityOnResume = true;
                                           d2.dismiss();
                                           Toast.makeText(activity,activity.getResources().getString(R.string.toast_backup_restored),Toast.LENGTH_SHORT).show();
                                       }
                                   });
                                   viewGroup.getChildAt(1).setOnClickListener(new View.OnClickListener() { //Delete
                                       @Override
                                       public void onClick(View view) {
                                           _BackupSET.delete(list[c],_BackupSET.TYPE_SETTINGS);
                                           d2.dismiss();
                                           group.performClick();
                                       }
                                   });
                               }
                           });
                       }
                    }
                }
                else if(k==3 && layoutNumber == BACKUP_AND_RESTORE_LAYOUT_NUM){
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(((TextView)group.getChildAt(0)).getText());
                    builder.setMessage(activity.getResources().getString(R.string.backup_and_restore_dialog3));
                    builder.setPositiveButton(activity.getResources().getString(R.string.mdtp_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            _BackupSET.clear(activity,_BackupSET.TYPE_SETTINGS);
                            _TimesSET.updateTimes(activity);
                            AlarmsScheduler.fire(activity,Calendar.getInstance());
                            MainActivity.reloadMainActivityOnResume = true;
                            SettingsActivity.reloadSettingsActivityOnResume = true;
                            dialogInterface.dismiss();
                            Toast.makeText(activity,activity.getResources().getString(R.string.toast_settings_reset_completed),Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton(activity.getResources().getString(R.string.mdtp_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                else if(k==4 && layoutNumber==BACKUP_AND_RESTORE_LAYOUT_NUM){
                    final EditText text = new EditText(activity);
                    text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    text.setTextSize(17);
                    text.setText((new SimpleDateFormat("dd.MM.yy-hh.mm.ss")).format(new Date()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        text.setFocusedByDefault(true);
                    }
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setTitle(activity.getResources().getString(R.string.set_file_name));
                    dialog.setView(text);
                    dialog.setPositiveButton(activity.getResources().getString(R.string.mdtp_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            _BackupSET.backup(activity,text.getText().toString(),_BackupSET.TYPE_LOCATIONS);
                            dialogInterface.dismiss();
                            Toast.makeText(activity,activity.getResources().getString(R.string.toast_backup_saved),Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton(activity.getResources().getString(R.string.mdtp_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.show();
                }
                else if(k==5 && layoutNumber ==BACKUP_AND_RESTORE_LAYOUT_NUM){
                    final AlertDialog.Builder b1 = new AlertDialog.Builder(activity);
                    b1.setTitle(activity.getResources().getString(R.string.backup_list));
                    final String[] list = _BackupSET.listSaved(_BackupSET.TYPE_LOCATIONS);
                    if(list == null || list.length == 0){
                        b1.setView(_BackupSET.noBackupsView(activity));
                        b1.create().show();
                    }
                    else {
                        ViewGroup viewGroup = _BackupSET.getBackups(activity,_BackupSET.TYPE_LOCATIONS);
                        b1.setView(viewGroup);
                        final AlertDialog dialog1 = b1.create();
                        dialog1.show();
                        for(int i=0;i<viewGroup.getChildCount();i++){
                            final int c = i;
                            viewGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog1.dismiss();
                                    final AlertDialog.Builder b2 = new AlertDialog.Builder(activity);
                                    ViewGroup viewGroup = _BackupSET.getBackupOptions(activity);
                                    b2.setView(viewGroup);
                                    final AlertDialog d2 = b2.create();
                                    d2.show();
                                    viewGroup.getChildAt(0).setOnClickListener(new View.OnClickListener() { //Restore
                                        @Override
                                        public void onClick(View view) {
                                            _BackupSET.restore(activity, list[c], _BackupSET.TYPE_LOCATIONS);
                                            _TimesSET.updateTimes(activity);
                                            AlarmsScheduler.fire(activity, Calendar.getInstance());
                                            MainActivity.reloadMainActivityOnResume = true;
                                            SettingsActivity.reloadSettingsActivityOnResume = true;
                                            d2.dismiss();
                                            Toast.makeText(activity,activity.getResources().getString(R.string.toast_backup_restored),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    viewGroup.getChildAt(1).setOnClickListener(new View.OnClickListener() { //Delete
                                        @Override
                                        public void onClick(View view) {
                                            _BackupSET.delete(list[c],_BackupSET.TYPE_LOCATIONS);
                                            d2.dismiss();
                                            group.performClick();
                                        }
                                    });
                                }
                            });
                        }
                    }
                }
                else if(k==0 && layoutNumber==DISPLAY_OPTIONS_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity, _DisplaySET.displayFile,"language",((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(new String[]{"O\'zbekcha","عربي", "English", "Русский", "Türk","նպատակա", "azerbaycan", "Shqiptare", "afrikaans", "हिंदी", "italiano", "Indonesia", "français", "точикча"}, new String[]{"uz", "ar", "en", "ru", "tr", "hy", "az", "sq", "af", "hi", "it", "in", "fr", "tg"}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {
                            _DisplaySET.setLanguagePreferences(activity.getApplicationContext());
                            SettingsActivity.reloadSettingsActivityOnResume = true;
                            MainActivity.reloadMainActivityOnResume = true;
                            activity.finish();

                        }
                    });
                    dialog.show();
                }
                else if(k==1&& layoutNumber==DISPLAY_OPTIONS_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity, _DisplaySET.displayFile,"numbers_language",((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(new String[]{"English","عربي",activity.getResources().getString(R.string.app_language)}, new int[]{0,1,2}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {
                            SettingsActivity.reloadSettingsActivityOnResume = true;
                            MainActivity.reloadMainActivityOnResume = true;
                        }
                    });
                    dialog.show();
                }
                else if(k==2 && layoutNumber==DISPLAY_OPTIONS_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity, _DisplaySET.displayFile,"theme",((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(activity.getResources().getStringArray(R.array.app_theme), new int[]{0,1}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {
                            MainActivity.reloadMainActivityOnResume = true;
                        }
                    });
                    dialog.show();
                }
                else if(k==3 && layoutNumber==DISPLAY_OPTIONS_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity, _DisplaySET.displayFile,"font",((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(activity.getResources().getStringArray(R.array.app_font), new int[]{0,1,2}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {
                            SettingsActivity.reloadSettingsActivityOnResume = true;
                            MainActivity.reloadMainActivityOnResume = true;
                            activity.recreate();
                        }
                    });
                    dialog.show();
                }
                else if(k==4 && layoutNumber==DISPLAY_OPTIONS_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_DisplaySET.displayFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("time24",_SET.isChecked(group));
                    editor.commit();
                    MainActivity.reloadMainActivityOnResume = true;
                }
                else if(layoutNumber == DISPLAY_OPTIONS_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity, _DisplaySET.displayFile,"widget_theme_"+(k-5),((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(activity.getResources().getStringArray(R.array.widget_theme), new int[]{0,1,2}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {
                            MainActivity.reloadMainActivityOnResume = true;
                        }
                    });
                    dialog.show();
                }

                else if(k==1 && layoutNumber==FAJR_AND_SAHOOR_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.sahoorFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("sahooralarm",_SET.isChecked(group));
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,50300,_SET.isChecked(group));
                    _SET.setStatus(activity,50400,_SET.isChecked(group));
                    _SET.setStatus(activity,50500,_SET.isChecked(group));
                    _SET.setStatus(activity,50600,_SET.isChecked(group));
                    _SET.setStatus(activity,50700,_SET.isChecked(group));
                    _SET.setStatus(activity,50800,_SET.isChecked(group));
                    _SET.setStatus(activity,50900,_SET.isChecked(group) && _SET.isChecked(50800));
                    _SET.setStatus(activity,51000,_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if(k==2 && layoutNumber==FAJR_AND_SAHOOR_LAYOUT_NUM){
                    SeekBarDialog dialog = new SeekBarDialog(activity,_AlarmSET.sahoorFile,"time",activity.getResources().getString(R.string.alarm_time_from_fajr_time));
                    dialog.initialize(-90, 20, checked -> {
                        AlarmsScheduler.fire(activity,Calendar.getInstance());
                        _SET.setDescription(group,Integer.toString(checked));
                    });
                    dialog.show();
                }
                else if(k==3 && layoutNumber==FAJR_AND_SAHOOR_LAYOUT_NUM){
                    Uri ringtone = Uri.parse(activity.getSharedPreferences(_AlarmSET.sahoorFile,Context.MODE_PRIVATE).getString("uri", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString()));
                    Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, ringtone);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, ringtone);
                    activity.startActivityForResult(intent , group.getId());
                }
                else if(k==4 && layoutNumber==FAJR_AND_SAHOOR_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.sahoorFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("vibrate",_SET.isChecked(group));
                    editor.commit();
                }
                else if(k==5 && layoutNumber==FAJR_AND_SAHOOR_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.sahoorFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("screen_on",_SET.isChecked(group));
                    editor.commit();
                }
                else if(k==6 && layoutNumber==FAJR_AND_SAHOOR_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.sahoorFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("work_on_silent",_SET.isChecked(group));
                    editor.commit();
                }
                else if(k==7 && layoutNumber==FAJR_AND_SAHOOR_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.sahoorFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("stop_automatically",_SET.isChecked(group));
                    editor.commit();
                    _SET.setStatus(activity,50900,_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if(k==8 && layoutNumber==FAJR_AND_SAHOOR_LAYOUT_NUM){
                    SeekBarDialog dialog = new SeekBarDialog(activity,_AlarmSET.sahoorFile,"stop_time",activity.getResources().getString(R.string.sahoor_activity_time_to_stop_dialog_title));
                    dialog.initialize(5, 45, new SeekBarDialog.run() {
                        @Override
                        public void go(int checked) {
                            _SET.setDescription(group,Integer.toString(checked));
                        }
                    });
                    dialog.show();
                }
                else if(k==9 && layoutNumber==FAJR_AND_SAHOOR_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity,_AlarmSET.sahoorFile,"snooze_time",((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(new String[]{"2", "5", "10", "15", "20"}, new int[]{2, 5, 10, 15, 20}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {

                        }
                    });
                    dialog.show();
                }


                //Hijri
                else if(k==0 && layoutNumber ==HIJRI_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_TimesSET.adjustTimesFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("is_google_cal",_SET.isChecked(group));
                    editor.commit();
                    MainActivity.reloadMainActivityOnResume = true;
                }else if(layoutNumber ==HIJRI_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity, _TimesSET.adjustTimesFile,"hijri_adjust",((TextView)group.getChildAt(0)).getText().toString());
                    dialog.initialize(new String[]{"+2", "+1", "0", "-1", "-2"}, new int[]{2, 1, 0, -1, -2}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {
                            MainActivity.reloadMainActivityOnResume = true;
                        }
                    });
                    dialog.show();
                }

                else if((k%2)==0 && layoutNumber == IQAMA_REMINDER_TIMES_LAYOUT_NUM){
                    SeekBarDialog dialog = new SeekBarDialog(activity, _AlarmSET.iqamaFile,"remind_time_"+(k/2),activity.getResources().getString(R.string.after_prayer_time));
                    dialog.initialize(0, 30, new SeekBarDialog.run() {
                        @Override
                        public void go(int checked) {
                            AlarmsScheduler.fire(activity, Calendar.getInstance());
                            _SET.setDescription(group,Integer.toString(checked));
                        }
                    });
                    dialog.show();
                }else if(layoutNumber == IQAMA_REMINDER_TIMES_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.iqamaFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("remind_"+((k-1)/2),_SET.isChecked(group));
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,40700+k,_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }

                else if(k>0 && k<7 && layoutNumber == MANUAL_TIMES_ADJUSTMENTS_LAYOUT_NAM){
                    SeekBarDialog dialog = new SeekBarDialog(activity, _TimesSET.adjustTimesFile,"adjust_"+(k-1),((TextView)group.getChildAt(0)).getText().toString());
                    dialog.initialize(-60, 60, new SeekBarDialog.run() {
                        @Override
                        public void go(int checked) {
                            _TimesSET.updateTimes(activity);
                            AlarmsScheduler.fire(activity, Calendar.getInstance());
                            _SET.setDescription(group,Integer.toString(checked));
                        }
                    });
                    dialog.show();
                }
                else if(k==7 && layoutNumber == MANUAL_TIMES_ADJUSTMENTS_LAYOUT_NAM){
                    SharedPreferences p = activity.getSharedPreferences(_TimesSET.adjustTimesFile,Context.MODE_PRIVATE);
                    SharedPreferences.Editor e = p.edit();
                    for(int i=2;i<8;i++){
                        e.putInt("adjust_" +(i-2),0);
                        _SET.setDescription(activity,(10700 + i),"0");
                    }
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                    e.commit();
                    Toast.makeText(activity,activity.getResources().getString(R.string.adjustments_is_reset),Toast.LENGTH_SHORT).show();
                }
                else if(k==0 && layoutNumber == IQAMA_REMINDER_TONE_LAYOUT_NUM){
                    Uri ringtone = _AlarmSET.getRingtoneUri(activity,_AlarmSET.IQAMA_REQUEST_CODE);
                    Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
                            RingtoneManager.TYPE_ALARM
                                    | RingtoneManager.TYPE_RINGTONE);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, ringtone);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, ringtone);
                    activity.startActivityForResult(intent , group.getId());
                }
                else if(k==1 && layoutNumber == IQAMA_REMINDER_TONE_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.iqamaFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("use_sd",_SET.isChecked(group));
                    editor.remove("uri");
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,41001,!_SET.isChecked(group));
                    _SET.setStatus(activity,41003,_SET.isChecked(group));
                    _SET.setDescription(activity,41003,"");
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if(k==2 && layoutNumber == IQAMA_REMINDER_TONE_LAYOUT_NUM){
                    Toast.makeText(activity,activity.getResources().getString(R.string.toast_only_mp3_files),Toast.LENGTH_SHORT).show();
                    System.gc();
                    Intent intent = new Intent();
                    intent.setType("audio/*");
                    if (Build.VERSION.SDK_INT < 19) {
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent = Intent.createChooser(intent, "Select file");
                    } else {
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        String[] mimetypes = { "audio/*" };
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                    }
                    activity.startActivityForResult(intent , group.getId());
                }

                else if(k==0 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    Intent intent = new Intent(activity, SettingsThirdActivity.class);
                    intent.putExtra("title",((TextView) group.getChildAt(0)).getText().toString());
                    intent.putExtra("index",PRAYER_NOTIFICATIONS_TIMES_LAYOUT_NUM);
                    activity.startActivity(intent);
                }
                else if(k==1 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.azanFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("notify",_SET.isChecked(group));
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,40100,_SET.isChecked(group));
                    _SET.setStatus(activity,40300,_SET.isChecked(group));
                    _SET.setStatus(activity,40400,_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if(k==2 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity,_AlarmSET.azanFile,"notification_type",((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(activity.getResources().getStringArray(R.array.notification_audio_type), new int[]{0,1,2,3}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {
                            AlarmsScheduler.fire(activity, Calendar.getInstance());
                        }
                    });
                    dialog.show();
                }
                else if(k==3 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    Intent intent = new Intent(activity, SettingsThirdActivity.class);
                    intent.putExtra("title",((TextView) group.getChildAt(0)).getText().toString());
                    intent.putExtra("index",PRAYER_NOTIFICATIONS_TONE_LAYOUT_NUM);
                    activity.startActivity(intent);
                }
                else if(k==4 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.azanFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("clear_notification",_SET.isChecked(group));
                    editor.commit();
                    _SET.setStatus(activity,40600,_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if(k==5 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    SeekBarDialog dialog = new SeekBarDialog(activity,_AlarmSET.azanFile,"clear_time",((TextView)group.getChildAt(0)).getText().toString());
                    dialog.initialize(1, 60, new SeekBarDialog.run() {
                        @Override
                        public void go(int checked) {
                            AlarmsScheduler.fire(activity,Calendar.getInstance());
                            _SET.setDescription(activity,40600, String.valueOf(checked));
                        }
                    });
                    dialog.show();
                }
                else if(k==6 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    Intent intent = new Intent(activity, SettingsThirdActivity.class);
                    intent.putExtra("title",((TextView) group.getChildAt(0)).getText().toString());
                    intent.putExtra("index",IQAMA_REMINDER_TIMES_LAYOUT_NUM);
                    activity.startActivity(intent);
                }
                else if(k == 7 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.iqamaFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("remind",_SET.isChecked(group));
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,40900,_SET.isChecked(group));
                    _SET.setStatus(activity,41000,_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if(k==8 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity,_AlarmSET.iqamaFile,"reminder_type",((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(activity.getResources().getStringArray(R.array.notification_audio_type), new int[]{0,1,2,3}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {
                            AlarmsScheduler.fire(activity, Calendar.getInstance());
                        }
                    });
                    dialog.show();
                }
                else if(k==9 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    Intent intent = new Intent(activity, SettingsThirdActivity.class);
                    intent.putExtra("title",((TextView) group.getChildAt(0)).getText().toString());
                    intent.putExtra("index",IQAMA_REMINDER_TONE_LAYOUT_NUM);
                    activity.startActivity(intent);
                }
                else if(k==10 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.iqamaFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("clear_reminder",_SET.isChecked(group));
                    editor.commit();
                    _SET.setStatus(activity,41200,_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if(k==11 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    SeekBarDialog dialog = new SeekBarDialog(activity,_AlarmSET.iqamaFile,"clear_time",((TextView)group.getChildAt(0)).getText().toString());
                    dialog.initialize(1, 60, new SeekBarDialog.run() {
                        @Override
                        public void go(int checked) {
                            AlarmsScheduler.fire(activity,Calendar.getInstance());
                            _SET.setDescription(activity,41200, String.valueOf(checked));
                        }
                    });
                    dialog.show();
                }
                else if(k==12 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.azanFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("vibrate",_SET.isChecked(group));
                    editor.commit();
                    pref = activity.getSharedPreferences(_AlarmSET.iqamaFile, Context.MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putBoolean("vibrate",_SET.isChecked(group));
                    editor.commit();
                }
                else if(k==13 && layoutNumber==NOTIFICATIONS_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.azanFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("led",_SET.isChecked(group));
                    editor.commit();
                    pref = activity.getSharedPreferences(_AlarmSET.iqamaFile, Context.MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putBoolean("led",_SET.isChecked(group));
                    editor.commit();
                }


                else if((k%2)==0 && layoutNumber == PRAYER_NOTIFICATIONS_TIMES_LAYOUT_NUM){
                    SeekBarDialog dialog = new SeekBarDialog(activity, _AlarmSET.azanFile,"notification_time_"+(k/2),activity.getResources().getString(R.string.before_prayer_time));
                    dialog.initialize(0, 60, new SeekBarDialog.run() {
                        @Override
                        public void go(int checked) {
                            AlarmsScheduler.fire(activity, Calendar.getInstance());
                            _SET.setDescription(group,Integer.toString(checked));
                        }
                    });
                    dialog.show();
                }
                else if(layoutNumber == PRAYER_NOTIFICATIONS_TIMES_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.azanFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("notify_"+((k-1)/2),_SET.isChecked(group));
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,40100+k,_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }

                //**************** PRAYER_NOTIFICATIONS_TONE_EACH_LAYOUT ****************//
                else if(k==0 && layoutNumber == PRAYER_NOTIFICATIONS_TONE_EACH_LAYOUT_NUM){
                    Uri ringtone = _AlarmSET.getRingtoneUri(activity,_AlarmSET.AZAN_REQUEST_CODE,((group.getId()%10)-5));
                    Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
                            RingtoneManager.TYPE_ALARM
                                    | RingtoneManager.TYPE_RINGTONE);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, ringtone);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, ringtone);
                    activity.startActivityForResult(intent , group.getId());
                }
                else if(k==1 && layoutNumber == PRAYER_NOTIFICATIONS_TONE_EACH_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.azanFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("use_sd_for" + ((group.getId()%10)-5),_SET.isChecked(group));
                    editor.remove("uri"+((group.getId()%10)-5)); //uri0->Fajr uri1->Zuhr ...
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,group.getId()-10,!_SET.isChecked(group));
                    _SET.setStatus(activity,group.getId()+10,_SET.isChecked(group));
                    _SET.setDescription((ViewGroup) activity.findViewById(group.getId()+10),"");
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if(k==2 && layoutNumber == PRAYER_NOTIFICATIONS_TONE_EACH_LAYOUT_NUM){
                    System.gc();
                    Intent intent = new Intent();
                    intent.setType("audio/*");
                    if (Build.VERSION.SDK_INT < 19) {
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent = Intent.createChooser(intent, "Select file");
                    } else {
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        String[] mimetypes = { "audio/*" };
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                    }
                    activity.startActivityForResult(intent , group.getId());
                }

                else if(k==0 && layoutNumber == PRAYER_NOTIFICATIONS_TONE_LAYOUT_NUM){
                    Uri ringtone = _AlarmSET.getRingtoneUri(activity,_AlarmSET.AZAN_REQUEST_CODE);
                    Intent intent=new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
                            RingtoneManager.TYPE_ALARM
                                    | RingtoneManager.TYPE_RINGTONE);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, ringtone);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, ringtone);
                    activity.startActivityForResult(intent , group.getId());
                }
                else if(k==1 && layoutNumber == PRAYER_NOTIFICATIONS_TONE_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.azanFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("use_sd",_SET.isChecked(group));
                    editor.remove("uri");
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,40401,!_SET.isChecked(group));
                    _SET.setStatus(activity,40403,_SET.isChecked(group));
                    _SET.setDescription(activity,40403,"");
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if(k==2 && layoutNumber == PRAYER_NOTIFICATIONS_TONE_LAYOUT_NUM){
                    System.gc();
                    Intent intent = new Intent();
                    intent.setType("audio/*");
                    if (Build.VERSION.SDK_INT < 19) {
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent = Intent.createChooser(intent, "Select file");
                    } else {
                        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        String[] mimetypes = { "audio/*" };
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                    }
                    activity.startActivityForResult(intent , group.getId());
                }
                else if(k==3 && layoutNumber == PRAYER_NOTIFICATIONS_TONE_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.azanFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("use_different",_SET.isChecked(group));
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,40401,!_SET.isChecked(group));
                    _SET.setStatus(activity,40402,!_SET.isChecked(group));
                    _SET.setCheckBox(activity,40402,false);
                    _SET.setStatus(activity,40403,false);
                    _SET.setStatus(activity,40405,_SET.isChecked(group));
                    _SET.setStatus(activity,40406,_SET.isChecked(group));
                    _SET.setStatus(activity,40407,_SET.isChecked(group));
                    _SET.setStatus(activity,40408,_SET.isChecked(group));
                    _SET.setStatus(activity,40409,_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if( layoutNumber == PRAYER_NOTIFICATIONS_TONE_LAYOUT_NUM){
                    Intent intent = new Intent(activity, SettingsForthActivity.class);
                    intent.putExtra("id",group.getId());
                    intent.putExtra("title",((TextView) group.getChildAt(0)).getText().toString());
                    activity.startActivity(intent);
                }
                //**************** PRAYER_TIMES_LAYOUT ****************//
                else if(k==0 && layoutNumber==PRAYER_TIMES_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity, _TimesSET.prayersFile,"method",((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(activity.getResources().getStringArray(R.array.prayer_calculations_methods), new int[]{4, 3, 1, 5, 2, 6}, checked -> {
                        _TimesSET.updateTimes(activity);
                        AlarmsScheduler.fire(activity, Calendar.getInstance());
                        _SET.setStatus(activity,10200, checked != 0 && checked != 7);
                        _SET.setStatus(activity,10300, checked == 0);
                        SettingsRecyclerViewAdapter.this.notifyDataSetChanged();

                    });
                    dialog.show();
                }
                else if (k == 1 && layoutNumber==PRAYER_TIMES_LAYOUT_NUM) {
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_TimesSET.prayersFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("angle_based_method",_SET.isChecked(group));
                    editor.commit();
                    _TimesSET.updateTimes(activity);
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                }
                else if (k == 2 && layoutNumber==PRAYER_TIMES_LAYOUT_NUM) {
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_TimesSET.prayersFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("adjust_isha_in_ramadan",_SET.isChecked(group));
                    editor.commit();
                    _TimesSET.updateTimes(activity);
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                }
                else if(k==3 && layoutNumber==PRAYER_TIMES_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity, _TimesSET.prayersFile,"asr_method",((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(activity.getResources().getStringArray(R.array.asr_methods), new int[]{0, 1}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {
                            _TimesSET.updateTimes(activity);
                            AlarmsScheduler.fire(activity, Calendar.getInstance());
                        }
                    });
                    dialog.show();
                }
                else if(k==4 && layoutNumber==PRAYER_TIMES_LAYOUT_NUM){
                    RadioDialog dialog = new RadioDialog(activity, _TimesSET.prayersFile,"dst",((TextView) group.getChildAt(0)).getText().toString());
                    dialog.initialize(new String[]{"-1","0","+1"}, new int[]{-1,0,1}, new RadioDialog.run() {
                        @Override
                        public void go(int checked) {
                            _TimesSET.updateTimes(activity);
                            AlarmsScheduler.fire(activity, Calendar.getInstance());
                        }
                    });
                    dialog.show();
                }
                else if(k==5 && layoutNumber==PRAYER_TIMES_LAYOUT_NUM){
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    final int[] chosen = new int[]{_TimesSET.isJumuahTimeDiff(activity)?1:0};
                    dialog.setSingleChoiceItems(activity.getResources().getStringArray(R.array.jumuah_time_different_array), chosen[0], new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            chosen[0] = i;
                        }
                    });
                    dialog.setPositiveButton(activity.getResources().getString(R.string.mdtp_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(chosen[0] == 0) _TimesSET.setJumuahDifferent(activity,false);
                            else {
                                String s = _TimesSET.getJumuahTime(activity);
                                TimePickerDialog dialog1 = new TimePickerDialog(activity,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                        _TimesSET.setJumuahDifferent(activity,true);
                                        _TimesSET.setJumuahTime(activity,i,i1);
                                    }
                                },Integer.parseInt(s.substring(0,s.indexOf(':'))),Integer.parseInt(s.substring(s.indexOf(':')+1)),false);
                                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                dialog1.show();
                            }
                            _TimesSET.updateTimes(activity);
                            AlarmsScheduler.fire(activity,Calendar.getInstance());
                        }
                    });
                    dialog.show();
                }
                else if(k==6 && layoutNumber==PRAYER_TIMES_LAYOUT_NUM){
                    Intent intent = new Intent(activity, SettingsThirdActivity.class);
                    intent.putExtra("title",((TextView) group.getChildAt(0)).getText().toString());
                    intent.putExtra("index",MANUAL_TIMES_ADJUSTMENTS_LAYOUT_NAM);
                    activity.startActivity(intent);
                }

                else if(k==0 && layoutNumber == SILENT_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.silentFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("active",_SET.isChecked(group));
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,60200,_SET.isChecked(group));
                    _SET.setStatus(activity,60300,_SET.isChecked(group));
                    _SET.setStatus(activity,60400,_SET.isChecked(group));
                    _SET.setStatus(activity,60500,_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
                else if(k==1 && layoutNumber == SILENT_LAYOUT_NUM){
                    Intent intent = new Intent(activity, SettingsThirdActivity.class);
                    intent.putExtra("title",((TextView) group.getChildAt(0)).getText().toString());
                    intent.putExtra("index",SILENT_TIME_SETTINGS_LAYOUT_NUM);
                    activity.startActivity(intent);
                }
                else if(k==2 && layoutNumber == SILENT_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.silentFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("vibrate_on_set",_SET.isChecked(group));
                    editor.commit();
                }
                else if(k==3 && layoutNumber == SILENT_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.silentFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("vibrate",_SET.isChecked(group));
                    editor.commit();
                }
                else if(k==4 && layoutNumber == SILENT_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.silentFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("msg",_SET.isChecked(group));
                    editor.commit();
                }
                else if((k%3)==0 && layoutNumber == SILENT_TIME_SETTINGS_LAYOUT_NUM){
                    SeekBarDialog dialog = new SeekBarDialog(activity, _AlarmSET.silentFile,"silent_time_"+(k/3),activity.getResources().getString(R.string.after_prayer_time));
                    dialog.initialize(0, 30, new SeekBarDialog.run() {
                        @Override
                        public void go(int checked) {
                            AlarmsScheduler.fire(activity, Calendar.getInstance());
                            _SET.setDescription(group,Integer.toString(checked));
                        }
                    });
                    dialog.show();
                }
                else if((k%3)==1 && layoutNumber == SILENT_TIME_SETTINGS_LAYOUT_NUM){
                    SeekBarDialog dialog = new SeekBarDialog(activity, _AlarmSET.silentFile,"silent_period_"+((k-1)/3),activity.getResources().getString(R.string.silent_period_in_minutes));
                    dialog.initialize(5, 90, new SeekBarDialog.run() {
                        @Override
                        public void go(int checked) {
                            _SET.setDescription(group,Integer.toString(checked));
                        }
                    });
                    dialog.show();
                }
                else if(layoutNumber == SILENT_TIME_SETTINGS_LAYOUT_NUM){
                    _SET.setCheckBox(group,!_SET.isChecked(group));
                    SharedPreferences pref = activity.getSharedPreferences(_AlarmSET.silentFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("active_"+((k-2)/3),_SET.isChecked(group));
                    editor.commit();
                    AlarmsScheduler.fire(activity, Calendar.getInstance());
                    _SET.setStatus(activity,(60200+k-1),_SET.isChecked(group));
                    _SET.setStatus(activity,(60200+k),_SET.isChecked(group));
                    SettingsRecyclerViewAdapter.this.notifyDataSetChanged();
                }
            }
        });
    }
}
