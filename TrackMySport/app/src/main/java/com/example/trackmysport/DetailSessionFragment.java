package com.example.trackmysport;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailSessionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;
    private String mParam7;
    private String mParam8;


    public DetailSessionFragment() {
        // Required empty public constructor
    }

    public static DetailSessionFragment newInstance(String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8) {
        DetailSessionFragment fragment = new DetailSessionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putString(ARG_PARAM8, param8);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
            mParam7 = getArguments().getString(ARG_PARAM7);
            mParam8 = getArguments().getString(ARG_PARAM8);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_session, container, false);

        //plan name
        TextView title_session = view.findViewById(R.id.title_session_name);
        title_session.setText(mParam1);

        //plan difficulty
        TextView diff_session = view.findViewById(R.id.title_session_difficulty);
        diff_session.setText(mParam2);

        //exercise1 name
        TextView ex1_name_session = view.findViewById(R.id.title_line1_1);
        ex1_name_session.setText(mParam3);

        //exercise1 time
        TextView ex1_time_session = view.findViewById(R.id.title_line1_2);
        ex1_time_session.setText(mParam4);

        //exercise1 reps
        TextView ex1_reps_session = view.findViewById(R.id.title_line1_3);
        ex1_reps_session.setText(mParam5);

        //exercise2 name
        TextView ex2_name_session = view.findViewById(R.id.title_line2_1);
        ex2_name_session.setText(mParam6);

        //exercise2 time
        TextView ex2_time_session = view.findViewById(R.id.title_line2_2);
        ex2_time_session.setText(mParam7);

        //exercise2 reps
        TextView ex2_reps_session = view.findViewById(R.id.title_line2_3);
        ex2_reps_session.setText(mParam8);


        return view;
    }
}