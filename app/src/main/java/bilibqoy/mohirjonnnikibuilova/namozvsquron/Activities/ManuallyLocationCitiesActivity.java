package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter.CountriesRecyclerViewAdapter;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations._LocationSET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Locations.TimezoneMapper;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ManuallyLocationCitiesActivity extends AppCompatActivity {

    private List<TextView> cities ;
    private List<TextView> visibleCities;
    private RecyclerView recyclerView;
    private String tempLocationFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_manually_location_cities);
        tempLocationFile = getIntent().getExtras().getString("filename");
        readListFromCSV();

        recyclerView = findViewById(R.id.manually_location_cities_activity_countries_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CountriesRecyclerViewAdapter(visibleCities));

        final EditText search = findViewById(R.id.manually_location_cities_activity_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateRecycelerView(search.getText().toString());
            }
            @Override public void afterTextChanged(Editable editable) {}
        });
    }


    private void updateRecycelerView(String visibleCitiesString) {
        String s = visibleCitiesString.toLowerCase();
        visibleCities.clear();
        for(int i=0;i<cities.size();i++) if(cities.get(i).getText().toString().toLowerCase().contains(s))
            visibleCities.add(cities.get(i));
        recyclerView.getAdapter().notifyDataSetChanged();
    }
    private void readListFromCSV() {
        cities = new ArrayList<>();
        visibleCities = new ArrayList<>();
        TextView tx;
        final String countryName = getIntent().getExtras().getString("COUNTRY_NAME");
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getApplicationContext().getAssets().open("location_table_"+countryName.charAt(0)+".csv")));
            String[] nextLine;
            nextLine = new String[5];
            reader.skip(getIntent().getExtras().getInt("COUNTRY_INDEX")-1);
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                if(!nextLine[0].equals(countryName)) break;
                final String cityName = nextLine[1];
                final String latitude = nextLine[2];
                final String longitude = nextLine[3];
                tx = new TextView(this);
                tx.setText(cityName);
                tx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setLocation(cityName+", " +countryName,Float.parseFloat(latitude),Float.parseFloat(longitude));
                    }
                });
                cities.add(tx);
                visibleCities.add(tx);
            }
        } catch (IOException e) {

        }
    }

    private void setLocation(String s, float latitude, float longitude) {
        SharedPreferences preferences = getSharedPreferences(tempLocationFile,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Calendar calendar = new GregorianCalendar();
        String timezone = TimezoneMapper.latLngToTimezoneString(latitude,longitude);
        editor.putString("location_name",s);
        editor.putFloat("latitude",latitude);
        editor.putFloat("longitude",longitude);
        editor.putString("timezone", timezone);
        editor.putFloat("offset",_LocationSET.getTimeRawOffset(timezone));
        editor.putFloat("auto_offset",_LocationSET.getTimeRawOffset(timezone));
        editor.putFloat("auto_dst",_LocationSET.getTimeDstSaving(timezone,calendar));
        editor.putBoolean("islocationassigned",true);
        editor.commit();
        finish();
    }
}
