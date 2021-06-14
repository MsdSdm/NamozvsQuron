package bilibqoy.mohirjonnnikibuilova.namozvsquron.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Display._DisplaySET;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.PrefManager;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.R;
import bilibqoy.mohirjonnnikibuilova.namozvsquron.Settings.RadioDialog;


public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;
    RadioDialog dialog;
    public static boolean reloadWelcomeActivityOnResume = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        reloadWelcomeActivityOnResume = false;
        _DisplaySET.setLanguagePreferences(this);
        setContentView(R.layout.activity_splash_screen);


        ///


            dialog = new RadioDialog(this, _DisplaySET.displayFile, "language", "Choose Language");
            dialog.initialize(new String[]{"O'zbekcha", "عربي", "English", "Русский", "Türk", "նպատակա", "azerbaycan", "Shqiptare", "afrikaans", "हिंदी", "italiano", "Indonesia", "français", "точикча"}, new String[]{"uz", "ar", "en", "ru", "tr", "hy", "az", "sq", "af", "hi", "it", "in", "fr", "tg"}, checked -> {
                _DisplaySET.setLanguagePreferences(WelcomeActivity.this);
                SettingsActivity.reloadSettingsActivityOnResume = true;
                MainActivity.reloadMainActivityOnResume = true;
                WelcomeActivity.reloadWelcomeActivityOnResume = true;
                btnNext.setText(getString(R.string.next));
                btnSkip.setText(getString(R.string.skip));
                TextView textView1 = (TextView) findViewById(R.id.title);
                textView1.setText(R.string.slide_1_title);
                TextView textView = (TextView) findViewById(R.id.subtitle);
                textView.setText(R.string.slide_2_desc);

               // startActivity(new Intent(this, WelcomeActivity.class));

                // finish();
            });
       // try {
            dialog.show();
       // }
       // catch (Exception e){
        //    Log.e("Tag", "Error: " + e );

       // }

        viewPager =  findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2};






        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(v -> launchHomeScreen());

        btnNext.setOnClickListener(v -> {
            // checking for last page
            // if last page home screen will be launched
            int current = getItem();
            if (current < layouts.length) {
                // move to next screen
                viewPager.setCurrentItem(current);
            } else {
                launchHomeScreen();
            }
        });
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(reloadWelcomeActivityOnResume){
            reloadWelcomeActivityOnResume = false;
            recreate();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem() {
        return viewPager.getCurrentItem() + 1;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivityForResult(new Intent(this, MainActivity.class), 10000);
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    //////////////////////////////////////////////


    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {

        public MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert layoutInflater != null;
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}