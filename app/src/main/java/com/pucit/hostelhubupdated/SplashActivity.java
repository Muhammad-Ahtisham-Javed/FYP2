package com.pucit.hostelhubupdated;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static int splashTimeOut = 2000;
//    public ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_splash);

//        logo=(ImageView)findViewById(R.id.imgv_splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this, MapsActivity.class);
                startActivity(i);
                finish();
            }
        },splashTimeOut);

//        Animation myanim= AnimationUtils.loadAnimation(this, R.anim.mysplashanimation);
//        logo.startAnimation(myanim);

    }

}
