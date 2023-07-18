package com.prox.shortlink;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddLinkFragment extends Fragment {

    private EditText editTextLink, editTextShortLink, editTextPassword;

    Spinner spinnerPrivacy;
    private Button buttonStore;
    private TextView textViewResult;
    private ApiService apiService;
    private String authorization;

    public AddLinkFragment() {
        // Required empty public constructor
    }

    public static AddLinkFragment newInstance() {
        AddLinkFragment fragment = new AddLinkFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_link, container, false);

        editTextLink = view.findViewById(R.id.editTextLink);
        editTextShortLink = view.findViewById(R.id.editTextShortLink);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonStore = view.findViewById(R.id.buttonStore);
        textViewResult = view.findViewById(R.id.textViewResult);
        spinnerPrivacy = view.findViewById(R.id.spinnerPrivacy);
        apiService = RetrofitClient.getClient().create(ApiService.class);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppPrefs", MODE_PRIVATE);
        authorization = "Bearer " + sharedPreferences.getString("accessToken", null);

        buttonStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = editTextLink.getText().toString();
                String shortLink = editTextShortLink.getText().toString();
                String password = editTextPassword.getText().toString();
                String privacy = spinnerPrivacy.getSelectedItem().toString();

                // Gọi phương thức lưu trữ link
                storeLink(link, shortLink, password, privacy);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void storeLink(String link, String shortLink, String password, String privacy) {
        // Gửi yêu cầu POST lưu trữ link
        Call<StoreLinkResponse> call = apiService.storeLink(authorization, link, shortLink, password, privacy);
        call.enqueue(new Callback<StoreLinkResponse>() {
            @Override
            public void onResponse(Call<StoreLinkResponse> call, Response<StoreLinkResponse> response) {
                if (response.isSuccessful()) {
                    StoreLinkResponse linkResponse = response.body();
                    if (linkResponse != null) {
                        // Hiển thị thông tin link vừa nhập ra
                        Link storedLink = linkResponse.getLink();
                        String message = "Short Link: " + storedLink.getShortLink() +
                                "\nPrivacy: " + storedLink.getPrivacy() +
                                "\nLink: " + storedLink.getLink();

                        showAlertDialog("Link Stored", message);

                        // Lưu link vào bộ nhớ tạm (SharedPreferences hoặc nơi khác)
                        saveLinkToMemory(storedLink);
                    }
                } else {
                    // Xử lý khi yêu cầu không thành công (lỗi server, lỗi dữ liệu, ...)
                    Gson gson = new Gson();
                    try {
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                        String error = "Failed to store link: " + errorResponse.getError().getMessage();

                        showAlertDialog("Error", error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<StoreLinkResponse> call, Throwable t) {
                // Xử lý khi có lỗi kết nối hoặc lỗi xử lý yêu cầu
                String error = "Error: " + t.getMessage();

                showAlertDialog("Error", error);
            }
        });
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void saveLinkToMemory(Link link) {
        // Lưu link vào bộ nhớ tạm (SharedPreferences hoặc nơi khác)
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LinkData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("shortLink", link.getShortLink());
        editor.putString("privacy", link.getPrivacy());
        editor.putString("link", link.getLink());
        editor.apply();
    }
}