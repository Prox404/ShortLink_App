package com.prox.shortlink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // Độ trễ để hiển thị splash screen (2 giây)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Kiểm tra trạng thái đăng nhập từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String username = preferences.getString("username", null);

        // Chuyển hướng tới Activity tương ứng
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (username != null) {
                    // Đã đăng nhập, chuyển tới MainActivity
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Chưa đăng nhập, chuyển tới HomeActivity
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish(); // Kết thúc Activity splash
            }
        }, SPLASH_DELAY);
    }
}