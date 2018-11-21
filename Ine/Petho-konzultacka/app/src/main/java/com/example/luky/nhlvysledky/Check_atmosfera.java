package com.example.luky.nhlvysledky;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Check_atmosfera extends Fragment {
    private static Check_atmosfera fragment;
    public Check_atmosfera() {
    }
    public static Check_atmosfera newInstance() {
        if (fragment == null)
            fragment = new Check_atmosfera();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.check_atmosfera,container, false);
        return rootview;
    }
}
