package com.example.eticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class menu_conductor extends AppCompatActivity {
    Button signout,scan_btn,detail_btn;
    TextView user_txt;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_conductor);


        signout  = findViewById(R.id.out_btn2);
        detail_btn = findViewById(R.id.his_btn);
        scan_btn = findViewById(R.id.tic_btn);
        user_txt = findViewById(R.id.user_txt);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            uname = extras.getString("uname");
        }

        user_txt.setText(uname);



        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),scan_ticket.class);
                i1.putExtra("uname",uname);
                startActivity(i1);

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

    }
}
