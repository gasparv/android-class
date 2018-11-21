package com.example.luky.nhlvysledky;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragmentToSwitchTo;
    FragmentManager fm;
    FragmentTransaction transaction;
    private final int PERMISSION_REQ_STORAGE_FINE_LOCATION = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fm = getSupportFragmentManager();
        FragmentTransaction onCreateFragmentTransaction = fm.beginTransaction();
        onCreateFragmentTransaction.replace(R.id.main_container,MainPage.newInstance());
        onCreateFragmentTransaction.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        transaction = fm.beginTransaction();

        if (id == R.id.nav_mainPage) {

            fragmentToSwitchTo = MainPage.newInstance();
            transaction.replace(R.id.main_container, fragmentToSwitchTo);
            transaction.commit();
            }
        else if (id == R.id.nav_futureMatch) {

            fragmentToSwitchTo = Future_matches.newInstance();
            transaction.replace(R.id.main_container, fragmentToSwitchTo);
            transaction.commit();

        } else if (id == R.id.nav_lastMatch) {

            fragmentToSwitchTo = Last_matches.newInstance();
            transaction.replace(R.id.main_container, fragmentToSwitchTo);
            transaction.commit();

        } else if (id == R.id.nav_tableTeams) {

            fragmentToSwitchTo = Table_teams.newInstance();
            transaction.replace(R.id.main_container, fragmentToSwitchTo);
            transaction.commit();

        }  else if (id == R.id.nav_checkAtmosfera) {

            fragmentToSwitchTo = Check_atmosfera.newInstance();
            transaction.replace(R.id.main_container, fragmentToSwitchTo);
            transaction.commit();
        } else if (id == R.id.nav_nearStadium) {
            ReqPermissionsForNearStadium();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void SwitchToNearStadium(){
        fragmentToSwitchTo = Near_stadium.newInstance(new WeakReference<Activity>(this));
        transaction.replace(R.id.main_container, fragmentToSwitchTo);
        transaction.commit();
    }
    private void ReqPermissionsForNearStadium(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQ_STORAGE_FINE_LOCATION);
        }
        else
            SwitchToNearStadium();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSION_REQ_STORAGE_FINE_LOCATION:{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                {
                    SwitchToNearStadium();
                }
                else{
                    new AlertDialog.Builder(this)
                            .setTitle("")
                            .setMessage("")
                            .setPositiveButton("", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ActivityCompat.requestPermissions();
                                }
                            })
                            .setNegativeButton("", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .show();
                }
                break;
            }
        }
    }
}
