package com.example.eticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class trip_details extends AppCompatActivity {
Button exit,pass_list;
EditText revenue,trip_from1,trip_to1,start_time1,pass_no;
    String conname = "errorr",busname = "error",tripid ="error";

    DatabaseReference reffer1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

    revenue = findViewById(R.id.revenue);
    trip_from1 = findViewById(R.id.trip_from1);
    trip_to1 = findViewById(R.id.trip_to1);
    start_time1 = findViewById(R.id.start_time1);
    pass_no = findViewById(R.id.pass_no);
    exit = findViewById(R.id.back_btn);
    pass_list  = findViewById(R.id.pass_list);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {

            conname = extras.getString("conname");
            busname = extras.getString("bus_name");
            tripid = extras.getString("trip_id");
        }




        reffer1 = FirebaseDatabase.getInstance().getReference().child("bus").child(busname).child("trip").child(tripid);


        reffer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() ){
                revenue.setText(snapshot.child("collection").getValue().toString());

                trip_from1.setText(snapshot.child("from").getValue().toString());
                trip_to1.setText(snapshot.child("to").getValue().toString());
                start_time1.setText(snapshot.child("start time").getValue().toString());
                pass_no.setText(snapshot.child("passenger_count").getValue().toString());
                }
                else
                {
                    Toast.makeText(trip_details.this,"database error",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(),menu_conductor.class);
                i2.putExtra("conname",conname);
                i2.putExtra("bus_name",busname);
                i2.putExtra("trip_id",tripid);
                startActivity(i2);
                trip_details.this.finish();
            }
        });






    }
}
