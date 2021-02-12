package com.example.eticket_admin.conductor;

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

import com.example.eticket_admin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class new_trip_create extends AppCompatActivity {

    EditText con_name,trip_to,trip_from,bus_name,start_time;
    String conname = "errorr",busname = "error",date1;
    DatabaseReference refferbus;
    Button confirm,back;
    SharedPreferences sharedpreferences;

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
        final String MyPREFERENCES = "trip_details" ;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();




        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {

            conname = extras.getString("conname");
            busname = extras.getString("bus_name");
        }

        final SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
        date1 = dateTime.format(new Date());
        con_name.setText(conname);
        bus_name.setText(busname);
        start_time.setText(date1);
        editor.putString("trip_id", date1);
        editor.commit();
        refferbus = FirebaseDatabase.getInstance().getReference().child("bus").child(busname).child("trip");


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refferbus.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        refferbus.child(date1).child("conductor name").setValue(conname);
                        refferbus.child(date1).child("to").setValue(trip_to.getText().toString().trim());
                        refferbus.child(date1).child("from").setValue(trip_from.getText().toString().trim());
                        refferbus.child(date1).child("collection").setValue("0");
                        refferbus.child(date1).child("passenger_count").setValue("0");
                        refferbus.child(date1).child("start time").setValue(date1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(new_trip_create.this,"added succesfully",Toast.LENGTH_LONG);
                                Intent i3 = new Intent(getApplicationContext(), trip_setting.class);
                                i3.putExtra("conname",conname);


                                startActivity(i3);
                                finish();
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), menu_conductor.class);
                i2.putExtra("conname",conname);
                startActivity(i2);
                new_trip_create.this.finish();
            }
        });








    }
}
