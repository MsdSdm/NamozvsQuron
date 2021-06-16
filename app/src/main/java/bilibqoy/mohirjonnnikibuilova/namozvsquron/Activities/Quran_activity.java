package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.Adapter.MyListAdapter;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

public class Quran_activity extends AppCompatActivity {
    ListView list;

    String arabicayat, translationayat, nameayat, orderayat, numberayat;

    MyListAdapter adapter;

    List<String> arabic = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> order = new ArrayList<>();
    List<String> translation = new ArrayList<>();

    List<Integer> type = new ArrayList<>();
    List<String> number = new ArrayList<>();

    String[] arabicArray, iconTextarray, translationArray, nameArray, numerayatArray;

    // String[] typeArray;
    String typeayat;
    Integer[] typeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_demo);

        try {
            XmlPullParser parser = getResources().getXml(R.xml.quran_data);

            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("sura")) {
                    Log.d("Parse", parser.getAttributeValue(0) + "\n" //6
                            + parser.getAttributeValue(1) + "\n"//Mankind
                            + parser.getAttributeValue(2) + "\n"//114
                            + parser.getAttributeValue(3) + "\n"//    الناس
                            + parser.getAttributeValue(4) + "\n"//  21
                            + parser.getAttributeValue(5) + "\n"//   1
                            + parser.getAttributeValue(6) + "\n"// 6230
                            + parser.getAttributeValue(7) + "\n"//An-Naas
                            + parser.getAttributeValue(8) + "\n"//Meccan
                            + parser.getAttributeValue(0) + "\n"); //6

                    arabicayat = parser.getAttributeValue(3);
                    orderayat = parser.getAttributeValue(2);
                    translationayat = parser.getAttributeValue(1);
                    nameayat = parser.getAttributeValue(7);
                    numberayat = parser.getAttributeValue(0);
                    typeayat = parser.getAttributeValue(8);

                    if (typeayat.equals("Meccan")) {
                        type.add(R.drawable.kaabamecca);
                    } else {
                        type.add(R.drawable.mosque_cblack);
                    }

                    arabic.add(arabicayat);
                    order.add(orderayat);
                    translation.add(translationayat);
                    name.add(nameayat);
                    number.add(numberayat);
                    //  type.add(typeayat);
                }
                parser.next();
            }
        } catch (Throwable t) {
            Toast.makeText(this,
                    "Ошибка при загрузке XML-документа: " + t.toString(), Toast.LENGTH_LONG)
                    .show();
        }

        arabicArray = new String[arabic.size()];
        arabic.toArray(arabicArray);
        nameArray = new String[name.size()];
        name.toArray(nameArray);
        translationArray = new String[translation.size()];
        translation.toArray(translationArray);
        iconTextarray = new String[order.size()];
        order.toArray(iconTextarray);
        //  typeArray = new String[ type.size() ];
        // type.toArray( typeArray );
        typeArray = new Integer[type.size()];
        type.toArray(typeArray);
        numerayatArray = new String[number.size()];
        number.toArray(numerayatArray);


        adapter = new MyListAdapter(this, nameArray, translationArray, iconTextarray, arabicArray, typeArray, numerayatArray);//tayyormas ...
        Log.d("String: ", arabicayat);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);


        list.setOnItemClickListener((parent, view, position, id) -> {
            // TODO Auto-generated method stub
            Intent aqs = new Intent(Quran_activity.this, Quran_activity_Sura.class);
            startActivity(aqs);

         /* //  Toast.makeText(Quran_activity.this,, Toast.LENGTH_LONG).show();
              ///  Toast.makeText(getApplicationContext(),"" + position ,Toast.LENGTH_SHORT).show();

         /*  if(position == 0) {
                //code specific to first list item
                Toast.makeText(getApplicationContext(),"Place Your First Option Code" + position ,Toast.LENGTH_SHORT).show();
            }

            else if(position == 1) {
                //code specific to 2nd list item
                Toast.makeText(getApplicationContext(),"Place Your Second Option Code",Toast.LENGTH_SHORT).show();
            }

            else if(position == 2) {

                Toast.makeText(getApplicationContext(),"Place Your Third Option Code",Toast.LENGTH_SHORT).show();
            }
            else if(position == 3) {

                Toast.makeText(getApplicationContext(),"Place Your Forth Option Code",Toast.LENGTH_SHORT).show();
            }
            else if(position == 4) {

                Toast.makeText(getApplicationContext(),"Place Your Fifth Option Code",Toast.LENGTH_SHORT).show();
            }

          */
        });


    }
}
