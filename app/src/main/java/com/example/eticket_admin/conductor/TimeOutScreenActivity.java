package com.example.eticket_admin.conductor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.eticket_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class TimeOutScreenActivity extends AppCompatActivity {
    TextView text_out;
    Date current_time;
    String price,conname,pass_name,ticketTo,ticketFrom;
    String bus_name,date1;
    int bus_bal=0;
    int trip_bal =0;
    int pass_co =0;
    Boolean updateSuccess=false,updatePassengerSuccess=false;
    String tripid,ticketid;
    DatabaseReference reffer3,preffer,curentReffer;
    SharedPreferences sharedpreferences;
    SharedPreferences profilePreferences;
    final String MyPREFERENCES = "trip_details" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_screem);
        ticketid=genarateId();
        text_out = findViewById(R.id.text_out);
        current_time = Calendar.getInstance().getTime();
        profilePreferences= getSharedPreferences("CONDUCTOR_PROFILE" , Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        bus_name = profilePreferences.getString("BUSID","error");
        tripid = sharedpreferences.getString("trip_id", "");
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {

            conname = extras.getString("conname");
            price = extras.getString("price");
            pass_name = extras.getString("pass_name");
            ticketTo = extras.getString("pass_to");
            ticketFrom = extras.getString("pass_from");

        }
        final SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss aa");
        date1 = dateTime.format(new Date());

            setvalue();





        new CountDownTimer(3000,200)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                text_out.setText("ticket successful");
            }

            @Override
            public void onFinish() {
        if(updateSuccess && updatePassengerSuccess){
            Intent i2 = new Intent(getApplicationContext(), CurrentTripMenuActivity.class);
            i2.putExtra("conname",conname);
            i2.putExtra("bus_name",bus_name);
            startActivity(i2);
            TimeOutScreenActivity.this.finish();
        }
            }

        }.start();
    }


    public void setvalue(){
       reffer3 = FirebaseDatabase.getInstance().getReference().child("bus").child(bus_name);

        reffer3.child("revenue").setValue(ServerValue.increment(Integer.parseInt(price))).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    reffer3.child("trip").child(tripid).child("collection").setValue(ServerValue.increment(Integer.parseInt(price)));
                    reffer3.child("trip").child(tripid).child("passengerCount").setValue(ServerValue.increment(1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            reffer3.child("passenger").child(tripid).child(ticketid).child("ticketNo").setValue(ticketid);
                            reffer3.child("passenger").child(tripid).child(ticketid).child("userName").setValue(pass_name);
                            reffer3.child("passenger").child(tripid).child(ticketid).child("ticketTo").setValue(ticketTo);
                            reffer3.child("passenger").child(tripid).child(ticketid).child("ticketFrom").setValue(ticketFrom);
                            reffer3.child("passenger").child(tripid).child(ticketid).child("ticketValue").setValue(price).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        sendPassengerTicket();
                                        updateSuccess=true;
                                    }

                                }
                            });
                        }
                    });

                }
            }
        });
    }

    public String genarateId(){
        return UUID.randomUUID().toString().toUpperCase().substring(0, 6);
    }

    public void sendPassengerTicket(){
        preffer = FirebaseDatabase.getInstance().getReference().child("tickets").child(pass_name).child("pastTickets");
        preffer.child(date1).child("ticketNo").setValue(ticketid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    preffer.child(date1).child("ticketTo").setValue(ticketTo);
                    preffer.child(date1).child("ticketFrom").setValue(ticketFrom);
                    preffer.child(date1).child("busName").setValue(bus_name);
                    preffer.child(date1).child("price").setValue(price).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful()){
                                currentPassengerTicket();
                                updatePassengerSuccess=true;
                            }
                        }
                    });
                }
            }
        });
    }

    public void currentPassengerTicket(){
        curentReffer = FirebaseDatabase.getInstance().getReference().child("tickets").child(pass_name).child("currentTicket");
        curentReffer.child("ticketNo").setValue(ticketid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    curentReffer.child("ticketTo").setValue(ticketTo);
                    curentReffer.child("ticketFrom").setValue(ticketFrom);
                    curentReffer.child("busName").setValue(bus_name);
                    curentReffer.child("price").setValue(price).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful()){
                                updatePassengerSuccess=true;
                            }
                        }
                    });
                }
            }
        });
    }
}
