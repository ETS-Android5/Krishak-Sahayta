package com.example.simran.krishaksahayata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends Activity {

    ImageView ivSplash;
    TextView tvMotto;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivSplash=findViewById(R.id.ivSplash);
        tvMotto = findViewById(R.id.tvMotto);
        animation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.a1);
        ivSplash.setAnimation(animation);
        tvMotto.startAnimation(animation);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent a = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(a);
                finish();
            }
        }).start();

    }
}