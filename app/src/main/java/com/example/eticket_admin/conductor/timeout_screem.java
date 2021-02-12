package com.example.eticket_admin.conductor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eticket_admin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class timeout_screem extends AppCompatActivity {
    TextView text_out;
    Date current_time;
    String price,conname,pass_name;
    String bus_name="error";
    int bus_bal=0;
    int trip_bal =0;
    int pass_co =0;
    DatabaseReference reffer1,reffer2;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_screem);

        text_out = findViewById(R.id.text_out);
        current_time = Calendar.getInstance().getTime();
        text_out.setText("ticket successful");

        final String MyPREFERENCES = "trip_details" ;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
     final String tripid = sharedpreferences.getString("trip_id", "");


        new CountDownTimer(3000,200)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                Bundle extras = getIntent().getExtras();
                if (extras != null)
                {

                    conname = extras.getString("conname");
                    price = extras.getString("price");
                    pass_name = extras.getString("pass_name");
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

                            Toast.makeText(timeout_screem.this,"database error",Toast.LENGTH_SHORT);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        Toast.makeText(timeout_screem.this,"database error",Toast.LENGTH_SHORT);
                    }
                });




                reffer2 = FirebaseDatabase.getInstance().getReference().child("bus").child(bus_name);
                reffer2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists() ){
                            String customer_balance = snapshot.child("revenue").getValue().toString();
                            String trip_balance = snapshot.child("trip").child(tripid).child("collection").getValue().toString();
                            String pass_count = snapshot.child("trip").child(tripid).child("passenger_count").getValue().toString();
                           // text_out.setText(tripid+"___"+trip_balance);
                            bus_bal = Integer.parseInt(customer_balance);
                            trip_bal= Integer.parseInt(trip_balance);
                            pass_co = Integer.parseInt(pass_count);

                        }
                        else
                        {

                            Toast.makeText(timeout_screem.this,"database error",Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onFinish() {
                bus_bal = bus_bal + Integer.parseInt(price);
                trip_bal = trip_bal+ Integer.parseInt(price);
                pass_co++;
                reffer2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reffer2.child("revenue").setValue(bus_bal).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                reffer2.child("trip").child(tripid).child("collection").setValue(trip_bal);
                                reffer2.child("trip").child(tripid).child("passenger_count").setValue(pass_co);
                                reffer2.child("trip").child(tripid).child("passenger").child(pass_name).setValue(price).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                   @Override
                                                   public void onSuccess(Void aVoid) {
                                                       Intent i2 = new Intent(getApplicationContext(), menu_conductor.class);
                                                       i2.putExtra("conname",conname);
                                                       i2.putExtra("bus_name",bus_name);
                                                       startActivity(i2);
                                                       timeout_screem.this.finish();

                                           }
                                       });

                                    }
                                });




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

        }.start();
    }
}
