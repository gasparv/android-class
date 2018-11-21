package com.example.luky.nhlvysledky;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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


public class MainPage extends Fragment {
    private static MainPage fragment;

    /*START API DATA*/
    private List<UserModel> users;
    private TextView mTeam;
    /*END API DATA*/

 
    private Color farba;
    public MainPage() {
    }
    public static MainPage newInstance() {
        if(fragment == null)
            fragment = new MainPage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mTeam = rootView.findViewById(R.id.tv_tim);
        Call<JsonArray> usersCall = ApiTools.getApi().getUsers();

        usersCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.code() == 200) {
                    users = JsonTools.parseToUsers(response.body());
                    mTeam.setText(users.get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

               Toast.makeText(getActivity(), t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        /* tv_text = rootView.findViewById(R.id.dajakyTextZosFragmentu);
        tv_text.setText("Nejaky novy text");
*/
/*
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        return rootView;
    }


}
