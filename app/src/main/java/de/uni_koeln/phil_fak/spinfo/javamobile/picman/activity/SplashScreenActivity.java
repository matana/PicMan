package de.uni_koeln.phil_fak.spinfo.javamobile.picman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import de.uni_koeln.phil_fak.spinfo.javamobile.picman.R;


public class SplashScreenActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                Intent intent = new Intent(SplashScreenActivity.this, PicManActivity.class);
                startActivity(intent);
                finish();

            }

        }, SPLASH_TIME_OUT);
    }



}
