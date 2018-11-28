package sk.tuke.smartlab.jsonspracovanie;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiMethods {
    @GET("teams")
    Call<JsonObject> getTeams();

    @GET("teams/{id}")
    Call<JsonObject> getTeam(@Path("id") int id);
}
