package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.content.Intent;
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
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ManuallyLocationActivity extends AppCompatActivity {

    private List<TextView> countries ;
    private List<TextView> visibleCountries;
    private RecyclerView recyclerView;
    private String tempLocationFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _DisplaySET.setLanguagePreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_location);
        tempLocationFile = getIntent().getExtras().getString("filename");
        readListFromCSV();

        recyclerView = findViewById(R.id.manually_location_activity_countries_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CountriesRecyclerViewAdapter(visibleCountries));


        final EditText search = findViewById(R.id.manually_location_activity_search);
        search.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateRecycelerView(search.getText().toString());
            }
            @Override public void afterTextChanged(Editable editable) {}
        });
    }

    private void updateRecycelerView(String visibleCountriesString) {
        String s = visibleCountriesString.toLowerCase();
        visibleCountries.clear();
        for(int i=0;i<countries.size();i++) if(countries.get(i).getText().toString().toLowerCase().contains(s))
            visibleCountries.add(countries.get(i));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void readListFromCSV() {
        countries = new ArrayList<>();
        visibleCountries = new ArrayList<>();
        TextView tx;

        final Intent intent = new Intent(this,ManuallyLocationCitiesActivity.class);
        intent.putExtra("filename",tempLocationFile);
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getApplicationContext().getAssets().open("location_table_countries.csv")));
            String[] nextLine;
            nextLine = new String[2];
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                final int countryCode = Integer.parseInt(nextLine[0]);
                final String countryName = nextLine[1];
                tx = new TextView(this);
                tx.setText(nextLine[1]);
                tx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("COUNTRY_INDEX",countryCode);
                        intent.putExtra("COUNTRY_NAME",countryName);
                        startActivity(intent);
                    }
                });
                countries.add(tx);
                visibleCountries.add(tx);
            }
        } catch (IOException e) {

        }
    }

    @Override
    protected void onResume() {
        SharedPreferences preferences = getSharedPreferences(tempLocationFile,MODE_PRIVATE);
        if(preferences.getBoolean("islocationassigned",false)) finish();
        super.onResume();

    }

}
