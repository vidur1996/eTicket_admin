package com.example.eticket_admin.admin.removeuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket_admin.R;

public class removeuser extends AppCompatActivity {
    Button RemovePaassenger;
    Button RemoveTopup;
    Button RemoveConductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removeuser);
        RemovePaassenger = (Button) findViewById(R.id.r_passenger_btn);
        RemoveTopup = (Button) findViewById(R.id.r_topup_btn);
        RemoveConductor = (Button) findViewById(R.id.r_conductor_btn);

        RemovePaassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), RemovePassengerActivity.class);
                startActivity(in1);
            }
        });

        RemoveTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), RemoveTopupActivity.class);
                startActivity(in1);
            }
        });

        RemoveConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), RemoveConductorActivity.class);
                startActivity(in1);
            }
        });


    }
}