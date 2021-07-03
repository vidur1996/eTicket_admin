package com.example.eticket_admin.conductor;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket_admin.MainActivity;
import com.example.eticket_admin.R;
import com.example.eticket_admin.profile.ProfileActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConductorMainActivity extends AppCompatActivity {
Button logout,tripDetails,profile,changeBus;
TextView username;
String conname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_main);

        logout = (Button)findViewById(R.id.btn_con_logout);
        profile = (Button)findViewById(R.id.btn_con_profile);
        changeBus = (Button)findViewById(R.id.btn_change_bus);
        tripDetails = (Button)findViewById(R.id.btn_trip);
        username = (TextView)findViewById(R.id.txt_con_username) ;
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            conname = extras.getString("conname");

        }
        username.setText(conname);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in1);
              finish();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), ProfileActivity.class);
                in1.putExtra("uname",conname);
                startActivity(in1);
            }
        });
        tripDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(),trip_setting.class);
                in1.putExtra("uname",conname);
                startActivity(in1);
            }
        });
        changeBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}