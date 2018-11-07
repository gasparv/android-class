package sk.tuke.smartlab.lab7_backgroundservice_location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapaActivity extends AppCompatActivity{

    MapView mapa;
    IMapController controllerMapy;

    private void initMap() {
        mapa.setTileSource(TileSourceFactory.MAPNIK);
        mapa.setMultiTouchControls(true);
        mapa.setBuiltInZoomControls(true);
        controllerMapy = mapa.getController();
        controllerMapy.setCenter(new GeoPoint(0d, 0d));
        controllerMapy.setZoom(18.0d);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        LocalBroadcastManager.getInstance(this).registerReceiver(locationReceiver,new IntentFilter(LocationService.BROADCAST_ACTION_NAME));
        initMap();

        if(!LocationService.isRunning)
            startService(new Intent(this,LocationService.class));
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        mapa = findViewById(R.id.mapa_view);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION},1000);
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(locationReceiver,new IntentFilter(LocationService.BROADCAST_ACTION_NAME));
        initMap();

        if(!LocationService.isRunning)
        startService(new Intent(this,LocationService.class));
    }


    private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() == LocationService.BROADCAST_ACTION_NAME){
                double latitude = intent.getDoubleExtra(LocationService.BROADCAST_INTENT_LAT,0d);
                double longitude = intent.getDoubleExtra(LocationService.BROADCAST_INTENT_LON,0d);
                controllerMapy.setCenter(new GeoPoint(latitude,longitude));
                Marker mojaPozicia = new Marker(mapa);
                mojaPozicia.setPosition(new GeoPoint(latitude,longitude));
                mapa.getOverlays().add(mojaPozicia);
            }
        }
    };
}
