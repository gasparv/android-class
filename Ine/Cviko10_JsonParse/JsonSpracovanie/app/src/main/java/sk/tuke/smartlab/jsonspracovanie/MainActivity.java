package sk.tuke.smartlab.jsonspracovanie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private ApiMethods api;
    private List<Team> zoznamNacitanychTimov;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zoznamNacitanychTimov = new ArrayList<>();
    retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://statsapi.web.nhl.com/api/v1/").build();
    api = retrofit.create(ApiMethods.class);

    api.getTeams().enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            zoznamNacitanychTimov = JsonTools.convertJsonToTeams(response.body());
            Toast.makeText(MainActivity.this,"",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            Toast.makeText(MainActivity.this,"Nepodarilo sa",Toast.LENGTH_LONG).show();
        }
    });
    }
}
