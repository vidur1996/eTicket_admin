package com.example.eticket_admin.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eticket_admin.MainActivity;
import com.example.eticket_admin.R;

public class main_menu extends AppCompatActivity {
Button signout_btn,adduser_btn,removeuser_btn,fare_btn,u_details_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        signout_btn  = findViewById(R.id.out_btn1);
        adduser_btn  = findViewById(R.id.add_btn);
        removeuser_btn  = findViewById(R.id.remove_btn);
        fare_btn  = findViewById(R.id.fare_btn);
        u_details_btn  = findViewById(R.id.user_details_btn);



        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), MainActivity.class);


                startActivity(in1);
                main_menu.this.finish();
            }
        });


        adduser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), adduser.class);


                startActivity(in1);
                main_menu.this.finish();
            }
        });


        removeuser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), removeuser.class);


                startActivity(in1);
                main_menu.this.finish();
            }
        });

        fare_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), fare_update.class);


                startActivity(in1);
                main_menu.this.finish();
            }
        });

        u_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), admin_profile.class);


                startActivity(in1);
                main_menu.this.finish();
            }
        });
    }
}
