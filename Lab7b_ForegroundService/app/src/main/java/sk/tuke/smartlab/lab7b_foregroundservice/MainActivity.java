package sk.tuke.smartlab.lab7b_foregroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_start;
    Button btn_stop;
    Intent serviceLauncherIntent;
    TextView tv_svc_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceLauncherIntent = new Intent(MainActivity.this,ForegroundService.class);
        tv_svc_status = findViewById(R.id.tv_svc_state);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(serviceLauncherIntent);
                if(ForegroundService.isServiceStarted){
                    tv_svc_status.setText("RUNNING");
                }
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(serviceLauncherIntent);
                tv_svc_status.setText("STOPPED");
            }
        });
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction() == ForegroundService.BROADCAST_INTENT_ACTION)
                Toast.makeText(MainActivity.this,intent.getStringExtra(ForegroundService.BROADCAST_DATA),Toast.LENGTH_LONG).show();
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(br,new IntentFilter(ForegroundService.BROADCAST_INTENT_ACTION));

    }
}
