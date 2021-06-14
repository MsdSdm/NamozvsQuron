package bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSeekBar;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;


public class SeekBarDialog extends AlertDialog.Builder {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ViewGroup seekBarLayout;
    private String filekey;
    public SeekBarDialog(@NonNull Context context,String filename,String fileKey,String title) {
        super(context);
        preferences = context.getSharedPreferences(filename,Context.MODE_PRIVATE);
        editor = preferences.edit();
        filekey = fileKey;
        seekBarLayout = (ViewGroup) ViewGroup.inflate(context, R.layout.seekbar_dialog,null);
        this.setTitle(Html.fromHtml("<b>"+title+"</b>"));
        this.setView(seekBarLayout);
    }
    public void initialize(final int min, final int max, final run onOk){
        ((AppCompatSeekBar) seekBarLayout.getChildAt(3)).setProgress(getProgressFromValue(min,max,preferences.getInt(filekey,min)));
        ((TextView) seekBarLayout.getChildAt(1)).setText(Integer.toString(min));
        ((TextView) seekBarLayout.getChildAt(2)).setText(Integer.toString(max));
        ((TextView) seekBarLayout.getChildAt(0)).setText(Integer.toString(preferences.getInt(filekey,min)));
        ((AppCompatSeekBar) seekBarLayout.getChildAt(3)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ((TextView) seekBarLayout.getChildAt(0)).setText(Integer.toString(getValueFromProgress(min,max,i)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        this.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editor.putInt(filekey,Integer.parseInt(((TextView) seekBarLayout.getChildAt(0)).getText().toString()));
                editor.commit();
                onOk.go(Integer.parseInt(((TextView) seekBarLayout.getChildAt(0)).getText().toString()));
                dialogInterface.dismiss();
            }
        });
        this.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }
    private int getProgressFromValue(int min,int max,int x){
        return ((x-min)*100)/(max-min);
    }
    private int getValueFromProgress(int min,int max,int pro){
        return (((max-min)*pro)/100) + min;
    }
    public interface run{
        void go(int checked);
    }
}
