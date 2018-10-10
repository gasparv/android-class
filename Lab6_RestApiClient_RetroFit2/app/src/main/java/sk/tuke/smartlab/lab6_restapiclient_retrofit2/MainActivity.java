package sk.tuke.smartlab.lab6_restapiclient_retrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<UserModel> users;
    private TextView mFirstUserTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirstUserTV = findViewById(R.id.tv_firstUserName);
        Call<JsonArray> usersCall = ApiTools.getApi().getUsers();

        //This call does not block the thread but you have to treat the results in the callback methods onResponse or onFailure
        // To use a synchronous method use usersCall.execute and try catch the IOException. This call can block the UI thread.

        usersCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.code() == 200) {
                    users = JsonTools.parseToUsers(response.body());
                    mFirstUserTV.setText(users.get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
