package sk.tuke.smartlab.testovanieapiretrofit;

import com.google.gson.JsonArray;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiMethods {
    @GET("Movie")
    Call<JsonArray> vratMiFilmy();

    @POST("Movie")
    @FormUrlEncoded
    Call<ResponseBody> postMovie(@Field("Title") String NazovFilmu, @Field("Year") int RokVydania, @Field("Director") String reziser);
}
