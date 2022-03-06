package com.example.eticket_admin.conductor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eticket_admin.R;
import com.example.eticket_admin.conductor.adapter.RevenueAdapter;
import com.example.eticket_admin.data.Trip;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RevenueActivity extends AppCompatActivity {
    TextView totalRevenue;
    String busname;
    SharedPreferences profilePreferences;
    DatabaseReference databaseReference, reff;
    ArrayList<Trip> list = new ArrayList<Trip>();
    RevenueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);
        totalRevenue = findViewById(R.id.tv_total_revenue);

        profilePreferences = getSharedPreferences("CONDUCTOR_PROFILE", Context.MODE_PRIVATE);
        busname = profilePreferences.getString("BUSID", "");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("bus").child(busname).child("trip");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Trip value = snapshot.getValue(Trip.class);
                    list.add(value);
                    adapter.notifyDataSetChanged();
                } else {


                }


            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_revenue);
        adapter = new RevenueAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        getTotalRevenue();

    }

    public void getTotalRevenue() {
        reff = FirebaseDatabase.getInstance().getReference().child("bus").child(busname).child("revenue");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String revenue = snapshot.getValue().toString();
                    totalRevenue.setText("Rs." + revenue);
                }
                else    {
                    totalRevenue.setText("Rs. 0.00" );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}