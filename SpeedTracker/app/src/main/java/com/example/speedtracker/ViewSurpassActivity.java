package com.example.speedtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewSurpassActivity extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_surpass);

        textView1 = findViewById(R.id.textView2);
        textView2 = findViewById(R.id.textView5);
        textView3 = findViewById(R.id.textView6);
        textView4 = findViewById(R.id.textView7);

        Float longtitude = getIntent().getFloatExtra("longtitude", 0);
        Float latitude = getIntent().getFloatExtra("latitude", 0);
        Float speed = getIntent().getFloatExtra("speed", 0);
        String datetime = getIntent().getStringExtra("datetime");

        textView1.setText(String.format("Longtitude: %s", longtitude.toString()));
        textView2.setText(String.format("Latitude: %s", latitude.toString()));
        textView3.setText(String.format("Speed: %s", speed.toString()));
        textView4.setText(String.format("Date: %s", datetime));

    }
}