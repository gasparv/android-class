package sk.tuke.smartlab.lab6_restapiclient_retrofit2;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IApiDefinition {
    @GET("users")
    Call<JsonArray> getUsers();
}
