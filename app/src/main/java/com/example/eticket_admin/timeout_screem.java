package com.example.eticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

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
    String price,conname;
    String bus_name="error";
    int bus_bal=0;
    DatabaseReference reffer1,reffer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_screem);

        text_out = findViewById(R.id.text_out);
        current_time = Calendar.getInstance().getTime();
        text_out.setText("ticket successful");



        new CountDownTimer(3000,1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                Bundle extras = getIntent().getExtras();
                if (extras != null)
                {

                    conname = extras.getString("uname");
                    price = extras.getString("price");
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
                            bus_bal = Integer.parseInt(customer_balance);
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
                reffer2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reffer2.child("revenue").setValue(bus_bal).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Intent i2 = new Intent(getApplicationContext(),menu_conductor.class);
                                startActivity(i2);
                                finish();


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
