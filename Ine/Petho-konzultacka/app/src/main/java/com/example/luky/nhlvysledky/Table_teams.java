package com.example.luky.nhlvysledky;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Table_teams extends Fragment {
    private static Table_teams fragment;
    public Table_teams() {
    }
    public static Table_teams newInstance() {
        if (fragment == null)
            fragment = new Table_teams();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.table_teams,container, false);
        return rootview;
    }
}
