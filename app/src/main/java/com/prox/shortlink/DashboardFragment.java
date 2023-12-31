package com.prox.shortlink;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView tvNumberLinks, tvNumberUser, tvNumberLinkOfUser;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        tvNumberLinks = view.findViewById(R.id.tvNumberLinks);
        tvNumberUser = view.findViewById(R.id.tvNumberUser);
        tvNumberLinkOfUser = view.findViewById(R.id.tvNumberLinkOfUser);

        tvNumberLinks.setText("@@");
        tvNumberUser.setText("@@");
        tvNumberLinkOfUser.setText("@@");
        Log.i("dashboard", "call");

        // Inflate the layout for this fragment
        displayDataFromSharedPreferences();
        return view;
    }

    private void displayDataFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OverviewData", MODE_PRIVATE);
        int numberLinks = sharedPreferences.getInt("numberLinks", 0);
        int numberUser = sharedPreferences.getInt("numberUser", 0);
        int numberLinkOfUser = sharedPreferences.getInt("numberLinkOfUser", 0);

        tvNumberLinks.setText(String.valueOf(numberLinks));
        tvNumberUser.setText(String.valueOf(numberUser));
        tvNumberLinkOfUser.setText(String.valueOf(numberLinkOfUser));
    }
}