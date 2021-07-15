package com.example.eticket_admin.conductor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ChangeBusActivity extends AppCompatActivity {
    final String MyPREFERENCES = "trip_details";
    DatabaseReference databaseReference;
    SharedPreferences profilePreferences;
    SharedPreferences sharedpreferences;
    String bus_name, conName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_bus);
        profilePreferences = getSharedPreferences("CONDUCTOR_PROFILE", Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        bus_name = profilePreferences.getString("BUSID", "error");
        conName = profilePreferences.getString("CONNAME", "");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("admin").child(conName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                bus_name = snapshot.getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
}