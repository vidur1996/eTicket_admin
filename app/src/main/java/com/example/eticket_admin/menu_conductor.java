package com.example.eticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class menu_conductor extends AppCompatActivity {
    Button signout,scan_btn,detail_btn,finish_trip_btn;
    TextView user_txt;
    String conname,bus_name;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_conductor);


        signout  = findViewById(R.id.out_btn2);
        detail_btn = findViewById(R.id.his_btn);
        scan_btn = findViewById(R.id.tic_btn);
        user_txt = findViewById(R.id.user_txt);
        finish_trip_btn = findViewById(R.id.finish_trip_btn);
        final String MyPREFERENCES = "trip_details" ;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String tripid = sharedpreferences.getString("trip_id", "");
        if (tripid.equals("") || tripid=="")
        {

            detail_btn.setEnabled(false);
            detail_btn.setBackgroundColor(Color.GRAY);

        }
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            conname = extras.getString("conname");
            bus_name = extras.getString("bus_name");
        }

        user_txt.setText(conname);



        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),scan_ticket.class);
                i1.putExtra("conname",conname);
                i1.putExtra("bus_name",bus_name);
                startActivity(i1);
                menu_conductor.this.finish();
            }
        });





        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in1);
                menu_conductor.this.finish();
            }
        });

        user_txt.setText(conname+"<"+bus_name+"<"+tripid);



        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),trip_details.class);

                intent2.putExtra("conname",conname);
                intent2.putExtra("bus_name",bus_name);
                intent2.putExtra("trip_id",tripid);
                startActivity(intent2);
                menu_conductor.this.finish();


            }
        });


        finish_trip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences("trip_details", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("trip_id", "");
                editor.commit();
                Intent in1 = new Intent(getApplicationContext(),trip_setting.class);
                in1.putExtra("conname",conname);
                startActivity(in1);
                menu_conductor.this.finish();
            }
        });

    }
}
