package bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class RadioDialog extends AlertDialog.Builder {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String filekey;
    private int checkeditem;

    public RadioDialog(@NonNull Context context,String filename,String fileKey,String title) {
        super(context);
        preferences = context.getSharedPreferences(filename,Context.MODE_PRIVATE);
        editor = preferences.edit();
        filekey = fileKey;
        checkeditem = -1;
        this.setTitle(Html.fromHtml("<b>"+title+"</b>"));
    }
    public void initialize(String[] shownItems, final String[] values, final run onSelect){
        for(int i=0;i<shownItems.length;i++){
            if(preferences.getString(filekey,"").equals(values[i])) {checkeditem = i; break;}
        }
        this.setSingleChoiceItems(shownItems, checkeditem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editor.putString(filekey,values[i]);
                editor.commit();
                onSelect.go(i);
                dialogInterface.dismiss();
            }
        });
    }
    public void initialize(String[] shownItems, final int[] values, final run onSelect){
        for(int i=0;i<shownItems.length;i++){
            if(preferences.getInt(filekey,-9999) == values[i]) {checkeditem = i; break;}
        }
        this.setSingleChoiceItems(shownItems, checkeditem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editor.putInt(filekey,values[i]);
                editor.commit();
                onSelect.go(i);
                dialogInterface.dismiss();
            }
        });
    }
    public void initialize(String[] shownItems, final float[] values, final run onSelect){
        for(int i=0;i<shownItems.length;i++){
            if(preferences.getFloat(filekey, (float) -9999.9) == values[i]) {checkeditem = i; break;}
        }
        this.setSingleChoiceItems(shownItems, checkeditem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editor.putFloat(filekey,values[i]);
                editor.commit();
                onSelect.go(i);
                dialogInterface.dismiss();
            }
        });
    }

    public void initialize(String[] shownItems, final boolean[] values, final run onSelect) {
        if(preferences.getBoolean(filekey,false) == values[0]) checkeditem =0;
        else checkeditem = 1;
        this.setSingleChoiceItems(shownItems, checkeditem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editor.putBoolean(filekey,values[i]);
                editor.commit();
                onSelect.go(i);
                dialogInterface.dismiss();
            }
        });
    }

    public interface run{
        void go(int checked);
    }
}
