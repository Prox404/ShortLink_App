package com.prox.shortlink;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Ánh xạ các thành phần giao diện
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

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
        // Truyền email và password dưới dạng x-www-urlencoded

        // Đoạn mã xử lý gửi yêu cầu API và nhận dữ liệu trả về
        // Trong ví dụ này, chúng ta giả định rằng yêu cầu thành công và nhận được dữ liệu JSON

        // Giả sử dữ liệu trả về từ API
        String jsonResponse = "{\"data\":{\"_id\":\"63ac7a849cdf1cbcce1c33d8\",\"username\":\"Proxx\",\"email\":\"prox@gmail.com\",\"avatar\":\"https://i.ibb.co/176z8y8/Prox-logo-white.png\",\"createdAt\":\"2022-12-28T17:19:00.365Z\",\"updatedAt\":\"2023-01-14T18:46:01.566Z\",\"__v\":0},\"accessToken\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2M2FjN2E4NDljZGYxY2JjY2UxYzMzZDgiLCJpYXQiOjE2ODgzOTUxNDAsImV4cCI6MTY4ODM5ODc0MH0.DafrDOE-YwKG0kB1XJQ_CPd2EGyafva1LTz98D65uKk\",\"refreshToken\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2M2FjN2E4NDljZGYxY2JjY2UxYzMzZDgiLCJpYXQiOjE2ODgzOTUxNDAsImV4cCI6MTcxOTkzMTE0MH0.Wp8-UuzscPIJ4BrXr9gT_nb5W2PUmwqjDnZbOGUgrhE\"}";

        // Xử lý dữ liệu trả về
        try {
            // Chuyển đổi chuỗi JSON thành đối tượng JSON
            JSONObject response = new JSONObject(jsonResponse);

            // Kiểm tra nếu đăng nhập thành công
            if (response.has("accessToken")) {
                // Lấy thông tin người dùng từ đối tượng JSON
                JSONObject userData = response.getJSONObject("data");
                String userId = userData.getString("_id");
                String username = userData.getString("username");

                // Chuyển đến Activity home
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("username", username);
                startActivity(intent);
                finish(); // Kết thúc Activity đăng nhập
            } else {
                // Đăng nhập thất bại, thông báo lỗi
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
        }
    }
}