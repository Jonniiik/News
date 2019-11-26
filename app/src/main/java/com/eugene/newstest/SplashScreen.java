package com.eugene.newstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        myThread.start();
    }

    Thread myThread = new Thread() {
        @Override
        public void run() {
            try {
                sleep(5500);
                Intent intent = new Intent(getApplicationContext(), ListNewsActivity.class);
                startActivity(intent);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
