package com.prox.shortlink;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LinkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LinkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private RecyclerView recyclerView;
    private LinkAdapter linkAdapter;
    private List<Link> linkList;

    private int currentPage = 1;
    private int totalPages = 1;
    private int totalLinks = 0;

    private ApiService apiService;
    private String authorization;


    public LinkFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LinkFragment newInstance() {
        LinkFragment fragment = new LinkFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Lấy token từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppPrefs", MODE_PRIVATE);
        authorization = "Bearer " + sharedPreferences.getString("accessToken", null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_link, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        linkList = new ArrayList<>();
        linkAdapter = new LinkAdapter(getActivity(), linkList);
        recyclerView.setAdapter(linkAdapter);

        // Gọi API để lấy danh sách link
        getAllLinks();

        return view;
    }

    private void getAllLinks() {
        getLinks(1);
    }

    private void getLinks(int page) {
        Call<GetAllLinkResponse> call = apiService.getLinks(authorization, page);
        call.enqueue(new Callback<GetAllLinkResponse>() {
            @Override
            public void onResponse(Call<GetAllLinkResponse> call, Response<GetAllLinkResponse> response) {
                if (response.isSuccessful()) {
                    GetAllLinkResponse linkResponse = response.body();
                    if (linkResponse != null) {
                        // Lấy dữ liệu từ Response
                        List<Link> links = linkResponse.getLinks();

                        // Cập nhật danh sách link và hiển thị
                        linkList.addAll(links);
                        linkAdapter.notifyDataSetChanged();

                        int currentPage = linkResponse.getCurrentPage();
                        int totalPages = linkResponse.getTotalPages();

                        // Gọi API cho trang tiếp theo nếu còn trang
                        if (currentPage < totalPages) {
                            getLinks(currentPage + 1);
                        }
                    }
                } else {
                    Log.e("LinkFragment", "Failed to get links: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GetAllLinkResponse> call, Throwable t) {
                Log.e("LinkFragment", "Error: " + t.getMessage());
            }
        });
    }
}