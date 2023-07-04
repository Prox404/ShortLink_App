package com.prox.shortlink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        String id = i.getStringExtra("userId");
        String username = i.getStringExtra("username");
        String hola = id + " " + username;
        textView = findViewById(R.id.text);
        textView.setText(hola);
    }
}