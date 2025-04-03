package com.example.speedtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.example.speedtracker.Classes.MyTTS;
import com.example.speedtracker.ViewSurpasses.ViewSurpassesActivity;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView SpeedTrackerText;




    Float SpeedLimit;

    SharedPreferences preferences;
    LocationManager locationManager;
    MyTTS myTTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpeedTrackerText = findViewById(R.id.Speed_Tracker_text);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Speed_Tracker_Mode", false);

        myTTS = new MyTTS(this);


        if (!preferences.contains("Speed_Limit")) {
            editor.putFloat("Speed_Limit", 0);
            SpeedLimit = 0F;
        } else {
            SpeedLimit = preferences.getFloat("Speed_Limit", 0);
        }

        editor.apply();

        SQLiteDatabase database = openOrCreateDatabase("Database.db", MODE_PRIVATE, null);
        database.execSQL("Create table if not exists Surpasses (" +
                         "Longtitude REAL," +
                         "Latitude REAL," +
                         "Speed REAL," +
                         "Timestamp TEXT Primary Key)");


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


    }


    public void toggleSpeedTracker(View view) {



        if ( preferences.getBoolean("Speed_Tracker_Mode", false) ) {
            //  speed tracking is on -> should be turned off

            locationManager.removeUpdates( this );

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("Speed_Tracker_Mode", false);
            editor.apply();

            findViewById(R.id.activity_main_id).setBackgroundColor( ContextCompat.getColor( this , R.color.white) );

            SpeedTrackerText.setText(R.string.OFF_text);

        } else {
            //  speed tracking is off -> should be turned on


            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions( this , new String[] {Manifest.permission.ACCESS_FINE_LOCATION} , 123 );
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("Speed_Tracker_Mode", true);
            editor.apply();


        }



    }


    public void setSpeedLimit ( View view ) {

        Intent intent = new Intent( this , ActivityChangeSpeedLimit.class);
        startActivity(intent);
    }


    public void viewSurpassesActivity ( View view ) {

        Intent intent = new Intent( this , ViewSurpassesActivity.class );
        startActivity(intent);

    }




    @Override
    public void onLocationChanged(@NonNull Location location) {
        Float speed = location.getSpeed();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( (float) (Math.floor((speed) * 100)) / 100 );
        stringBuilder.append("\nm/s");
        SpeedTrackerText.setText( stringBuilder.toString() );

        if ( preferences.getFloat( "Speed_Limit" , 0) == 0 ){
            return;
        }

        if (speed > preferences.getFloat( "Speed_Limit" , 0)){
            if ( ((ColorDrawable) findViewById(R.id.activity_main_id).getBackground()).getColor() == ContextCompat.getColor( this , R.color.red_bg) ){
                //  if user was already alerted but didn't lower the speed
                //  we don't keep another record or give new alert
                return;
            }
            SQLiteDatabase database = openOrCreateDatabase("Database.db", MODE_PRIVATE, null);
            stringBuilder.setLength(0);
            stringBuilder.append("Insert or ignore into Surpasses values(");
            stringBuilder.append( location.getLongitude() ).append(",");
            stringBuilder.append( location.getLatitude() ).append(",");
            stringBuilder.append( speed ).append(",");
            stringBuilder.append( "datetime('now')" ).append(")");

            database.execSQL( stringBuilder.toString() );

            findViewById(R.id.activity_main_id).setBackgroundColor( ContextCompat.getColor( this , R.color.red_bg) );

            new AlertDialog.Builder(this).setTitle("WARNING").setMessage("WARNING! YOU HAVE SURPASSED THE SPEED LIMIT!").show();

            myTTS.speak("WARNING! YOU HAVE SURPASSED THE SPEED LIMIT!");
        }
        else {
            findViewById(R.id.activity_main_id).setBackgroundColor( ContextCompat.getColor( this , R.color.white) );
        }
    }




}