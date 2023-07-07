package com.prox.shortlink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        getDataFromApiAndSaveToPreferences();
    }

    private void getDataFromApiAndSaveToPreferences() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
//        String Bearer = sharedPreferences.getString("accessToken");
        String authorization = "Bearer " + sharedPreferences.getString("accessToken", null); // Thay YOUR_AUTH_TOKEN bằng token của bạn
        Log.i("AppPrefs_access_token", authorization);

        Call<OverviewResponse> call = apiService.getOverviewData(authorization);
        call.enqueue(new Callback<OverviewResponse>() {
            @Override
            public void onResponse(Call<OverviewResponse> call, Response<OverviewResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("fetch_overview", "success");
                    OverviewResponse overviewResponse = response.body();
                    if (overviewResponse != null) {
                        OverviewData data = overviewResponse.getData();

                        // Lưu dữ liệu vào SharedPreferences
                        saveDataToSharedPreferences(data);

                        // Hiển thị Fragment Dashboard
                        showDashboardFragment();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch data from API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OverviewResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fetching Failure, Please check internet connection !", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void saveDataToSharedPreferences(OverviewData data) {
        SharedPreferences sharedPreferences = getSharedPreferences("OverviewData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numberLinks", data.getNumberLinks());
        editor.putInt("numberUser", data.getNumberUser());
        editor.putInt("numberLinkOfUser", data.getNumberLinkOfUser());
        editor.apply();
    }

    private void showDashboardFragment() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, dashboardFragment);
        fragmentTransaction.commit();
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