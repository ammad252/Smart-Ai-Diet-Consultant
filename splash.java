package com.example.signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.splash);

        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);

                if(preferences.contains("isAdminLogin")){
                    Intent intent = new Intent(splash.this,Dashboard_d.class);
                    startActivity(intent);
                }
                if(preferences.contains("isUserLogin")){
                    Intent intent = new Intent(splash.this,dashboard_p.class);
                    startActivity(intent);
                }
                else {
                    Intent i = new Intent(splash.this, Login.class);
                    startActivity(i);
                    finish();
                }
            }
        },1700);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}