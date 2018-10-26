package sk.tuke.smartlab.testovanieapiretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("http://147.232.153.12:8088/api/").build();
        ApiMethods api = retrofit.create(ApiMethods.class);
        api.vratMiFilmy().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                switch(response.code()){
                    case 200:{
                        JsonArray mojePole = response.body();
                        Movie movie = new Movie();
                        movie.director = mojePole.get(0).getAsJsonObject().get("director").getAsString();
                        movie.title = mojePole.get(0).getAsJsonObject().get("title").getAsString();
                        movie.year = mojePole.get(0).getAsJsonObject().get("year").getAsInt();
                        Toast.makeText(MainActivity.this, movie.title, Toast.LENGTH_LONG).show();
                        break;
                    }
                    case 401:
                    case 400:
                    case 404:{
                        break;
                    }
                    case 500:{
                        break;
                    }
                    default:{

                        break;
                    }
                }
               // Toast.makeText(MainActivity.this, "Bavi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Nebavi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
