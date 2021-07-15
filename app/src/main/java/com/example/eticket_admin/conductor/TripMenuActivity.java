package com.example.eticket_admin.conductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eticket_admin.R;

public class TripMenuActivity extends AppCompatActivity {


    TextView text1;
    Button current_btn,new_btn,past_btn;
    String conname,bus_name;
    SharedPreferences sharedpreferences,profilePreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_setting);
        Log.e("222","trip SCREEN START");
        text1 = findViewById(R.id.text1);
        new_btn = findViewById(R.id.new_trip_btn);
        past_btn = findViewById(R.id.past_trip_btn);
        current_btn = findViewById(R.id.current_btn);
        final String MyPREFERENCES = "trip_details" ;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        profilePreferences = getSharedPreferences("CONDUCTOR_PROFILE",Context.MODE_PRIVATE);
        String tripid = sharedpreferences.getString("trip_id", "");
        conname = profilePreferences.getString("CONNAME","");
        bus_name = profilePreferences.getString("BUSID","");
        text1.setText(tripid);
        if(tripid.equals("")||tripid=="")  {
            current_btn.setEnabled(false);
            current_btn.setBackgroundColor(Color.GRAY);
        } else {
            new_btn.setEnabled(false);
            new_btn.setBackgroundColor(Color.GRAY);
        }

            current_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), CurrentTripMenuActivity.class);
                Log.e("222","trip SCREEN end");
                startActivity(i1);
                TripMenuActivity.this.finish();
            }
        });

        new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), NewTripCreateActivity.class);
                startActivity(i1);
                Log.e("222","trip SCREEN end");
                TripMenuActivity.this.finish();

            }
        });

        past_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), OldTripsActivity.class);
                startActivity(i1);
                TripMenuActivity.this.finish();
                //todo
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i1 = new Intent(getApplicationContext(), ConductorMainActivity.class);
        i1.putExtra("conname",conname);
        startActivity(i1);
        TripMenuActivity.this.finish();
        super.onBackPressed();
    }
}
