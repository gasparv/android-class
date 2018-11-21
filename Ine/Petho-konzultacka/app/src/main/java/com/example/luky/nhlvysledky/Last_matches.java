package com.example.luky.nhlvysledky;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Last_matches extends Fragment {

    private static Last_matches fragment;
    public Last_matches() {
    }
    public static Last_matches newInstance() {
        if (fragment == null)
            fragment = new Last_matches();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootview =inflater.inflate(R.layout.last_matches,container, false);
        return rootview;
    }
}
