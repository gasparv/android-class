package sk.tuke.smartlab.novaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_data= findViewById(R.id.iv_data);
        Call<JsonArray> call = Tools.getApi().getData("10");
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Toast.makeText(MainActivity.this,"bavi to",Toast.LENGTH_LONG).show();
                JsonArray data = response.body();
                String url = data.get(0).getAsJsonObject().get("Images").getAsString();
                Picasso.get().load(url).into(iv_data);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(MainActivity.this,"nebavi to",Toast.LENGTH_LONG).show();
            }
        });

    }
}
