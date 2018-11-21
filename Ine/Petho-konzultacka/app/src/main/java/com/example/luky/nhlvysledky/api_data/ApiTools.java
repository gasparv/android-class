package com.example.luky.nhlvysledky.api_data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiTools {
    private static final String mBaseApiUrl = "https://records.nhl.com/site/api/";
    private static Retrofit mRetrofitInstance;

    private static Retrofit createRetrofitApiInstance(){
        if(mRetrofitInstance == null)
                    mRetrofitInstance = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(mBaseApiUrl).build();
        return mRetrofitInstance;
    }

    public static IApiDefinition getApi(){
        return createRetrofitApiInstance().create(IApiDefinition.class);
    }

}
