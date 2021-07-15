package com.example.eticket_admin.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fare_update extends AppCompatActivity {
    String text = "CURRENT RATE";
    String fare, adminname;
    TextView con_txt;
    EditText new_fare;
    Button fare_update_btn;
    DatabaseReference fare_reffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_update);


        con_txt = findViewById(R.id.con_txt);
        fare_update_btn = findViewById(R.id.fare_update_btn);
        new_fare = findViewById(R.id.new_fare_txt);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            adminname = extras.getString("adminname");
        }


        fare_reffer = FirebaseDatabase.getInstance().getReference();
        fare_reffer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fare = snapshot.child("rate").getValue().toString().trim();
                con_txt.setText(text + " - " + fare);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        fare_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n_fare = new_fare.getText().toString().trim();
                fare_reffer.child("rate").setValue(n_fare);

                Intent inout = new Intent(getApplicationContext(), main_menu.class);
                inout.putExtra("adminname", adminname);

                startActivity(inout);
                fare_update.this.finish();
            }
        });
    }
}