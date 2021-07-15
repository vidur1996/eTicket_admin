package com.example.eticket_admin.conductor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.eticket_admin.R;
import com.example.eticket_admin.conductor.adapter.TicketAdapter;
import com.example.eticket_admin.conductor.adapter.TripsAdapter;
import com.example.eticket_admin.data.Ticket;
import com.example.eticket_admin.data.Trip;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PassengerListAcitivty extends AppCompatActivity {
    DatabaseReference databaseReference;
    String tripid = "errorr",busname = "error";
    ArrayList<Ticket> list = new ArrayList<Ticket>();
    TicketAdapter adapter;
    SharedPreferences profilePreferences;
    SharedPreferences sharedpreferences;
    final String MyPREFERENCES = "trip_details" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_list_acitivty);
        profilePreferences = getSharedPreferences("CONDUCTOR_PROFILE", Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        tripid = sharedpreferences.getString("trip_id", "");
        busname = profilePreferences.getString("BUSID","");
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {

        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("bus").child(busname).child("passenger").child(tripid);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    Ticket value= snapshot.getValue(Ticket.class);

                    list.add(value);
                  adapter.notifyDataSetChanged();
                }
                else{


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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_passengers);
        adapter = new TicketAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}