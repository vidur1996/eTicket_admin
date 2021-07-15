package com.example.eticket_admin.conductor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket_admin.MainActivity;
import com.example.eticket_admin.R;
import com.example.eticket_admin.profile.ProfileActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class ConductorMainActivity extends AppCompatActivity {
    Button logout,tripDetails,profile,revenueBus;
    TextView username;
    String conname,busname ;
    SharedPreferences sharedpreferences;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor_main);
        Log.e("111","CON MAIN SCREEN START");


        logout = (Button)findViewById(R.id.btn_con_logout);
        profile = (Button)findViewById(R.id.btn_con_profile);
        revenueBus = (Button)findViewById(R.id.btn_revenue);
        tripDetails = (Button)findViewById(R.id.btn_trip);
        username = (TextView)findViewById(R.id.txt_con_username) ;
        Bundle extras = getIntent().getExtras();


        if (extras != null)
        {
            conname = extras.getString("uname");

        }
        getProfile();
        username.setText(conname);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in1);
                Log.e("111","CON MAIN SCREEN end");
                ConductorMainActivity.this.finish();
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
                Intent in1 = new Intent(getApplicationContext(), TripMenuActivity.class);
                Log.e("111","CON MAIN SCREEN end");
                startActivity(in1);
                ConductorMainActivity.this.finish();
            }
        });
        revenueBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in1 = new Intent(getApplicationContext(),RevenueActivity.class);
                startActivity(in1);
                Log.e("111","CON MAIN SCREEN end");
                ConductorMainActivity.this.finish();
            }
        });
    }

    public void getProfile(){

        reff= FirebaseDatabase.getInstance().getReference().child("admin").child(conname).child("bus");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                busname =snapshot.getValue().toString();
                saveProfile();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public void saveProfile(){
        final String MyPREFERENCES = "CONDUCTOR_PROFILE" ;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("BUSID", busname);
        editor.putString("CONNAME",conname);
        editor.commit();
    }


}