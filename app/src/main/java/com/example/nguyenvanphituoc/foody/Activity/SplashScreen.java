package com.example.nguyenvanphituoc.foody.Activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyenvanphituoc.foody.R;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {
    // Animation
//    AnimationDrawable animRotate;
    Animation animRotate;
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView imgRotate = (ImageView) findViewById(R.id.imgSplashScreen);
//        animRotate = (AnimationDrawable) txtLoading.getCompoundDrawables()[0];
//        animRotate.start();
//// load the animation
        animRotate = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
        animRotate.setAnimationListener(this);
        imgRotate.startAnimation(animRotate);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
