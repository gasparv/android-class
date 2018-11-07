package sk.tuke.smartlab.lab7a_intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button fireServicebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(MainActivity.this,intent.getStringExtra(SomeKindOfService.BROADCAST_SOME_DATA),Toast.LENGTH_LONG).show();
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,new IntentFilter(SomeKindOfService.BROADCAST_ACTION));
        fireServicebtn = findViewById(R.id.btn_fire_service);
        fireServicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyService();
            }
        });

    }

    private void startMyService(){

        Intent intentSentToService = new Intent(this,SomeKindOfService.class);
        intentSentToService.putExtra("dataThatCameFromActivity", true);
        startService(intentSentToService);
    }


}
