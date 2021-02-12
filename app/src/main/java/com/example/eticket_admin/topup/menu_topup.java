package com.example.eticket_admin.topup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eticket_admin.MainActivity;
import com.example.eticket_admin.R;

public class menu_topup extends AppCompatActivity {
    Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_topup);


        signout  = findViewById(R.id.out_btn3);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), MainActivity.class);


                startActivity(in1);
                menu_topup.this.finish();
            }
        });

    }
}
