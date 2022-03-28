package com.example.dietapp6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

   private static int SPLASHSCREEN= 12000;
  private ImageView pic1, pic2;
  Animation pic1Animation, pic2Animation ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        pic1=findViewById(R.id.logo);
        pic2=findViewById(R.id.animationView);
        pic1Animation= AnimationUtils.loadAnimation(this,R.anim.up_to_down_alpha);
        pic2Animation= AnimationUtils.loadAnimation(this,R.anim.alpha);


        pic1.startAnimation(pic1Animation);
        pic2.startAnimation(pic2Animation);


        pic1.animate()
                .translationY(-1600)
                .setDuration(7000)
                .setStartDelay(8000);

        pic2.animate()
                .translationY(1600)
                .setDuration(7000)
                .setStartDelay(8000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(SplashScreen.this,MainActivity.class));
            }
        },SPLASHSCREEN);
    }
}