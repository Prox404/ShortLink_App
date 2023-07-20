package com.prox.shortlink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.prox.shortlink.response.EditLinkResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditLinkActivity extends AppCompatActivity {

    private ApiService apiService;
    EditText edtLink, edtShortLink, edtPassword;
    Spinner spnPrivacy;
    Button btnSave;
    String authorization;
    String currentLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_link);

        edtLink = findViewById(R.id.edt_link);
        edtShortLink = findViewById(R.id.edt_short_link);
        edtPassword = findViewById(R.id.edt_password);
        spnPrivacy = findViewById(R.id.spn_privacy);
        btnSave = findViewById(R.id.btn_save);
        apiService = RetrofitClient.getClient().create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        authorization = "Bearer " + sharedPreferences.getString("accessToken", null);

        Intent i = getIntent();
        Link link = (Link) i.getSerializableExtra("link");
        if(link != null){
            edtLink.setText(link.getLink());
            edtShortLink.setText(link.getShortLink());
            currentLink = link.getShortLink();
                    edtPassword.setText(link.getPassword());
            if (link.getPrivacy().equals("public")){
                spnPrivacy.setSelection(0);
            }else{
                spnPrivacy.setSelection(1);
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = edtLink.getText().toString();
                String shortLink = edtShortLink.getText().toString();
                String password = edtPassword.getText().toString();
                String privacy = spnPrivacy.getSelectedItem().toString().toLowerCase();
                Log.i("params", link + "-" + currentLink + "-" + shortLink + "-" + password + "-" + privacy + "-" + authorization);

                updateLink(currentLink, link, shortLink, password, privacy);
            }
        });
    }

    private void updateLink(String currentShortLink, String link, String newShortLink, String password , String privacy) {
        Call<EditLinkResponse> call = apiService.updateLink(authorization, currentShortLink, newShortLink,link, password, privacy);
        call.enqueue(new Callback<EditLinkResponse>() {
            @Override
            public void onResponse(Call<EditLinkResponse> call, Response<EditLinkResponse> response) {
                if (response.isSuccessful()) {
                    EditLinkResponse linkResponse = response.body();
                    if (linkResponse != null) {
                        // Link đã được cập nhật thành công
                        Link updatedLink = linkResponse.getLink();
                        Log.d("UpdateSuccess", "Updated link: " + updatedLink.getShortLink());
                    }
                } else {
                    // Xử lý khi yêu cầu không thành công (lỗi server, lỗi dữ liệu, ...)
                    Log.e("UpdateError", "Failed to update link: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<EditLinkResponse> call, Throwable t) {
                // Xử lý khi có lỗi kết nối hoặc lỗi xử lý yêu cầu
                Log.e("UpdateError", "Error: " + t.getMessage());
            }
        });
    }
}