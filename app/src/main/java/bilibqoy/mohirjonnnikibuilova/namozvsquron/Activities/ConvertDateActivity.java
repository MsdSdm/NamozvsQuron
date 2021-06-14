package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations._LocationSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Times._TimesSET;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import net.alhazmy13.hijridatepicker.date.gregorian.GregorianDatePickerDialog;
import net.alhazmy13.hijridatepicker.date.hijri.HijriDatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static bilibqoy.mohirjonnnikibuilova.namozvsquron.Times._TimesSET.adjustTimesFile;

public class ConvertDateActivity extends AppCompatActivity
        implements  HijriDatePickerDialog.OnDateSetListener,
        GregorianDatePickerDialog.OnDateSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(_DisplaySET.getAppTheme(this)==_DisplaySET.WHITE ?R.style.AppTheme_NoActionBar: R.style.AppThemeDark_NoActionBar);
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_convert_date);
        Toolbar toolbar = findViewById(R.id.toolbar_convert_date_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        manageRadioGroup();
        manageDialogs();
        checkForCalendarAdjustments();
    }

    private void checkForCalendarAdjustments() {
        final int l = getSharedPreferences(adjustTimesFile,MODE_PRIVATE).getInt("hijri_adjust",0);
        if(l!=0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.alert_hijri_calendar_adjusted));
            builder.setPositiveButton(getResources().getString(R.string.mdtp_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }
    }

    private void manageRadioGroup() {
        setTextsDefault();
        ((RadioGroup) findViewById(R.id.convert_date_activity_radio_group)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                setTextsDefault();
                if(i == R.id.convert_date_activity_from_gregorian_radio_button){
                    ((RadioButton) findViewById(R.id.convert_date_activity_from_hijri_radio_button)).setChecked(false);
                    switchRadio(findViewById(R.id.convert_date_activity_from_gregorian_button),
                            findViewById(R.id.convert_date_activity_from_hijri_button));
                }else{
                    ((RadioButton) findViewById(R.id.convert_date_activity_from_gregorian_radio_button)).setChecked(false);
                    switchRadio(findViewById(R.id.convert_date_activity_from_hijri_button),
                            findViewById(R.id.convert_date_activity_from_gregorian_button));
                }
            }
        });
    }
    private void switchRadio(View enabled,View disabled){
        enabled.setClickable(true);
        enabled.setAlpha(1.0f);
        disabled.setClickable(false);
        disabled.setAlpha(0.2f);
    }
    private void manageDialogs(){
        findViewById(R.id.convert_date_activity_from_hijri_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextsDefault();
                UmmalquraCalendar now = _TimesSET.getUmmalquraCalendar(ConvertDateActivity.this);
                HijriDatePickerDialog dpd = HijriDatePickerDialog.newInstance(
                        ConvertDateActivity.this,
                        now.get(UmmalquraCalendar.YEAR),
                        now.get(UmmalquraCalendar.MONTH),
                        now.get(UmmalquraCalendar.DAY_OF_MONTH));

                dpd.show(ConvertDateActivity.this.getFragmentManager(), "HijriDatePickerDialog");
            }
        });
        findViewById(R.id.convert_date_activity_from_gregorian_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTextsDefault();
                Calendar now = Calendar.getInstance();
                GregorianDatePickerDialog dpd = GregorianDatePickerDialog.newInstance(
                        ConvertDateActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(ConvertDateActivity.this.getFragmentManager(),"GregorianDatePickerDialog");
            }
        });
        findViewById(R.id.convert_date_activity_from_gregorian_button).setClickable(false);
    }
    private void setTextsDefault(){
        Calendar gregorianCal = Calendar.getInstance();
        UmmalquraCalendar hijriCal = _TimesSET.getUmmalquraCalendar(this);
        ((TextView) findViewById(R.id.convert_date_activity_from_gregorian_button)).setText(
                _DisplaySET.formatStringNumbers(this,gregorianCal.get(Calendar.DAY_OF_MONTH) + " / " + (gregorianCal.get(Calendar.MONTH)+1) + " / " + gregorianCal.get(Calendar.YEAR))
        );
        ((TextView) findViewById(R.id.convert_date_activity_from_hijri_button)).setText(
                _DisplaySET.formatStringNumbers(this,hijriCal.get(Calendar.DAY_OF_MONTH) + " / " + (hijriCal.get(Calendar.MONTH)+1) + " / " + hijriCal.get(Calendar.YEAR))
        );
        ((TextView) findViewById(R.id.convert_date_activity_result_title)).setText("");
        findViewById(R.id.convert_date_activity_result_prayers_layout).setVisibility(View.GONE);
        findViewById(R.id.convert_date_activity_result_drop_button).setVisibility(View.GONE);
    }

    @Override
    public void onDateSet(GregorianDatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        calendar = adjustCalendarWithUmmalquraLimits(calendar);
        UmmalquraCalendar u = _TimesSET.getUmmalquraCalendar(this);
        u.setTime(calendar.getTime());
        ((TextView)findViewById(R.id.convert_date_activity_result_title))
                .setText(_DisplaySET.formatStringNumbers(this,getResources().getStringArray(R.array.day_of_week)[u.get(UmmalquraCalendar.DAY_OF_WEEK) - 1]
                        + "  " + u.get(UmmalquraCalendar.DAY_OF_MONTH) + "/" + (u.get(UmmalquraCalendar.MONTH)+1) + "/" +
                        u.get(UmmalquraCalendar.YEAR)));
        ((TextView) findViewById(R.id.convert_date_activity_from_gregorian_button)).setText(
                _DisplaySET.formatStringNumbers(this,dayOfMonth + " / " + (monthOfYear + 1) + " / " + year)
        );
        managePrayerTimes(calendar);
    }


    @Override
    public void onDateSet(HijriDatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        UmmalquraCalendar u = _TimesSET.getUmmalquraCalendar(this);
        u.set(UmmalquraCalendar.YEAR, year);
        u.set(UmmalquraCalendar.MONTH,monthOfYear);
        u.set(UmmalquraCalendar.DAY_OF_MONTH,dayOfMonth);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(u.getTime());
        ((TextView)findViewById(R.id.convert_date_activity_result_title))
                .setText(getResources().getStringArray(R.array.day_of_week)[calendar.get(Calendar.DAY_OF_WEEK) - 1]
                        + "  " + _DisplaySET.formatStringNumbers(this,(new SimpleDateFormat("dd/MM/yyyy")).format(calendar.getTime())));
        ((TextView) findViewById(R.id.convert_date_activity_from_hijri_button)).setText(
                _DisplaySET.formatStringNumbers(this,dayOfMonth + " / " + (monthOfYear + 1) + " / " + year)
        );
        managePrayerTimes(calendar);
    }

    private void managePrayerTimes(Calendar c) {
        final View button = findViewById(R.id.convert_date_activity_result_drop_button);
        final ViewGroup group = findViewById(R.id.convert_date_activity_result_prayers_layout);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
            }
        });
        _LocationSET.assignLocation(this,_LocationSET.currentLocation,"test");
        _LocationSET.updateDstToCurrent(this,"test",c);
        String[] times = _TimesSET.initializeTimesFor(this, "test",c);
        _LocationSET.clearTempLocationFile(this,"test");
        for(int i=0;i<times.length;i++){
            ((TextView)group.getChildAt(i+1)).setText(getResources().getStringArray(R.array.prayer_time)[i] + "\n" + _DisplaySET.formatStringNumbers(this,times[i]));
        }
    }
    private GregorianCalendar adjustCalendarWithUmmalquraLimits(GregorianCalendar calendar) {
        if(calendar.get(Calendar.YEAR) > 1937 && calendar.get(Calendar.YEAR) <2077) return calendar;
        if(calendar.get(Calendar.YEAR) == 1937 && calendar.get(Calendar.MONTH) > Calendar.MARCH) return calendar;
        if(calendar.get(Calendar.YEAR) == 1937 && calendar.get(Calendar.MONTH) == Calendar.MARCH && calendar.get(Calendar.DAY_OF_MONTH)>=14) return calendar;
        if(calendar.get(Calendar.YEAR) == 2077 && calendar.get(Calendar.MONTH) < Calendar.NOVEMBER) return calendar;
        if(calendar.get(Calendar.YEAR) == 2077 && calendar.get(Calendar.MONTH) == Calendar.NOVEMBER && calendar.get(Calendar.DAY_OF_MONTH)<=16) return calendar;
        GregorianCalendar calendar1 = new GregorianCalendar();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.alert_date_conversion_limits_title));
        builder.setMessage(getResources().getString(R.string.alert_date_conversion_limits_msg));
        builder.setPositiveButton(getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        setTextsDefault();
        builder.create().show();
        return calendar1;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
