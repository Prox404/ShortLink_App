package com.prox.shortlink;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prox.shortlink.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Ánh xạ các thành phần giao diện
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Khởi tạo Retrofit client
        apiService = RetrofitClient.getClient().create(ApiService.class);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị email và password từ EditText
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Gọi phương thức đăng nhập
                login(email, password);
            }
        });
    }

    private void login(String email, String password) {
        // Gửi yêu cầu đăng nhập đến API
        Call<LoginResponse> call = apiService.login(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.getAccessToken() != null) {
                        // Đăng nhập thành công, chuyển đến Activity home
                        String userId = loginResponse.getUserData().getId();
                        String username = loginResponse.getUserData().getUsername();
                        String refreshToken = loginResponse.getRefreshToken();
                        String accessToken = loginResponse.getAccessToken();


                        //Save to SharedPreference
                        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", userId);
                        editor.putString("username", username);
                        editor.putString("refreshToken", refreshToken);
                        editor.putString("accessToken", accessToken);
                        editor.apply();


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Kết thúc Activity đăng nhập
                    } else {
                        // Đăng nhập thất bại, thông báo lỗi
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Đăng nhập thất bại, thông báo lỗi
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc yêu cầu
                Toast.makeText(LoginActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}