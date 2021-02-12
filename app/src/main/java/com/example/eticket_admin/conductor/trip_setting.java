package com.example.eticket_admin.conductor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eticket_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class trip_setting extends AppCompatActivity {


    TextView text1;
    Button current_btn,new_btn,past_btn;
    String conname,bus_name;
    DatabaseReference reffer1;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_setting);

        text1 = findViewById(R.id.text1);
        new_btn = findViewById(R.id.new_trip_btn);
        past_btn = findViewById(R.id.past_trip_btn);
        current_btn = findViewById(R.id.current_btn);
        final String MyPREFERENCES = "trip_details" ;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String tripid = sharedpreferences.getString("trip_id", "");
        text1.setText(tripid);
        if(tripid.equals("")||tripid=="")
        {
            current_btn.setEnabled(false);
            current_btn.setBackgroundColor(Color.GRAY);
        }
        else
        {
            new_btn.setEnabled(false);
            new_btn.setBackgroundColor(Color.GRAY);
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            conname = extras.getString("conname");
        }
        reffer1 = FirebaseDatabase.getInstance().getReference().child("admin").child(conname);
        reffer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() ){
                    bus_name = snapshot.child("bus").getValue().toString();

                }
                else
                {

                    Toast.makeText(trip_setting.this,"database error",Toast.LENGTH_SHORT);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(trip_setting.this,"database error",Toast.LENGTH_SHORT);
            }
        });


        current_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), menu_conductor.class);
                i1.putExtra("conname",conname);
                i1.putExtra("bus_name",bus_name);
                startActivity(i1);
                trip_setting.this.finish();
            }
        });

        new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), new_trip_create.class);
                i1.putExtra("conname",conname);
                i1.putExtra("bus_name",bus_name);
                startActivity(i1);
                trip_setting.this.finish();

            }
        });





    }
}
