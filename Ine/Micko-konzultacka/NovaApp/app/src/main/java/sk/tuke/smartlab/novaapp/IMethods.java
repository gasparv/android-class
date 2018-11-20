package sk.tuke.smartlab.novaapp;

import com.google.gson.JsonArray;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMethods {
    @GET("uvod.php")
    Call<JsonArray> getData(@Query("id") String id);

}
