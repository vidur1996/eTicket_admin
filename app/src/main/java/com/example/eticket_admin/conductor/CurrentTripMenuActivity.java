package com.example.eticket_admin.conductor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket_admin.R;

public class CurrentTripMenuActivity extends AppCompatActivity {
    Button scan_btn, detail_btn, finish_trip_btn;
    TextView user_txt;
    String conname, bus_name, date1;
    SharedPreferences sharedpreferences, profilePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_conductor);
        detail_btn = findViewById(R.id.his_btn);
        scan_btn = findViewById(R.id.tic_btn);
        user_txt = findViewById(R.id.user_txt);
        finish_trip_btn = findViewById(R.id.finish_trip_btn);
        final String MyPREFERENCES = "trip_details";
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String tripid = sharedpreferences.getString("trip_id", "");
        profilePreferences = getSharedPreferences("CONDUCTOR_PROFILE", Context.MODE_PRIVATE);
        conname = profilePreferences.getString("CONNAME", "");
        bus_name = profilePreferences.getString("BUSID", "");
        if (tripid.equals("") || tripid == "") {

            detail_btn.setEnabled(false);
            detail_btn.setBackgroundColor(Color.GRAY);

        }
        user_txt.setText(conname);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), ScanTicketActivity.class);
                startActivity(i1);
                CurrentTripMenuActivity.this.finish();

            }
        });
        user_txt.setText(conname + "<" + bus_name + "<" + tripid);
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), CurrentTripDetailActivity.class);
                startActivity(intent2);
                CurrentTripMenuActivity.this.finish();
            }
        });


        finish_trip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), EndTripActivity.class);
                startActivity(intent2);
                CurrentTripMenuActivity.this.finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i2 = new Intent(getApplicationContext(), TripMenuActivity.class);
        startActivity(i2);
        CurrentTripMenuActivity.this.finish();
    }
}
