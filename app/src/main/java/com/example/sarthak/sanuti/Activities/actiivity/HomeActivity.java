package com.example.sarthak.sanuti.Activities.actiivity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sarthak.sanuti.Activities.Fragments.ConnectionRequest;
import com.example.sarthak.sanuti.Activities.Fragments.ConnectionShow;
import com.example.sarthak.sanuti.Activities.Fragments.FindPeopleFragments;
import com.example.sarthak.sanuti.Activities.Fragments.UserFragment;
import com.example.sarthak.sanuti.Activities.db.Databasehelper;
import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.example.sarthak.sanuti.Activities.utils.LatLang;
import com.example.sarthak.sanuti.Activities.utils.SharePrefrences;
import com.example.sarthak.sanuti.R;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    String user;
    String provider;
    String userid;
    int count=0;
    FragmentManager fragmentManager=getFragmentManager();
    double pi=3.14159;
    DatabaseReference  reference;
    LocationManager locationManager;
    LocationListener locationListener;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager=getFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                {

                    fragmentManager.beginTransaction().replace(R.id.content,new FindPeopleFragments()).commit();
                    return true;
                }
                case R.id.navigation_dashboard:
                   return true;
                case R.id.navigation_notifications:{
                    fragmentManager.beginTransaction().replace(R.id.content,new ConnectionShow()).commit();

                    return true;
                }
                case R.id.user_profile:{
                    fragmentManager.beginTransaction().replace(R.id.content,new UserFragment()).commit();
                    return true;
                }
                case R.id.request:{
                    fragmentManager.beginTransaction().replace(R.id.content,new ConnectionRequest()).commit();
                    return true;
                }
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupToolbar();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        user=SharePrefrences.readString(HomeActivity.this, CONFIG.userid);
        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        fragmentManager.beginTransaction().replace(R.id.content,new FindPeopleFragments()).commit();
        reference= FirebaseDatabase.getInstance().getReference(CONFIG.latlang_db_name);
       // StrictMode.setThreadPolicy((new StrictMode().ThreadPolicy.Builder().permitNetwork().build()));//to run internet on the main thread
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                double longi=location.getLongitude();
                double lati=location.getLatitude();
                Log.v("Latitude is ",String.valueOf(lati));
                LatLang latLang=new LatLang(user,String.valueOf(lati),String.valueOf(longi));
                new Databasehelper(HomeActivity.this).update_student("LATI",String.valueOf(lati),user);
                new Databasehelper(HomeActivity.this).update_student("LONGI",String.valueOf(longi),user);
                reference.child(user).setValue(latLang);
                locationManager.removeUpdates(locationListener);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
               Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };


        //Log.i("ID from shared",user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbaaroption,menu);
        return true;

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION
                }, 10);
                return;
            }
            else
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.logout);
        {
            LoginManager.getInstance().logOut();
            SharePrefrences.addString(HomeActivity.this,CONFIG.userid,null);
            SharePrefrences.addBoolean(HomeActivity.this,CONFIG.sucess_login,false);
            Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
            new Databasehelper(HomeActivity.this).delete_data(user);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // getSupportActionBar().show();
        toolbar.setTitle("Up Social");
        setSupportActionBar(toolbar);
       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //What to do on back clicked
            }
        });*/

    }
}
