package bilibqoy.mohirjonnnikibuilova.namozvsquron.SuraListen;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;


public class RecyclerViewActivity extends AppCompatActivity implements InterfaceSure {
    private static final String TAG = "MyLog";
    AdapterRecyclerView adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Sure> sureList;
    List<Sure> sureler;
    DatabaseSure databaseSure;
    SearchView searchView;
    Map<Integer, String> sureMap = new HashMap<>();
    Sure sure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_recycler_view);
        sureler = new ArrayList<>();
        getMapData();
        addListData(sureMap);

        sureList = new ArrayList<>();
        databaseSure = new DatabaseSure(getApplicationContext());
        sureList = databaseSure.getSureList();
        adapter = new AdapterRecyclerView(sureler, getApplicationContext(), this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        try{
            MobileAds.initialize(this, "ca-app-pub-3311803693724764~9440687858");
            AdView adView = (AdView) findViewById(R.id.adView);
            //adView.setAdSize(AdSize.BANNER);
            //adView.setAdUnitId("ca-app-pub-3311803693724764/5256417379");
            AdRequest adRequest1 = new AdRequest.Builder().build();
            //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            // Check the LogCat to get your test device ID
            //.addTestDevice("5E834B5036EAD368F2C0548034D35DEC")
            // .build();
            adView.loadAd(adRequest1);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    Toast.makeText(getApplicationContext(), "Ad is loaded!",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdClosed() {
                    Toast.makeText(getApplicationContext(), "Ad is closed!",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    Toast.makeText(getApplicationContext(), "Error" + i, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "ErrorAd" + i);
                }

                @Override
                public void onAdImpression() {
                    Toast.makeText(getApplicationContext(), "Ad left application!",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdOpened() {
                    Toast.makeText(getApplicationContext(), "Ad is opened!",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                }

            });
            adView.loadAd(adRequest1);

        }catch (Exception e){
            Toast.makeText(this, "Error1 e" + e , Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ErrorAd1~" + e);
        }catch (ExceptionInInitializerError error){
            Toast.makeText(this, "Error2 e" + error , Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ErrorAd2~" + error);
        }catch (Error error){
            Toast.makeText(this, "Error3 e" + error , Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ErrorAd3~" + error);
        }



    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);

            return;
        }
        super.onBackPressed();


    }



    @Override
    public void play(String number, String name) {
        Intent intent = new Intent(RecyclerViewActivity.this, MediaPlayer2Activity.class);
        intent.putExtra("number", number);
        intent.putExtra("name", name);
     //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }


    private void getMapData() {
        sureMap.put(1, getString(R.string.sura1));
        sureMap.put(2, getString(R.string.sura2));
        sureMap.put(3, getString(R.string.sura3));
        sureMap.put(4, getString(R.string.sura4));
        sureMap.put(5, getString(R.string.sura5));
        sureMap.put(6, getString(R.string.sura6));
        sureMap.put(7, getString(R.string.sura7));
        sureMap.put(8, getString(R.string.sura8));
        sureMap.put(9, getString(R.string.sura9));
        sureMap.put(10, getString(R.string.sura10));
        sureMap.put(11, getString(R.string.sura11));
        sureMap.put(12, getString(R.string.sura12));
        sureMap.put(13, getString(R.string.sura13));
        sureMap.put(14, getString(R.string.sura14));
        sureMap.put(15, getString(R.string.sura15));
        sureMap.put(16, getString(R.string.sura16));
        sureMap.put(17, getString(R.string.sura17));
        sureMap.put(18, getString(R.string.sura18));
        sureMap.put(19, getString(R.string.sura19));
        sureMap.put(20, getString(R.string.sura20));
        sureMap.put(21, getString(R.string.sura21));
        sureMap.put(22, getString(R.string.sura22));
        sureMap.put(23, getString(R.string.sura23));
        sureMap.put(24, getString(R.string.sura24));
        sureMap.put(25, getString(R.string.sura25));
        sureMap.put(26, getString(R.string.sura26));
        sureMap.put(27, getString(R.string.sura27));
        sureMap.put(28, getString(R.string.sura28));
        sureMap.put(29, getString(R.string.sura29));
        sureMap.put(30, getString(R.string.sura30));
        sureMap.put(31, getString(R.string.sura31));
        sureMap.put(32, getString(R.string.sura32));
        sureMap.put(33, getString(R.string.sura33));
        sureMap.put(34, getString(R.string.sura34));
        sureMap.put(35, getString(R.string.sura35));
        sureMap.put(36, getString(R.string.sura36));
        sureMap.put(37, getString(R.string.sura37));
        sureMap.put(38, getString(R.string.sura38));
        sureMap.put(39, getString(R.string.sura39));
        sureMap.put(40, getString(R.string.sura40));
        sureMap.put(41, getString(R.string.sura41));
        sureMap.put(42, getString(R.string.sura42));
        sureMap.put(43, getString(R.string.sura43));
        sureMap.put(44, getString(R.string.sura44));
        sureMap.put(45, getString(R.string.sura45));
        sureMap.put(46, getString(R.string.sura46));
        sureMap.put(47, getString(R.string.sura47));
        sureMap.put(48, getString(R.string.sura48));
        sureMap.put(49, getString(R.string.sura49));
        sureMap.put(50, getString(R.string.sura50));
        sureMap.put(51, getString(R.string.sura51));
        sureMap.put(52, getString(R.string.sura52));
        sureMap.put(53, getString(R.string.sura53));
        sureMap.put(54, getString(R.string.sura54));
        sureMap.put(55, getString(R.string.sura55));
        sureMap.put(56, getString(R.string.sura56));
        sureMap.put(57, getString(R.string.sura57));
        sureMap.put(58, getString(R.string.sura58));
        sureMap.put(59, getString(R.string.sura59));
        sureMap.put(60, getString(R.string.sura60));
        sureMap.put(61, getString(R.string.sura61));
        sureMap.put(62, getString(R.string.sura62));
        sureMap.put(63, getString(R.string.sura63));
        sureMap.put(64, getString(R.string.sura64));
        sureMap.put(65, getString(R.string.sura65));
        sureMap.put(66, getString(R.string.sura66));
        sureMap.put(67, getString(R.string.sura67));
        sureMap.put(68, getString(R.string.sura68));
        sureMap.put(69, getString(R.string.sura69));
        sureMap.put(70, getString(R.string.sura70));
        sureMap.put(71, getString(R.string.sura71));
        sureMap.put(72, getString(R.string.sura72));
        sureMap.put(73, getString(R.string.sura73));
        sureMap.put(74, getString(R.string.sura74));
        sureMap.put(75, getString(R.string.sura75));
        sureMap.put(76, getString(R.string.sura76));
        sureMap.put(77, getString(R.string.sura77));
        sureMap.put(78, getString(R.string.sura78));
        sureMap.put(79, getString(R.string.sura79));
        sureMap.put(80, getString(R.string.sura80));
        sureMap.put(81, getString(R.string.sura81));
        sureMap.put(82, getString(R.string.sura82));
        sureMap.put(83, getString(R.string.sura83));
        sureMap.put(84, getString(R.string.sura84));
        sureMap.put(85, getString(R.string.sura85));
        sureMap.put(86, getString(R.string.sura86));
        sureMap.put(87, getString(R.string.sura87));
        sureMap.put(88, getString(R.string.sura88));
        sureMap.put(89, getString(R.string.sura89));
        sureMap.put(90, getString(R.string.sura90));
        sureMap.put(91, getString(R.string.sura91));
        sureMap.put(92, getString(R.string.sura92));
        sureMap.put(93, getString(R.string.sura93));
        sureMap.put(94, getString(R.string.sura94));
        sureMap.put(95, getString(R.string.sura95));
        sureMap.put(96, getString(R.string.sura96));
        sureMap.put(97, getString(R.string.sura97));
        sureMap.put(98, getString(R.string.sura98));
        sureMap.put(99, getString(R.string.sura99));
        sureMap.put(100,getString(R.string.sura100));
        sureMap.put(101,getString(R.string.sura101));
        sureMap.put(102,getString(R.string.sura102));
        sureMap.put(103,getString(R.string.sura103));
        sureMap.put(104,getString(R.string.sura104));
        sureMap.put(105,getString(R.string.sura105));
        sureMap.put(106,getString(R.string.sura106));
        sureMap.put(107,getString(R.string.sura107));
        sureMap.put(108,getString(R.string.sura108));
        sureMap.put(109,getString(R.string.sura109));
        sureMap.put(110,getString(R.string.sura110));
        sureMap.put(111,getString(R.string.sura111));
        sureMap.put(112,getString(R.string.sura112));
        sureMap.put(113,getString(R.string.sura113));
        sureMap.put(114,getString(R.string.sura114));


    }

    public void addListData(Map<Integer, String> sureMap) {
        for (int i = 1; i < sureMap.size() + 1; i++) {
            sure = new Sure();
            sure.setNumber(String.valueOf(i));
            sure.setName(sureMap.get(i));
            System.out.println("sure=" + sure);
            sureler.add(sure);

        }

    }


}

/*

 */