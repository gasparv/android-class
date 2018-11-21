package com.example.luky.nhlvysledky;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luky.nhlvysledky.api_data.ApiTools;
import com.example.luky.nhlvysledky.api_data.JsonTools;
import com.example.luky.nhlvysledky.api_data.UserModel;
import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Future_matches extends Fragment  {

    private static Future_matches fragment;
    public Future_matches() {
    }
    public static Future_matches newInstance() {
        if (fragment == null)
            fragment = new Future_matches();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.future_matches,container, false);
        return rootview;

    }

}
