package bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final String[] iconText;
    private final String[] arabicayat;
    private final String[] numberayat;
    private final Integer[] type;

    public MyListAdapter(Activity context, String[] maintitle, String[] subtitle, String[] iconText, String[] arabicayat, Integer[] type, String[] numberayat) {
        super(context, R.layout.ayatslist, maintitle);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.maintitle = maintitle;
        this.subtitle = subtitle;
        this.iconText = iconText;
        this.numberayat = numberayat;
        this.arabicayat = arabicayat;
        this.type = type;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.ayatslist, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.nameofayat);
        TextView icon_number = (TextView) rowView.findViewById(R.id.icon_ayatnumber);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.tranlsationofayat);
        TextView arabic = (TextView) rowView.findViewById(R.id.arabicayat);
        TextView numberText = (TextView) rowView.findViewById(R.id.numberayat);
        ImageView typeimg = (ImageView) rowView.findViewById(R.id.typeayat);
        // ImageView typeImg = (ImageView) rowView.findViewById(R.id.typeayat);


        titleText.setText(maintitle[position]);
        icon_number.setText(iconText[position]);
        subtitleText.setText(subtitle[position]);
        arabic.setText(arabicayat[position]);
        numberText.setText(numberayat[position]);
        typeimg.setImageResource(type[position]);

        return rowView;

    }

}