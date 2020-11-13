package com.example.eticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class timeout_screem extends AppCompatActivity {
TextView text_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_screem);

        text_out = findViewById(R.id.text_out);

        text_out.setText("ticket successful");
        new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent i2 = new Intent(getApplicationContext(),menu_conductor.class);

                startActivity(i2);
                finish();
            }
        }.start();
    }
}
