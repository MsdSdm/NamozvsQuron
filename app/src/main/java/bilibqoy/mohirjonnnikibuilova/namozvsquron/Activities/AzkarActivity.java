package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter.AzkarRecyclerViewAdapter;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter.CountryAdapter;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter.CountryItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AzkarActivity extends AppCompatActivity {

    private List<String> listMain,listSanad,listRep,listSpel,listTrans;
    private RecyclerView recyclerView;
    private boolean[] transVisibility;
    private ArrayList<CountryItem> mCountryList;
    private CountryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_azkar);
        initializeLists();
        MobileAds.initialize(this, "ca-app-pub-3311803693724764~9440687858");
        AdView mAdView;
        mAdView = findViewById(R.id.bannerad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        initList();
        recyclerView = findViewById(R.id.activity_azkar_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        transVisibility = new boolean[]{false};
        recyclerView.setAdapter(new AzkarRecyclerViewAdapter(listMain,listSanad,listRep,listSpel,listTrans,transVisibility));
        ((Switch) findViewById(R.id.activity_azkar_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                transVisibility[0] = b;
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
        findViewById(R.id.activity_azkar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AzkarActivity.this.onBackPressed();
            }
        });
        if(getResources().getConfiguration().getLayoutDirection() == LayoutDirection.RTL)
            ((ImageView) findViewById(R.id.activity_azkar_back)).setImageDrawable(getResources().getDrawable(
                  R.drawable.ic_arrow_forward_black_24dp
            ));

        Spinner spinnerCountries = findViewById(R.id.activity_azkar_spinner);
        mAdapter = new CountryAdapter(this, mCountryList);
        spinnerCountries.setAdapter(mAdapter);
        spinnerCountries.setSelection(0);
        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateList(position);
                recyclerView.getAdapter().notifyDataSetChanged();
                //}
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void initList() {
        mCountryList = new ArrayList<>();
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list1, R.drawable.morning));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list2, R.drawable.moon));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list3, R.drawable.kaaba));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list4, R.drawable.day));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list5, R.drawable.summer));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list6, R.drawable.family));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list7, R.drawable.dinner));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list8, R.drawable.shield));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list10, R.drawable.babyboy));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list9, R.drawable.justicescale));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list11, R.drawable.award));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list12, R.drawable.selectable_item_background));
        mCountryList.add(new CountryItem(R.string.azkar_spinner_list13, R.drawable.loan));
    }

    private void updateList(int i) {
        listMain.clear();
        listSanad.clear();
        listRep.clear();
        listSpel.clear();
        listTrans.clear();
        switch (i){
            case 0:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_1))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_1))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_1))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_1))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_1))));
                break;
            case 1:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_2))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_2))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_2))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_2))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_2))));
                break;
            case 2:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_3))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_3))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_3))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_3))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_3))));
                break;
            case 3:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_4))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_4))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_4))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_4))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_4))));
                break;
            case 4:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_5))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_5))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_5))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_5))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_5))));
                break;
            case 5:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_6))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_6))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_6))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_6))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_6))));
                break;
             case 6:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_7))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_7))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_7))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_7))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_7))));
                break;
             case 7:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_8))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_8))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_8))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_8))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_8))));
                break;

          case 8:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_9))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_9))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_9))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_9))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_9))));
                break;

          case 9:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_10))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_10))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_10))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_10))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_10))));
                break;
           case 10:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_11))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_11))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_11))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_11))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_11))));
                break;
           case 11:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_12))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_12))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_12))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_12))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_12))));
                break;
            case 12:
                listMain.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_13))));
                listSanad.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_13))));
                listRep.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_13))));
                listSpel.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_13))));
                listTrans.addAll(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_13))));
                break;

            default:
        }
    }

    private void initializeLists() {
        listMain = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_1)));
        listSanad = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_sanad_1)));
        listRep = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_repetition_1)));
        listSpel = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_spel_1)));
        listTrans = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.azkar_translate_1)));

    }
}
