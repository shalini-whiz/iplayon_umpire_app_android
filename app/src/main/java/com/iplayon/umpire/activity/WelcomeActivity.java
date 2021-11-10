package com.iplayon.umpire.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import com.iplayon.umpire.R;
import com.iplayon.umpire.adapter.ViewPagerAdapter;
import com.iplayon.umpire.util.SessionManager;
import com.iplayon.umpire.util.Util;


public class WelcomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    SessionManager mSession;
    TextView skipPage;
    private Util mUtil;
    Timer timer;
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUtil = new Util(WelcomeActivity.this);
        mUtil.setWindowStyle(WelcomeActivity.this);

        setContentView(R.layout.activity_welcome);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
        }

        mSession = new SessionManager(getApplicationContext());
        mUtil.setStatusBarTransparent(WelcomeActivity.this);

        if(mSession.ypGetUserName() != null)
        {


            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Intent lIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    lIntent.putExtra("display", "tournamentList");
                    lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(lIntent);

                }
            });

        }

        sliderDotspanel = (LinearLayout) findViewById(R.id.sliderDots);
        skipPage = (TextView) findViewById(R.id.skipPage);

        skipPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Intent lIntent = new Intent(getApplicationContext(), MainActivity.class);
                        lIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(lIntent);
                    }
                });


            }
        });


                viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < 3; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
            sliderDotspanel.setGravity(Gravity.CENTER);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        dots[2].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< 3; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }
                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                dots[2].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 2000);


    }

    public void onBackPressed() {
        mSession.clearSession();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 2000);
    }

    public void onDestroy(){
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();

    }


    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            WelcomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0)
                        viewPager.setCurrentItem(1);
                    else if(viewPager.getCurrentItem() == 1)
                        viewPager.setCurrentItem(2);
                    else if(viewPager.getCurrentItem() == 2)
                        viewPager.setCurrentItem(3);
                    else if(viewPager.getCurrentItem() == 3)
                        viewPager.setCurrentItem(4);
                    else if(viewPager.getCurrentItem() == 4)
                        viewPager.setCurrentItem(5);
                    else if(viewPager.getCurrentItem() == 5)
                        viewPager.setCurrentItem(6);
                    else if(viewPager.getCurrentItem() == 6)
                        viewPager.setCurrentItem(7);
                    else if(viewPager.getCurrentItem() == 7)
                        viewPager.setCurrentItem(8);
                    else if(viewPager.getCurrentItem() == 8)
                        viewPager.setCurrentItem(0);
                    else
                        viewPager.setCurrentItem(0);


                }
            });

        }
    }

}
