package com.example.speedtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityChangeSpeedLimit extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    EditText SpeedLimitEditText;
    TextView CurrentSpeedText;
    TextView WrongInputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_speed_limit);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        SpeedLimitEditText = findViewById(R.id.editSpeedLimit);
        CurrentSpeedText = findViewById(R.id.CurrentSpeed_text);
        WrongInputText = findViewById(R.id.WrongInputText);
        WrongInputText.setVisibility( View.INVISIBLE );

        CurrentSpeedText.setText(String.format("Current Speed Limit:\n%s\nm/s", preferences.getFloat("Speed_Limit", 0F)));

    }

    public void setSpeedLimit(View view ) {

        if ( SpeedLimitEditText.getText().toString().matches("") ) {
            WrongInputText.setVisibility( View.VISIBLE );
            return;
        }
        else {
            WrongInputText.setVisibility( View.INVISIBLE );
        }

        Float NewSpeedLimit = Float.valueOf( SpeedLimitEditText.getText().toString() );



        editor.putFloat( "Speed_Limit" , NewSpeedLimit );
        editor.apply();

        CurrentSpeedText.setText( String.format("Current Speed Limit:\n%s\nm/s", preferences.getFloat("Speed_Limit", 0F)) );


    }
}