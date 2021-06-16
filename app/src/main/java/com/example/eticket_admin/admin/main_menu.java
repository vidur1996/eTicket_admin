package com.example.eticket_admin.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eticket_admin.MainActivity;
import com.example.eticket_admin.R;
import com.example.eticket_admin.admin.removeuser.removeuser;

public class main_menu extends AppCompatActivity {
Button signout_btn,adduser_btn,removeuser_btn,fare_btn,u_details_btn;
String adminname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        TextView name = findViewById(R.id.adname_txt);
        signout_btn  = findViewById(R.id.out_btn1);
        adduser_btn  = findViewById(R.id.add_btn);
        removeuser_btn  = findViewById(R.id.remove_btn);
        fare_btn  = findViewById(R.id.fare_btn);
        u_details_btn  = findViewById(R.id.user_details_btn);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            adminname = extras.getString("adminname");
        }
        name.setText(adminname);

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
