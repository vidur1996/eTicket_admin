package com.example.eticket_admin.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eticket_admin.R;
import com.example.eticket_admin.admin.confirmuser.ConfirmAdminActivity;
import com.example.eticket_admin.admin.confirmuser.ConfirmConductorActivity;
import com.example.eticket_admin.admin.confirmuser.ConfirmPassengerActivity;
import com.example.eticket_admin.admin.confirmuser.ConfirmTopupActivity;

public class adduser extends AppCompatActivity {
    Button addPaassenger;
    Button addTopup;
    Button addAdmin;
    Button addConductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        addPaassenger = (Button)findViewById(R.id.passenger_btn);
        addTopup = (Button)findViewById(R.id.topup_btn);
        addAdmin = (Button)findViewById(R.id.admin_btn);
        addConductor = (Button)findViewById(R.id.conductor_btn);

        addPaassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), ConfirmPassengerActivity.class);
                startActivity(in1);
            }
        });

        addTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), ConfirmTopupActivity.class);
                startActivity(in1);
            }
        });

        addConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), ConfirmConductorActivity.class);
                startActivity(in1);
            }
        });

        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), ConfirmAdminActivity.class);
                startActivity(in1);
            }
        });
    }
}