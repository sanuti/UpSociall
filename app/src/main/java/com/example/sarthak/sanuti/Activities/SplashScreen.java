package com.example.sarthak.sanuti.Activities;

import com.crashlytics.android.Crashlytics;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import io.fabric.sdk.android.Fabric;

import com.example.sarthak.sanuti.Activities.actiivity.HobbiesSelection;
import com.example.sarthak.sanuti.Activities.actiivity.HomeActivity;
import com.example.sarthak.sanuti.Activities.actiivity.LoginActivity;
import com.example.sarthak.sanuti.Activities.db.Databasehelper;
import com.example.sarthak.sanuti.Activities.utils.CONFIG;
import com.example.sarthak.sanuti.Activities.utils.SharePrefrences;
import com.example.sarthak.sanuti.R;

public class SplashScreen extends AppCompatActivity {
        RelativeLayout r;
    boolean connected = false;
    ConnectivityManager cm;
    NetworkInfo info;
    Databasehelper hd;
    boolean success=false,hobby=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        r=findViewById(R.id.layout);
        cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      // info=cm.getActiveNetworkInfo();

        //Toast.makeText(this, "In main", Toast.LENGTH_SHORT).show();
        if (isNetworkAvailable()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    success= SharePrefrences.readBoolean(SplashScreen.this, CONFIG.sucess_login);
                    if(success)
                    {

                        if(hobby=SharePrefrences.readBoolean(SplashScreen.this,CONFIG.hobby_select))
                        {
                            hd=new Databasehelper(SplashScreen.this);
                            Intent intent=new Intent(SplashScreen.this,HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Intent intent=new Intent(SplashScreen.this, HobbiesSelection.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else {
                        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                   // Toast.makeText(SplashScreen.this, "fdsfsad", Toast.LENGTH_SHORT).show();
                }
            }, 2500);
        }
        else
        {
            //Toast.makeText(this, "in else", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar
                    .make(r, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(SplashScreen.this,SplashScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }
    private boolean isNetworkAvailable() {

              cm  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cm.getActiveNetworkInfo();
        return info!= null && info.isConnected();
    }

}
