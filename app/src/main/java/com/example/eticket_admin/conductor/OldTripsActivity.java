
package com.example.eticket_admin.conductor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.eticket_admin.R;
import com.example.eticket_admin.admin.confirmuser.adapter.UserAdminAdapter;
import com.example.eticket_admin.conductor.adapter.TripsAdapter;
import com.example.eticket_admin.data.Trip;
import com.example.eticket_admin.data.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OldTripsActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    String conname = "errorr",busname = "error";
    ArrayList<Trip> list = new ArrayList<Trip>();
    TripsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldt_trips);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {

            conname = extras.getString("conname");
            busname = extras.getString("bus_name");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("bus").child(busname).child("trip");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    Trip value= snapshot.getValue(Trip.class);
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




        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_trips);
        adapter = new TripsAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
      //  adapter.onClickAdminAdapter(this);
    }



}