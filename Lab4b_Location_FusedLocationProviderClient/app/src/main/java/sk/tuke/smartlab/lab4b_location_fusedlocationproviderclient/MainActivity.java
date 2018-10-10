package sk.tuke.smartlab.lab4b_location_fusedlocationproviderclient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.location.LocationProvider;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient locationClient;
    private LocationRequest request;
    private TextView tv_location;
    private final int PERMISSION_FINE_LOCATION_ID = 1000;
    private boolean isRequestingLocationUpdates = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_location = findViewById(R.id.tv_location);
        locationClient = LocationServices.getFusedLocationProviderClient(this);

        request = new LocationRequest();
        request.setInterval(1000);
        request.setFastestInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION_ID);
            return;
        } else {
            registerRequestingLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void registerRequestingLocation() {
        if (!isRequestingLocationUpdates) {
            isRequestingLocationUpdates = true;
            locationClient.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    tv_location.setText(Double.toString(locationResult.getLocations().get(0).getLatitude()) + " " + Double.toString(locationResult.getLocations().get(0).getLongitude()));
                }
            }, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case PERMISSION_FINE_LOCATION_ID:{
                for(int i=0;i<permissions.length;i++){
                    if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION))
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            registerRequestingLocation();
                        }
                        else{
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION_ID);
                        }
                }
                break;
            }
        }
    }
}
