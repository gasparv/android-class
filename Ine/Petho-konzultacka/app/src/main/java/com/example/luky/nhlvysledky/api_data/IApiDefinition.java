package com.example.luky.nhlvysledky.api_data;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IApiDefinition {
    @GET("v1/teams")
    Call<JsonArray> getUsers();
}
