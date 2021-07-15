package com.example.eticket_admin.conductor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eticket_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EndTripActivity extends AppCompatActivity {
    DatabaseReference refferbus;
    SharedPreferences profilePreferences;
    SharedPreferences sharedpreferences;
    String bus_name,conName,tripId;
    Boolean saveSuccess = false;
    final String MyPREFERENCES = "trip_details" ;
    TextView tv_startTime,tv_tripTo,tv_tripFrom,tv_Collection,tv_busId,tv_count;
    Button end_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_trip);
        Log.e("666","end trip SCREEN START");
        tv_startTime = findViewById(R.id.tv_start_time);
        tv_tripTo = findViewById(R.id.tv_to);
        tv_tripFrom = findViewById(R.id.tv_from);
        tv_Collection = findViewById(R.id.tv_collection);
        tv_busId = findViewById(R.id.tv_bus_id);
        tv_count = findViewById(R.id.tv_pass_count);
        end_btn = findViewById(R.id.btn_end_trip);


        profilePreferences= getSharedPreferences("CONDUCTOR_PROFILE" , Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        bus_name = profilePreferences.getString("BUSID","error");
        conName = profilePreferences.getString("CONNAME", "");
        tripId = sharedpreferences.getString("trip_id", "");
        refferbus = FirebaseDatabase.getInstance().getReference().child("bus").child(bus_name).child("trip").child(tripId);
                getdata();
         end_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
              confirmlert();


             }
         });
      }

    public void getdata(){
        refferbus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String to =snapshot.child("toTrip").getValue().toString();
                String from =snapshot.child("fromTrip").getValue().toString();
                String collection =snapshot.child("collection").getValue().toString();
                String starttime =snapshot.child("startTime").getValue().toString();
                String passCount = snapshot.child("passengerCount").getValue().toString();
                displayData(to,from,collection,starttime,passCount);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void displayData(String to, String from, String collection, String starttime, String passCount){
        tv_startTime.setText(  "Trip Start Time - " +starttime);
        tv_tripTo.setText(     "Trip To         - "+to);
        tv_tripFrom.setText(   "Trip  From      - "+from);
        tv_Collection.setText( "Trip Collection - Rs. "+collection);
        tv_busId.setText(      "Trip Bus Id     - "+bus_name);
        tv_count.setText(      "Passenger Count - "+passCount);
    }

    public void savedata(){
        final SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
        String date1 = dateTime.format(new Date());
        refferbus.child("endTime").setValue(date1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                   removeShared();
                }

            }
        });
    }

    public void removeShared(){
        sharedpreferences = getSharedPreferences("trip_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("trip_id","");
        editor.commit();

        showAlert();

    }

    public void showAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Alert")
                .setMessage("Trip ended Successful")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in1 = new Intent(getApplicationContext(), TripMenuActivity.class);
                        in1.putExtra("conname",conName);
                        startActivity(in1);
                        Log.e("666","end trip SCREEN end");
                        EndTripActivity.this.finish();


                    }
                }).show();

    }

    public void confirmlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Alert")
                .setMessage("Are you sure you want toe end the trip")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       savedata();

                    }
                }).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i2 = new Intent(getApplicationContext(), CurrentTripMenuActivity.class);
        startActivity(i2);
        Log.e("666","end trip SCREEN end");
        EndTripActivity.this.finish();
    }
}