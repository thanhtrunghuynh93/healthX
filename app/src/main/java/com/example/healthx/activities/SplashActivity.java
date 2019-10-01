package com.example.healthx.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthx.R;
import com.example.healthx.utils.Utils;

public class SplashActivity extends AppCompatActivity {
    ImageView ivLogo;
    TextView tvAppName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.hideStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        setValues();
    }

    private void initViews() {

        ivLogo = findViewById(R.id.ivLogo);
        tvAppName = findViewById(R.id.tvAppName);
    }

    private void setValues() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        ivLogo.startAnimation(animation);
        tvAppName.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Utils.startNewActivity(MainActivity.class,SplashActivity.this);
            finish();
        }, 5000);
    }
}
