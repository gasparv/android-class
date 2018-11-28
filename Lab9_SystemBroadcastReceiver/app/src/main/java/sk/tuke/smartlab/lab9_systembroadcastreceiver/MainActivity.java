package sk.tuke.smartlab.lab9_systembroadcastreceiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private TextView tv_data;
    private final int PERMISSION_SMS = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        tv_data = findViewById(R.id.tv_data);
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},PERMISSION_SMS);
        }
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Toast.makeText(MainActivity.this,"SMS",Toast.LENGTH_LONG).show();
                //PDUS is a name of extra variable (remember PDU from networking in CISCO??? :) )

                //We get SMS data as an array of byte object messages. If only one sms is received at a time, we only get array of length 1
                Object[] smsExtra = (Object[]) intent.getExtras().get( "pdus" );

                //We iterate through our SMS messages
                for ( int i = 0; i < smsExtra.length; ++i )
                {
                    //We create an sms object out of our byte SMS by calling the createFromPdu
                    SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
                    String body = sms.getMessageBody();

                    //
                    //Note: If you want to change the way YOUR APP behaves e.g. at the night time when receiving SMS, this method is the way to go :)
                    //sms.getTimestampMillis();
                    //
                    String address = sms.getOriginatingAddress();
                    if(address.equals("+421905555555"))
                    {
                        tv_data.setText("Your boss is saying ... " +body);
                    }
                }
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case PERMISSION_SMS: {
                boolean RECEIVE_SMS_PERMISSION_GRANTED = false;
                boolean READ_SMS_PERMISSION_GRANTED = false;
                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i] == Manifest.permission.RECEIVE_SMS){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        RECEIVE_SMS_PERMISSION_GRANTED = true;
                    }
                    if(permissions[i] == Manifest.permission.READ_SMS){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        READ_SMS_PERMISSION_GRANTED = true;
                    }
                }
                if(!RECEIVE_SMS_PERMISSION_GRANTED || !READ_SMS_PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},PERMISSION_SMS);
                }
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
                registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
}
