package bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final String[] iconText;
    private final String[] arabicayat;

    public MyListAdapter(Activity context, String[] maintitle, String[] subtitle, String[] iconText, String[] arabicayat) {
        super(context, R.layout.ayatslist, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.iconText=iconText;

        this.arabicayat = arabicayat;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.ayatslist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        TextView icon_number = (TextView) rowView.findViewById(R.id.icon_number);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        TextView arabic = (TextView) rowView.findViewById(R.id.arabicayat);

        titleText.setText(maintitle[position]);
        icon_number.setText(iconText[position]);
        subtitleText.setText(subtitle[position]);
        arabic.setText(arabicayat[position]);

        return rowView;

    };
}