package com.example.eticket_admin.conductor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewTripCreateActivity extends AppCompatActivity {

    EditText con_name, trip_to, trip_from, bus_name, start_time;
    String conname = "errorr", busname = "error", date1;
    DatabaseReference refferbus;
    Button confirm, back;
    SharedPreferences sharedpreferences, profilePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip_create);
        con_name = findViewById(R.id.con_name);
        trip_from = findViewById(R.id.trip_from);
        trip_to = findViewById(R.id.trip_to);
        bus_name = findViewById(R.id.bus_name);
        start_time = findViewById(R.id.start_time);
        confirm = findViewById(R.id.con_btn);
        back = findViewById(R.id.back_btn);
        back.setVisibility(View.GONE);
        final String MyPREFERENCES = "trip_details";
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        profilePreferences = getSharedPreferences("CONDUCTOR_PROFILE", Context.MODE_PRIVATE);
        conname = profilePreferences.getString("CONNAME", "");
        busname = profilePreferences.getString("BUSID", "");

        final SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
        date1 = dateTime.format(new Date());
        con_name.setText(conname);
        bus_name.setText(busname);
        start_time.setText(date1);
        editor.putString("trip_id", date1);
        editor.putString("busId", busname);
        editor.commit();
        refferbus = FirebaseDatabase.getInstance().getReference().child("bus").child(busname).child("trip");


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    public void saveData() {
        refferbus.child(date1).child("conductorName").setValue(conname).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    refferbus.child(date1).child("toTrip").setValue(trip_to.getText().toString().trim());
                    refferbus.child(date1).child("fromTrip").setValue(trip_from.getText().toString().trim());
                    refferbus.child(date1).child("collection").setValue(0);
                    refferbus.child(date1).child("passengerCount").setValue(0);
                    refferbus.child(date1).child("endTime").setValue("---");
                    refferbus.child(date1).child("startTime").setValue(date1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                showAlert();
                            }
                        }
                    });
                }
            }
        });
    }

    public void showAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Success")
                .setMessage("New Trip Created Successful")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent i3 = new Intent(getApplicationContext(), TripMenuActivity.class);
                        startActivity(i3);
                        finish();
                    }
                }).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i1 = new Intent(getApplicationContext(), TripMenuActivity.class);
        startActivity(i1);
        NewTripCreateActivity.this.finish();

    }
}
