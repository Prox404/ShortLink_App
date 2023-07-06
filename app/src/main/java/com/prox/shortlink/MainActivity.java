package com.prox.shortlink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new DashboardFragment());
    }

    private NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.i("navigation", "call");
            Fragment fragment;
            int itemId = item.getItemId();
//            Log.i("navigation", toString(itemId));
            if (itemId == R.id.navigation_dashboard) {
                fragment = new DashboardFragment();
                loadFragment(fragment);
                return true;
            } else if (itemId == R.id.navigation_link) {
                fragment = new LinkFragment();
                loadFragment(fragment);
                return true;
            } else if (itemId == R.id.navigation_contact) {
                fragment = new ContactFragment();
                loadFragment(fragment);
                return true;
            } else if (itemId == R.id.navigation_about) {
                fragment = new AboutFragment();
                loadFragment(fragment);
                return true;
            } else {
                return false;
            }
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}