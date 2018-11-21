package com.example.luky.nhlvysledky;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luky.nhlvysledky.LocationService.LocationService;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Near_stadium extends Fragment {

    private static Near_stadium fragment;
    private static Context context;
    MapView mapa;
    IMapController controllerMapy;

    private void initMap() {
        mapa.setTileSource(TileSourceFactory.MAPNIK);
        mapa.setMultiTouchControls(true);
        mapa.setBuiltInZoomControls(true);
        controllerMapy = mapa.getController();
        controllerMapy.setCenter(new GeoPoint(21.3d, 47.4d));
        controllerMapy.setZoom(10.0d);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            LocalBroadcastManager.getInstance(context).registerReceiver(locationReceiver,new IntentFilter(LocationService.BROADCAST_ACTION_NAME));
        initMap();

        if(!LocationService.isRunning)
            context.startService(new Intent(context,LocationService.class));
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public Near_stadium() {
    }

    public static Near_stadium newInstance(WeakReference<Activity> contextReference) {
        if (fragment == null)
            fragment = new Near_stadium();
            context = contextReference.get();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(context).registerReceiver(locationReceiver,new IntentFilter(LocationService.BROADCAST_ACTION_NAME));
        if(!LocationService.isRunning)
            context.startService(new Intent(context,LocationService.class));
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.near_stadion,container, false);
        mapa = rootview.findViewById(R.id.mapa_view);
        initMap();
        return rootview;
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
