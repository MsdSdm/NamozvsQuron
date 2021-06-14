package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter.NameModel;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter.RecyclerAdapter;

public class NamesActivity extends AppCompatActivity {

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.names);

       MobileAds.initialize(this, "ca-app-pub-3311803693724764~9440687858");
       AdView mAdView;
       mAdView = findViewById(R.id.bannerad_view);
       AdRequest adRequest = new AdRequest.Builder().build();
       mAdView.loadAd(adRequest);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(NamesActivity.this);
         recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<NameModel> arrayList= new ArrayList<>();
         arrayList.add(new NameModel("الله",getString(R.string.mano1),"",getString(R.string.name1),R.raw.allah));
         arrayList.add(new NameModel("الرحمن",getString(R.string.mano2),"",getString(R.string.name2),R.raw.rahman));
         arrayList.add(new NameModel("الرحيم",getString(R.string.mano3),"",getString(R.string.name3),R.raw.rahim));
         arrayList.add(new NameModel("الملك",getString(R.string.mano4),"",getString(R.string.name4),R.raw.malik));
         arrayList.add(new NameModel("القدوس",getString(R.string.mano5),"",getString(R.string.name5),R.raw.quddus));
         arrayList.add(new NameModel("السلام",getString(R.string.mano6),"",getString(R.string.name6),R.raw.salam));
         arrayList.add(new NameModel("المؤمن",getString(R.string.mano7),"",getString(R.string.name7),R.raw.mumin));
         arrayList.add(new NameModel("المهيمن",getString(R.string.mano8),"",getString(R.string.name8),R.raw.muhaimin));
         arrayList.add(new NameModel("العزيز",getString(R.string.mano9),"",getString(R.string.name9),R.raw.aziz));
         arrayList.add(new NameModel("الجبار",getString(R.string.mano10),"",getString(R.string.name10),R.raw.jabbar));
         arrayList.add(new NameModel("المتكبر",getString(R.string.mano11),"",getString(R.string.name11),R.raw.mutakabbir));
         arrayList.add(new NameModel("الخالق",getString(R.string.mano12),"",getString(R.string.name12),R.raw.khaliq));
         arrayList.add(new NameModel("البارئ",getString(R.string.mano13),"",getString(R.string.name13),R.raw.bari));
         arrayList.add(new NameModel("المصور",getString(R.string.mano14),"",getString(R.string.name14),R.raw.musawwir));
         arrayList.add(new NameModel("آل الغفار",getString(R.string.mano15),"",getString(R.string.name15),R.raw.ghaffar));
         arrayList.add(new NameModel("القهار",getString(R.string.mano16),"",getString(R.string.name16),R.raw.qahhar));
         arrayList.add(new NameModel("الوهاب",getString(R.string.mano17),"",getString(R.string.name17),R.raw.wahhab));
         arrayList.add(new NameModel("الرزاق",getString(R.string.mano18),"",getString(R.string.name18),R.raw.razzaq));
         arrayList.add(new NameModel("الفتاح",getString(R.string.mano19),"",getString(R.string.name19),R.raw.fattah));
         arrayList.add(new NameModel("العليم",getString(R.string.mano20),"",getString(R.string.name20),R.raw.alim));
         arrayList.add(new NameModel("القابض",getString(R.string.mano21),"",getString(R.string.name21),R.raw.qabid));
         arrayList.add(new NameModel("الباسط",getString(R.string.mano22),"",getString(R.string.name22),R.raw.basit));
         arrayList.add(new NameModel("الخافض",getString(R.string.mano23),"",getString(R.string.name23),R.raw.khafid));
         arrayList.add(new NameModel("الرافع",getString(R.string.mano24),"",getString(R.string.name24),R.raw.rafi));
         arrayList.add(new NameModel("المعز",getString(R.string.mano25),"",getString(R.string.name25),R.raw.muizz));
         arrayList.add(new NameModel("المذل",getString(R.string.mano26),"",getString(R.string.name26),R.raw.mudhill));
         arrayList.add(new NameModel("السميع",getString(R.string.mano27),"",getString(R.string.name27),R.raw.sami));
         arrayList.add(new NameModel("البصير",getString(R.string.mano28),"",getString(R.string.name28),R.raw.basir));
         arrayList.add(new NameModel("الحكم",getString(R.string.mano29),"",getString(R.string.name29),R.raw.hakam));
         arrayList.add(new NameModel("العدل",getString(R.string.mano30),"",getString(R.string.name30),R.raw.adl));
         arrayList.add(new NameModel("اللطيف",getString(R.string.mano31),"",getString(R.string.name31),R.raw.latif));
         arrayList.add(new NameModel("الخبير",getString(R.string.mano32),"",getString(R.string.name32),R.raw.khabir));
         arrayList.add(new NameModel("الحليم",getString(R.string.mano33),"",getString(R.string.name33),R.raw.halim));
         arrayList.add(new NameModel("العظيم",getString(R.string.mano34),"",getString(R.string.name34),R.raw.azim));
         arrayList.add(new NameModel("الغفور",getString(R.string.mano35),"",getString(R.string.name35),R.raw.ghafur));
         arrayList.add(new NameModel("الشكور",getString(R.string.mano36),"",getString(R.string.name36),R.raw.shakur));
         arrayList.add(new NameModel("العلي",getString(R.string.mano37),"",getString(R.string.name37),R.raw.ali));
         arrayList.add(new NameModel("الكبير",getString(R.string.mano38),"",getString(R.string.name38),R.raw.kabir));
         arrayList.add(new NameModel("الحفيظ",getString(R.string.mano39),"",getString(R.string.name39),R.raw.hafiz));
         arrayList.add(new NameModel("المقيت",getString(R.string.mano40),"",getString(R.string.name40),R.raw.muqit));
         arrayList.add(new NameModel("الحسيب",getString(R.string.mano41),"",getString(R.string.name41),R.raw.hasib));
         arrayList.add(new NameModel("الجليل",getString(R.string.mano42),"",getString(R.string.name42),R.raw.jalil));
         arrayList.add(new NameModel("الكريم",getString(R.string.mano43),"",getString(R.string.name43),R.raw.karim));
         arrayList.add(new NameModel("الرقيب",getString(R.string.mano44),"",getString(R.string.name44),R.raw.raqib));
         arrayList.add(new NameModel("المجيب",getString(R.string.mano45),"",getString(R.string.name45),R.raw.mujib));
         arrayList.add(new NameModel("الواسع",getString(R.string.mano46),"",getString(R.string.name46),R.raw.wasi));
         arrayList.add(new NameModel("الحكيم",getString(R.string.mano47),"",getString(R.string.name47),R.raw.hakim));
         arrayList.add(new NameModel("الودود",getString(R.string.mano48),"",getString(R.string.name48),R.raw.wadud));
         arrayList.add(new NameModel("المجيد",getString(R.string.mano49),"",getString(R.string.name49),R.raw.majid));
         arrayList.add(new NameModel("الباعث",getString(R.string.mano50),"",getString(R.string.name50),R.raw.baith));
         arrayList.add(new NameModel("الشهيد",getString(R.string.mano51),"",getString(R.string.name51),R.raw.shahid));
         arrayList.add(new NameModel("الحق",getString(R.string.mano52),"",getString(R.string.name52),R.raw.haqq));
         arrayList.add(new NameModel("الوكيل",getString(R.string.mano53),"",getString(R.string.name53),R.raw.wakil));
         arrayList.add(new NameModel("القوى",getString(R.string.mano54),"",getString(R.string.name54),R.raw.qawi));
         arrayList.add(new NameModel("المتين",getString(R.string.mano55),"",getString(R.string.name55),R.raw.matin));
         arrayList.add(new NameModel("الولى",getString(R.string.mano56),"",getString(R.string.name56),R.raw.waliy));
         arrayList.add(new NameModel("الحميد",getString(R.string.mano57),"",getString(R.string.name57),R.raw.hamid));
         arrayList.add(new NameModel("المحصى",getString(R.string.mano58),"",getString(R.string.name58),R.raw.muhsi));
         arrayList.add(new NameModel("المبدئ",getString(R.string.mano59),"",getString(R.string.name59),R.raw.mubdi));
         arrayList.add(new NameModel("المعيد",getString(R.string.mano60),"",getString(R.string.name60),R.raw.muid));
         arrayList.add(new NameModel("المحيى",getString(R.string.mano61),"",getString(R.string.name61),R.raw.muhyi));
         arrayList.add(new NameModel("المميت",getString(R.string.mano62),"",getString(R.string.name62),R.raw.mumit));
         arrayList.add(new NameModel("الحي",getString(R.string.mano63),"",getString(R.string.name63),R.raw.hayy));
         arrayList.add(new NameModel("القيوم",getString(R.string.mano64),"",getString(R.string.name64),R.raw.qayyum));
         arrayList.add(new NameModel("الواجد",getString(R.string.mano65),"",getString(R.string.name65),R.raw.wajid));
         arrayList.add(new NameModel("الماجد",getString(R.string.mano66),"",getString(R.string.name66),R.raw.majid));
         arrayList.add(new NameModel("الواحد",getString(R.string.mano67),"",getString(R.string.name67),R.raw.wahid));
         arrayList.add(new NameModel("الصمد",getString(R.string.mano68),"",getString(R.string.name68),R.raw.samad));
         arrayList.add(new NameModel("القادر",getString(R.string.mano69),"",getString(R.string.name69),R.raw.qadir));
         arrayList.add(new NameModel("المقتدر",getString(R.string.mano70),"",getString(R.string.name70),R.raw.muqtadir));
         arrayList.add(new NameModel("المقدم",getString(R.string.mano71),"",getString(R.string.name71),R.raw.muqaddim));
         arrayList.add(new NameModel("المؤخر",getString(R.string.mano72),"",getString(R.string.name72),R.raw.muakhkhir));
         arrayList.add(new NameModel("الأول",getString(R.string.mano73),"",getString(R.string.name73),R.raw.awwal));
         arrayList.add(new NameModel("الأخر",getString(R.string.mano74),"",getString(R.string.name74),R.raw.akhir));
         arrayList.add(new NameModel("الظاهر",getString(R.string.mano75),"",getString(R.string.name75),R.raw.zahir));
         arrayList.add(new NameModel("الباطن",getString(R.string.mano76),"",getString(R.string.name76),R.raw.batin));
         arrayList.add(new NameModel("الوالي",getString(R.string.mano77),"",getString(R.string.name77),R.raw.wali));
         arrayList.add(new NameModel("المتعالي",getString(R.string.mano78),"",getString(R.string.name78),R.raw.muta_ali));
         arrayList.add(new NameModel("البر",getString(R.string.mano79),"",getString(R.string.name79),R.raw.barr));
         arrayList.add(new NameModel("التواب",getString(R.string.mano80),"",getString(R.string.name80),R.raw.tawwab));
         arrayList.add(new NameModel("المنتقم",getString(R.string.mano81),"",getString(R.string.name81),R.raw.muntaqim));
         arrayList.add(new NameModel("العفو",getString(R.string.mano82),"",getString(R.string.name82),R.raw.afuw));
         arrayList.add(new NameModel("الرؤوف",getString(R.string.mano83),"",getString(R.string.name83),R.raw.rauf));
         arrayList.add(new NameModel("مالك الملك",getString(R.string.mano84),"",getString(R.string.name84),R.raw.malik_ul_mulk));
         arrayList.add(new NameModel("ذو الجلال والإكرام",getString(R.string.mano85),"",getString(R.string.name85),R.raw.dhu_l_jalali_wal_ikram));
         arrayList.add(new NameModel("المقسط",getString(R.string.mano86),"",getString(R.string.name86),R.raw.muqsit));
         arrayList.add(new NameModel("الجامع",getString(R.string.mano87),"",getString(R.string.name87),R.raw.jami));
         arrayList.add(new NameModel("الغني",getString(R.string.mano88),"",getString(R.string.name88),R.raw.ghaniy));
         arrayList.add(new NameModel("المغني",getString(R.string.mano89),"",getString(R.string.name89),R.raw.mughni));
         arrayList.add(new NameModel("المانع",getString(R.string.mano90),"",getString(R.string.name90),R.raw.mani));
         arrayList.add(new NameModel("الضار",getString(R.string.mano91),"",getString(R.string.name91),R.raw.darr));
         arrayList.add(new NameModel("النافع",getString(R.string.mano92),"",getString(R.string.name92),R.raw.nafi));
         arrayList.add(new NameModel("النور",getString(R.string.mano93),"",getString(R.string.name93),R.raw.nur));
         arrayList.add(new NameModel("الهادي",getString(R.string.mano94),"",getString(R.string.name94),R.raw.hadi));
         arrayList.add(new NameModel("البديع",getString(R.string.mano95),"",getString(R.string.name95),R.raw.badi));
         arrayList.add(new NameModel("الباقي",getString(R.string.mano96),"",getString(R.string.name96),R.raw.baqi));
         arrayList.add(new NameModel("الوارث",getString(R.string.mano97),"",getString(R.string.name97),R.raw.warith));
         arrayList.add(new NameModel("الرشيد",getString(R.string.mano98),"",getString(R.string.name98),R.raw.rashid));
         arrayList.add(new NameModel("الصبور",getString(R.string.mano99),"",getString(R.string.name99),R.raw.sabur));



        RecyclerAdapter adapter=new RecyclerAdapter(NamesActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

        ImageView back = findViewById(R.id.activity_names_back);
        back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
          NamesActivity.super.onBackPressed();
         }
        });



    }


    @Override
    public void onBackPressed() {
                super.onBackPressed();
    }

    /*public void reviewAPPRequested(){
        ReviewManager manager= ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                ReviewInfo reviewInfo = task.getResult();
                manager.launchReviewFlow(NamesActivity.this, reviewInfo).addOnFailureListener(e -> Toast.makeText(NamesActivity.this, "Failed", Toast.LENGTH_SHORT).show()).addOnCompleteListener(task1 -> {
                   // Toast.makeText(NamesActivity.this, "Thanks", Toast.LENGTH_SHORT).show();
                    if (interstitialAd.isLoaded()){
                        interstitialAd.show();
                    }else {
                        NamesActivity.super.onBackPressed();
                    }
                });
            } else {
                //"não foi"
               // Toast.makeText(this,"",Toast.LENGTH_LONG).show();
            }
        });
    }*/
}
