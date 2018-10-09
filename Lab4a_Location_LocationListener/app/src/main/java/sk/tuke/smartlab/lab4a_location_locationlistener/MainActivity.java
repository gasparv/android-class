package sk.tuke.smartlab.lab4a_location_locationlistener;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView locationText;
    LocationManager locManager;
    private final int PERMISSION_LOCATION_ID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationText = findViewById(R.id.tv_location);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_LOCATION_ID);
            return;
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        locationText.setText("Lat:" + Double.toString(location.getLatitude()) + " Lon:" +  Double.toString(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(this,"GPS bolo zapnute",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(this,"GPS bolo vypnute",Toast.LENGTH_LONG).show();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_LOCATION_ID:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5,5,this);
                        }
                        else{
                            Toast.makeText(this,"Unable to get proper permission. You sure?",Toast.LENGTH_LONG).show();
                            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_LOCATION_ID);
                        }
                    }
                }
                break;
            }
        }
    }
}
