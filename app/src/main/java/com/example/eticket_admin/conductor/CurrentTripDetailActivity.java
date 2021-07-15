package com.example.eticket_admin.conductor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eticket_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CurrentTripDetailActivity extends AppCompatActivity {
    Button exit,pass_list;
    EditText revenue,trip_from1,trip_to1,start_time1,pass_no;
    String conname = "errorr",busname = "error",tripid ="error";
    SharedPreferences sharedpreferences,profilePreferences;
    DatabaseReference reffer1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        Log.e("555","current trip detialSCREEN START");
    revenue = findViewById(R.id.revenue);
    trip_from1 = findViewById(R.id.trip_from1);
    trip_to1 = findViewById(R.id.trip_to1);
    start_time1 = findViewById(R.id.start_time1);
    pass_no = findViewById(R.id.pass_no);
    exit = findViewById(R.id.back_btn);
    pass_list  = findViewById(R.id.pass_list);
        final String MyPREFERENCES = "trip_details" ;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String tripid = sharedpreferences.getString("trip_id", "");
        profilePreferences = getSharedPreferences("CONDUCTOR_PROFILE",Context.MODE_PRIVATE);
        conname = profilePreferences.getString("CONNAME","");
        busname = profilePreferences.getString("BUSID","");

        reffer1 = FirebaseDatabase.getInstance().getReference().child("bus").child(busname).child("trip").child(tripid);
        reffer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() ){
                    revenue.setText(snapshot.child("collection").getValue().toString());
                    trip_from1.setText(snapshot.child("fromTrip").getValue().toString());
                    trip_to1.setText(snapshot.child("toTrip").getValue().toString());
                    start_time1.setText(snapshot.child("startTime").getValue().toString());
                    pass_no.setText(snapshot.child("passengerCount").getValue().toString());
                }
                else
                {
                    Toast.makeText(CurrentTripDetailActivity.this,"database error",Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        pass_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), PassengerListAcitivty.class);
                i2.putExtra("trip_id",tripid);
                startActivity(i2);
                Log.e("555","current trip detialSCREEN end");
            }
        });

        exit.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i2 = new Intent(getApplicationContext(), CurrentTripMenuActivity.class);
        startActivity(i2);
        Log.e("555","current trip detialSCREEN end");
        CurrentTripDetailActivity.this.finish();
    }
}
