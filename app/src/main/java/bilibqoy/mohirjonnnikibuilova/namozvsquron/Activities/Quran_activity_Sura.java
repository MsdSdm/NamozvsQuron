package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;

import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;

public class Quran_activity_Sura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_sura);
        try {
            XmlPullParser ayaparser = getResources().getXml(R.xml.quran_simple);

            while (ayaparser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (ayaparser.getEventType() == XmlPullParser.START_TAG
                        && ayaparser.getName().equals("index")) {
                    Log.d("String", ayaparser.getAttributeValue(0));


                }
                ayaparser.next();
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


    }
}